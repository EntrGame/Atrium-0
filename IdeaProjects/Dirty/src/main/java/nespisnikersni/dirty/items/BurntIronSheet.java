package nespisnikersni.dirty.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BurntIronSheet extends Item {
    private int timer=0;
    public BurntIronSheet(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        timer = stack.getOrCreateNbt().getInt("timer");
        entity.setOnFireFor(1);
        timer++;
        stack.getOrCreateNbt().putInt("timer",timer);
        if(timer>=12000&&entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;
            player.getInventory().setStack(slot,new ItemStack(DirtyItems.IRON_SHEET,stack.getCount()));
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

}
