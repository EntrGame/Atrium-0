package nespisnikersni.dirty.blocks.entities.dirt_recycler;

import nespisnikersni.dirty.blocks.entities.DirtyBlockEntities;
import nespisnikersni.dirty.blocks.entities.ImplementedInventory;
import nespisnikersni.dirty.recipes.types.DirtRecyclerRecipeType;
import nespisnikersni.dirty.screenhandlers.DirtRecyclerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
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
import org.jetbrains.annotations.Nullable;

public class DirtRecyclerEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4,ItemStack.EMPTY);
    private static final int OUTPUT = 2;
    private static final int FUEL = 1;
    private static final int INPUT = 0;
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private static int maxProgress = 200;
    private int burnTime=0;
    private int maxBurnTime=0;
    public DirtRecyclerEntity(BlockPos pos, BlockState state) {
        super(DirtyBlockEntities.DIRT_RECYCLER_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DirtRecyclerEntity.this.progress;
                    case 1 -> DirtRecyclerEntity.this.maxProgress;
                    case 2 -> DirtRecyclerEntity.this.burnTime;
                    case 3 -> DirtRecyclerEntity.this.maxBurnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DirtRecyclerEntity.this.progress = value;
                    case 1 -> DirtRecyclerEntity.this.maxProgress = value;
                    case 2 -> DirtRecyclerEntity.this.burnTime = value;
                    case 3 -> DirtRecyclerEntity.this.maxBurnTime = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }
    private boolean isBurning() {
        return this.burnTime > 0;
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("progress", progress);
        nbt.putShort("BurnTime", (short)this.burnTime);
        nbt.putInt("maxburntime",maxBurnTime);
        Inventories.writeNbt(nbt,inventory);
        super.writeNbt(nbt);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.getShort("BurnTime");
        this.maxBurnTime = nbt.getInt("maxburntime");
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
    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.isBurning()) {
            --this.burnTime;
        }else{
            if(AbstractFurnaceBlockEntity.canUseAsFuel(this.getStack(FUEL))&&!this.getStack(INPUT).isEmpty()){
                if(this.getStack(FUEL).getItem() instanceof BucketItem){
                    this.setStack(FUEL,Items.BUCKET.getDefaultStack());
                }else{
                    this.removeStack(FUEL,1);
                }
                this.maxBurnTime=this.getFuelTime(this.getStack(FUEL));
                this.burnTime=this.getFuelTime(this.getStack(FUEL));
            }
        }
        if(outputSlot()){
            if(this.hasRecipe()){
                this.increaseProgress();
                markDirty(world,pos,state);
                if(craftFinished()){
                    this.craftItem();
                    this.updateProgress();
                }
            }else{
                this.updateProgress();
            }
        }else{
            this.updateProgress();
            markDirty(world,pos,state);
        }
    }
    private void craftItem() {
        World world = getWorld();
        if (world == null) return;

        RecipeManager recipeManager = world.getRecipeManager();
        SimpleInventory inputInventory = new SimpleInventory(1);
        inputInventory.setStack(0, inventory.get(INPUT));

        recipeManager.getFirstMatch(DirtRecyclerRecipeType.INSTANCE, inputInventory, world).ifPresent(recipe -> {
            if (recipe.matches(inputInventory, world)) {
                ItemStack result = recipe.getOutput(world.getRegistryManager());
                this.inventory.get(INPUT).decrement(1);
                ItemStack outputStack = this.inventory.get(OUTPUT);
                if (outputStack.isEmpty()) {
                    this.inventory.set(OUTPUT, result.copy());
                } else if (outputStack.getItem() == result.getItem()) {
                    outputStack.increment(result.getCount());
                }
            }
        });
    }
    private boolean craftFinished() {
        return progress >= maxProgress;
    }
    private void updateProgress(){
        progress = 0;
    }
    private void increaseProgress(){
        progress++;
    }
    private boolean outputSlot(){
        return this.getStack(OUTPUT).isEmpty() || this.getStack(OUTPUT).getCount() < this.getStack(OUTPUT).getMaxCount();
    }
    private boolean hasRecipe() {
        World world = getWorld();
        if (world == null) return false;

        RecipeManager recipeManager = world.getRecipeManager();
        SimpleInventory inputInventory = new SimpleInventory(1);
        inputInventory.setStack(0, this.getStack(INPUT));

        return recipeManager.getFirstMatch(DirtRecyclerRecipeType.INSTANCE, inputInventory, world).map(recipe -> {
            ItemStack result = recipe.getOutput(world.getRegistryManager());
            return canCraft(result.getItem()) && isOutputFull(result);
        }).orElse(false);
    }
    private boolean canCraft(Item item){
        return this.getStack(OUTPUT).getItem() == item || this.getStack(OUTPUT).isEmpty() && burnTime>0;
    }
    private boolean isOutputFull(ItemStack result){
        return this.getStack(OUTPUT).getCount() + result.getCount() <= this.getStack(OUTPUT).getMaxCount();
    }
    
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("dirty.dirt_recycler.name");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new DirtRecyclerScreenHandler(syncId,playerInventory,this,propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
