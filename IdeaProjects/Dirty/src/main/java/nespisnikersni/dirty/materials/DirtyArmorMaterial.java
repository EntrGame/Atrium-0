package nespisnikersni.dirty.materials;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import java.util.function.Supplier;

public class DirtyArmorMaterial implements ArmorMaterial {
    private int durability;
    private int protection;
    private int enchantability;
    private float knockback;
    private float toughness;
    private String name;
    private Supplier<Ingredient> ingredient;
    public DirtyArmorMaterial(int durability, int protection, int enchantability, float knockback, float toughness, String name,Supplier<Ingredient> ingredient) {
        this.durability = durability;
        this.protection = protection;
        this.enchantability = enchantability;
        this.knockback = knockback;
        this.toughness = toughness;
        this.name = name;
        this.ingredient = ingredient;
    }
    @Override
    public int getDurability(ArmorItem.Type type) {
        return durability;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return protection;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return null;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return ingredient.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockback;
    }
}
