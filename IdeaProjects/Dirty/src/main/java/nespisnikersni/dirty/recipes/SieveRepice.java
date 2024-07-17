package nespisnikersni.dirty.recipes;

import com.google.gson.JsonObject;
import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.recipes.types.SieveRecipeType;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class SieveRepice implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final Ingredient ingredient;
    private final ItemStack output;

    public SieveRepice(Identifier id, Ingredient ingredient, ItemStack output) {
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
        return SieveRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return SieveRecipeType.INSTANCE;
    }

    public static class SieveRecipeSerializer implements RecipeSerializer<SieveRepice> {
        public static final SieveRecipeSerializer INSTANCE = new SieveRecipeSerializer();
        public static final Identifier ID = new Identifier(Dirty.MOD_ID, "screening");

        @Override
        public SieveRepice read(Identifier id, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new SieveRepice(id, ingredient, output);
        }

        @Override
        public SieveRepice read(Identifier id, PacketByteBuf buf) {
            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            return new SieveRepice(id, ingredient, output);
        }

        @Override
        public void write(PacketByteBuf buf, SieveRepice recipe) {
            recipe.ingredient.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
