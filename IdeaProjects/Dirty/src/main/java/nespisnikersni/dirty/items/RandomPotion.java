package nespisnikersni.dirty.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.PotionItem;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import java.util.List;

public class RandomPotion extends PotionItem {
    private static final List<StatusEffect> RANDOM_EFFECTS = Registries.STATUS_EFFECT.stream().toList();

    public RandomPotion(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient) {
            Random random = user.getEntityWorld().random;
            StatusEffect randomEffect = RANDOM_EFFECTS.get(random.nextInt(RANDOM_EFFECTS.size()));
            user.addStatusEffect(new StatusEffectInstance(randomEffect, 600));
        }
        return super.finishUsing(stack, world, user);
    }

}

