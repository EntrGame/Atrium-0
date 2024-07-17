package nespisnikersni.dirty.recipes.types;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.recipes.DirtRecyclerRepice;
import nespisnikersni.dirty.recipes.ProcessingTableRepice;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ProcessingTableRecipeType implements RecipeType<ProcessingTableRepice> {
    public static final ProcessingTableRecipeType INSTANCE = new ProcessingTableRecipeType();
    public static final Identifier ID = new Identifier(Dirty.MOD_ID, "processing");

    @Override
    public String toString() {
        return ID.toString();
    }

    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, ID, INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, ProcessingTableRepice.ProcessingTableRecipeSerializer.ID, ProcessingTableRepice.ProcessingTableRecipeSerializer.INSTANCE);
    }
}

