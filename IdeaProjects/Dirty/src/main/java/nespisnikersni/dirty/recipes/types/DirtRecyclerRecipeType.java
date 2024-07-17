package nespisnikersni.dirty.recipes.types;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.recipes.DirtRecyclerRepice;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DirtRecyclerRecipeType implements RecipeType<DirtRecyclerRepice> {
    public static final DirtRecyclerRecipeType INSTANCE = new DirtRecyclerRecipeType();
    public static final Identifier ID = new Identifier(Dirty.MOD_ID, "recycling");

    @Override
    public String toString() {
        return ID.toString();
    }

    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, ID, INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, DirtRecyclerRepice.DirtRecyclerRecipeSerializer.ID, DirtRecyclerRepice.DirtRecyclerRecipeSerializer.INSTANCE);
    }
}

