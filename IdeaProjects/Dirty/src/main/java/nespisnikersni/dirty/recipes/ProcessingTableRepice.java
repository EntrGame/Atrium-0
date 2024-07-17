package nespisnikersni.dirty.recipes;

import com.google.gson.JsonObject;
import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.recipes.types.ProcessingTableRecipeType;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class ProcessingTableRepice implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final Ingredient ingredient;
    private final ItemStack output;

    public ProcessingTableRepice(Identifier id, Ingredient ingredient, ItemStack output) {
        this.id = id;
        this.ingredient = ingredient;
        this.output = output;
    }

    @Override
    public boolean matches(SimpleInventory inv, World world) {
        return ingredient.test(inv.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output.copy();
    }
    @Override
    public boolean fits(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ProcessingTableRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ProcessingTableRecipeType.INSTANCE;
    }

    public static class ProcessingTableRecipeSerializer implements RecipeSerializer<ProcessingTableRepice> {
        public static final ProcessingTableRecipeSerializer INSTANCE = new ProcessingTableRecipeSerializer();
        public static final Identifier ID = new Identifier(Dirty.MOD_ID, "processing");

        @Override
        public ProcessingTableRepice read(Identifier id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new ProcessingTableRepice(id, ingredient, output);
        }

        @Override
        public ProcessingTableRepice read(Identifier id, PacketByteBuf buf) {
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new ProcessingTableRepice(id, ingredient, output);
        }

        @Override
        public void write(PacketByteBuf buf, ProcessingTableRepice recipe) {
            recipe.ingredient.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
