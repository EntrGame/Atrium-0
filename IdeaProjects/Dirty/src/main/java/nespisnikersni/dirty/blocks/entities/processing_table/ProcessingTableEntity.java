package nespisnikersni.dirty.blocks.entities.processing_table;

import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.blocks.entities.ImplementedInventory;
import nespisnikersni.dirty.recipes.types.DirtRecyclerRecipeType;
import nespisnikersni.dirty.recipes.types.ProcessingTableRecipeType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.Vibrations;
import org.jetbrains.annotations.Nullable;

public class ProcessingTableEntity extends BlockEntity implements ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1,ItemStack.EMPTY);
    private static final int SLOT = 0;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private static int maxProgress = 10;
    public ProcessingTableEntity(BlockPos pos, BlockState state) {
        super(DirtyBlockEntities.PROCESSING_TABLE_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ProcessingTableEntity.this.progress;
                    case 1 -> ProcessingTableEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ProcessingTableEntity.this.progress = value;
                    case 1 -> ProcessingTableEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("progress", progress);
        Inventories.writeNbt(nbt,inventory);
        super.writeNbt(nbt);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
        progress = nbt.getInt("progress");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if(hasRecipe()) {
            if (craftFinished()) {
                craftItem();
                dropItem();
                updateProgress();
            }
        }
    }
    private boolean hasRecipe() {
        World world = getWorld();
        if (world == null) return false;

        RecipeManager recipeManager = world.getRecipeManager();
        SimpleInventory inputInventory = new SimpleInventory(1);
        inputInventory.setStack(0, this.getStack(SLOT));
        return recipeManager.getFirstMatch(ProcessingTableRecipeType.INSTANCE, inputInventory, world)
                .isPresent();
    }
    private void craftItem() {
        World world = getWorld();
        if (world == null) return;
        RecipeManager recipeManager = world.getRecipeManager();
        SimpleInventory inputInventory = new SimpleInventory(1);
        inputInventory.setStack(0, inventory.get(SLOT));

        recipeManager.getFirstMatch(ProcessingTableRecipeType.INSTANCE, inputInventory, world).ifPresent(recipe -> {
            if (recipe.matches(inputInventory, world)) {
                ItemStack result = recipe.getOutput(world.getRegistryManager());
                this.inventory.get(SLOT).decrement(1);
                ItemStack outputStack = this.inventory.get(SLOT);
                if (outputStack.isEmpty()) {
                    this.inventory.set(SLOT, result.copy());
                }
            }
        });
    }
    public ItemStack getRenderStack(){
        return this.getStack(SLOT);
    }

    @Override
    public void markDirty() {
        world.updateListeners(pos,getCachedState(),getCachedState(),3);
        super.markDirty();
    }

    private boolean craftFinished() {
        return progress >= maxProgress;
    }
    private void updateProgress(){
        progress = 0;
    }
    public void increaseProgress(){
        progress++;
    }
    public void dropItem(){
        ItemStack stack = this.getStack(0);
        if(!stack.isEmpty()){
            ItemEntity itemEntity = new ItemEntity(world,this.pos.getX()+0.5,this.pos.getY()+1,this.pos.getZ()+0.5,stack);
            world.spawnEntity(itemEntity);
            this.setStack(0,ItemStack.EMPTY);
        }
    }
    public void addItem(Item item){
        dropItem();
        this.setStack(SLOT,item.getDefaultStack());
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    
}
