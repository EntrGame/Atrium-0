package nespisnikersni.dirty.enchants;

import nespisnikersni.dirty.Dirty;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.Random;

public class DirtyEnchants {
    public static final Enchantment BOER = new Enchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}) {};
    public static final Enchantment AUTO_SMELT = new Enchantment(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}){};
    public static final Enchantment REGENERATION = new Enchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{}){
        @Override
        public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
            int probability = 0;
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            switch (level){
                case 1:{
                    probability=5;
                }
                case 2:{
                    probability=7;
                }
                case 3:{
                    probability=10;
                }
            }
                if (randomNumber < probability) {
                    user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,100,2));
                }
            super.onUserDamaged(user, attacker, level);
        }

        @Override
        public int getMaxLevel() {
            return 3;
        }
    };
    public static final Enchantment WITHER = new Enchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]{}){
        @Override
        public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
            int probability = 0;
            Random random = new Random();
            int randomNumber = random.nextInt(100);
            switch (level){
                case 1:{
                    probability=5;
                }
                case 2:{
                    probability=7;
                }
                case 3:{
                    probability=10;
                }
            }
                if (randomNumber < probability) {
                    if(attacker instanceof LivingEntity) {
                        LivingEntity attackerLiv=(LivingEntity) attacker;
                        attackerLiv.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,200,2));
                    }
                }
            super.onUserDamaged(user, attacker, level);
        }

        @Override
        public int getMaxLevel() {
            return 3;
        }
    };
    public static final Enchantment FLY = new Enchantment(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET}) {};

    public static void register() {
        Registry.register(Registries.ENCHANTMENT, new Identifier(Dirty.MOD_ID, "boer"), BOER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(Dirty.MOD_ID, "auto_smelt"), AUTO_SMELT);
        Registry.register(Registries.ENCHANTMENT, new Identifier(Dirty.MOD_ID, "regeneration"), REGENERATION);
        Registry.register(Registries.ENCHANTMENT, new Identifier(Dirty.MOD_ID, "wither"), WITHER);
        Registry.register(Registries.ENCHANTMENT, new Identifier(Dirty.MOD_ID, "fly"), FLY);
    }
}
