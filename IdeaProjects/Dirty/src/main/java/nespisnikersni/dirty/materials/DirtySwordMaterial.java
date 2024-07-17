package nespisnikersni.dirty.materials;


import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import static nespisnikersni.dirty.items.DirtyItems.DRIED_ROOTS;

public class DirtySwordMaterial implements ToolMaterial {
    public static ToolMaterial INSTANCE = new DirtySwordMaterial();
    @Override
    public int getDurability() {
        return 1000;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(DRIED_ROOTS);
    }
}
