package nespisnikersni.dirty.recipes.types;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.recipes.SieveRepice;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SieveRecipeType implements RecipeType<SieveRepice> {
    public static final SieveRecipeType INSTANCE = new SieveRecipeType();
    public static final Identifier ID = new Identifier(Dirty.MOD_ID, "screening");

    @Override
    public String toString() {
        return ID.toString();
    }

    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, ID, INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, SieveRepice.SieveRecipeSerializer.ID, SieveRepice.SieveRecipeSerializer.INSTANCE);
    }
}

