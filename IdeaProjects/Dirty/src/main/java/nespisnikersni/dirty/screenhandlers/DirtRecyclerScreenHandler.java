package nespisnikersni.dirty.screenhandlers;

import nespisnikersni.dirty.blocks.entities.dirt_recycler.DirtRecyclerEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class DirtRecyclerScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public final DirtRecyclerEntity blockEntity;
    private final PropertyDelegate propertyDelegate;
    public DirtRecyclerScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf){
        this(syncId,inventory,inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(4));
    }
    public DirtRecyclerScreenHandler(int syncId, PlayerInventory inventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(DirtyScreenHandlers.DIRT_RECYCLER_SCREEN_HANDLER,syncId);
        checkSize((Inventory) blockEntity,4);
        this.inventory = (Inventory) blockEntity;
        inventory.onOpen(inventory.player);
        this.propertyDelegate = propertyDelegate;
        this.blockEntity = (DirtRecyclerEntity) blockEntity;
        this.addSlot(new Slot(this.inventory,0,80,11));
        this.addSlot(new Slot(this.inventory,2,80,59));
        this.addSlot(new Slot(this.inventory,1,52,35));
        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addProperties(propertyDelegate);
    }
    public boolean isCrafting(){
        return propertyDelegate.get(0)>0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    public int getScaledFuel() {
        float progress = this.propertyDelegate.get(2);
        float maxProgress = this.propertyDelegate.get(3);
        float progressArrowSize = 23;
        return maxProgress != 0 && progress != 0 ? (int) ((progress / maxProgress) * progressArrowSize) : 0;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
