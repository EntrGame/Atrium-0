package nespisnikersni.dirty.items;

import nespisnikersni.dirty.blocks.DirtyBlocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class LootBoxItem extends Item {
    public LootBoxItem(Settings settings) {
        super(settings);
    }

    private static final Map<ItemStack, Integer> ITEM_CHANCES = new HashMap<>();

    static {
        ITEM_CHANCES.put(new ItemStack(Items.DIAMOND), 3);
        ITEM_CHANCES.put(new ItemStack(Items.GOLD_INGOT), 4);
        ITEM_CHANCES.put(new ItemStack(Items.IRON_INGOT), 5);
        ITEM_CHANCES.put(new ItemStack(Items.EMERALD), 2);
        ITEM_CHANCES.put(new ItemStack(Items.COAL), 15);
        ITEM_CHANCES.put(new ItemStack(Items.NETHERITE_INGOT),1);
        ITEM_CHANCES.put(new ItemStack(Items.NETHERITE_BLOCK),1);
        ITEM_CHANCES.put(new ItemStack(Items.COAL_BLOCK),10);
        ITEM_CHANCES.put(new ItemStack(Items.DIAMOND_BLOCK),2);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.DRIED_ROOTS),2);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.BIOFUEL),8);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.NETHERITE_HAMMER),2);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.DIAMOND_HAMMER),4);
        ITEM_CHANCES.put(new ItemStack(DirtyBlocks.DIRT_BLOCK_ITEM),8);
        ITEM_CHANCES.put(new ItemStack(Items.DIAMOND_SWORD),2);
        ITEM_CHANCES.put(new ItemStack(Items.MOSS_BLOCK),6);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.EXPLOSIVE_DIRT),5);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.PIECE_OF_DIRT),5);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.IRON_SHEET),5);
        ITEM_CHANCES.put(new ItemStack(DirtyItems.IRON_SHEET),5);
        ITEM_CHANCES.put(new ItemStack(Items.ENCHANTED_BOOK),5);
    }
    private static ItemStack createRandomEnchantedBook(PlayerEntity player) {
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        Optional<RegistryEntry.Reference<Enchantment>> optionalEnchantment = Registries.ENCHANTMENT.getRandom(player.getWorld().getRandom());
        if (optionalEnchantment.isPresent()) {
            Enchantment randomEnchantment = optionalEnchantment.get().value();
            int level = 1 + new Random().nextInt(randomEnchantment.getMaxLevel());
            EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentLevelEntry(randomEnchantment, level));
        }
        return enchantedBook;
    }

    private void giveRandomItemToPlayer(PlayerEntity player) {
        int totalChance = ITEM_CHANCES.values().stream().mapToInt(Integer::intValue).sum();
        int randomChance = player.getWorld().getRandom().nextInt(totalChance);
        int cumulativeChance = 0;
        ItemStack itemToGive = null;
        for (Map.Entry<ItemStack, Integer> entry : ITEM_CHANCES.entrySet()) {
            cumulativeChance += entry.getValue();
            if (randomChance < cumulativeChance) {
                itemToGive = entry.getKey().copy();
                break;
            }
        }
        if (itemToGive != null) {
            if (itemToGive.getItem() == Items.ENCHANTED_BOOK) {
                itemToGive = createRandomEnchantedBook(player);
            }
            int randomCount = 1 + player.getWorld().getRandom().nextInt(3);
            itemToGive.setCount(randomCount);
            if(itemToGive.getItem() instanceof ToolItem){
                itemToGive.setCount(1);
            }
            if (!player.getInventory().insertStack(itemToGive)) {
                player.dropStack(itemToGive);
            }
        }
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClient) {
            if (!player.isCreative()) {
                player.getMainHandStack().decrement(1);
            }
            giveRandomItemToPlayer(player);
        }
        return super.use(world, player, hand);
    }
}
