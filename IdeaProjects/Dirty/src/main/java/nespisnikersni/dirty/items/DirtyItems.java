package nespisnikersni.dirty.items;

import nespisnikersni.dirty.Dirty;
import nespisnikersni.dirty.blocks.DirtyBlocks;
import nespisnikersni.dirty.entities.ModEntities;
import nespisnikersni.dirty.materials.DirtyArmorMaterial;
import nespisnikersni.dirty.materials.DirtySwordMaterial;
import nespisnikersni.dirty.materials.DirtyToolMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class DirtyItems{
    public static final Item PIECE_OF_DIRT = regItem("piece_of_dirt",new Piece_of_dirt(new Item.Settings().maxCount(16)));
    public static final Item EXPLOSIVE_DIRT = regItem("explosive_dirt",new Explosive_dirt(new Item.Settings().maxCount(16)));
    public static final Item DRIED_ROOTS = regItem("dried_roots",new Item(new Item.Settings()));
    public static final Item LIVING_ROOTS = regItem("living_roots",new Item(new Item.Settings()));
    public static final Item MELTED_DIRT = regItem("melted_dirt",new Item(new Item.Settings()));
    public static final Item PILE_OF_DIRT = regItem("pile_of_dirt",new Item(new Item.Settings()));
    public static final Item BIOFUEL = regItem("biofuel",new Item(new Item.Settings()));
    public static final ToolItem DIAMOND_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 1000;}@Override public float getMiningSpeedMultiplier() {return 7;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.DIAMOND);}}, 7,-2.4f,new Item.Settings());
    public static final ToolItem IRON_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 500;}@Override public float getMiningSpeedMultiplier() {return 6;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.IRON_INGOT);}}, 6,-2.4f,new Item.Settings());
    public static final ToolItem GOLDEN_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 100;}@Override public float getMiningSpeedMultiplier() {return 8;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.GOLD_INGOT);}}, 5,-2.4f,new Item.Settings());
    public static final ToolItem STONE_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 350;}@Override public float getMiningSpeedMultiplier() {return 4;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.COBBLESTONE,Items.DEEPSLATE);}}, 5,-2.4f,new Item.Settings());
    public static final ToolItem WOODEN_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 200;}@Override public float getMiningSpeedMultiplier() {return 3;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.OAK_PLANKS);}}, 4,-2.4f,new Item.Settings());
    public static final ToolItem NETHERITE_HAMMER = new PickaxeItem(new ToolMaterial() {@Override public int getDurability() {return 1700;}@Override public float getMiningSpeedMultiplier() {return 8;}@Override public float getAttackDamage() {return 0;}@Override public int getMiningLevel() {return 0;}@Override public int getEnchantability() {return 1;}@Override public Ingredient getRepairIngredient() {return Ingredient.ofItems(Items.NETHERITE_INGOT);}}, 8,-2.4f,new Item.Settings());
//    public static final Item MIRROR = regItem("mirror",new MirorItem(new Item.Settings()));
    public static final Item IRON_SHEET = regItem("iron_sheet",new Item(new Item.Settings()));
    public static final Item BURNT_IRON_SHEET = regItem("burnt_iron_sheet",new BurntIronSheet(new Item.Settings()));
    public static final Item LOOT_BOX = regItem("loot_box",new LootBoxItem(new Item.Settings()));
    public static final Item RANDOM_POTION = regItem("random_potion",new RandomPotion(new Item.Settings()));
    public static final Item MUD_SPAWN_EGG = regItem("mud_dog_spawn_egg",new SpawnEggItem(ModEntities.MUD_ENTITY_TYPE,Integer.parseInt("0E9925",16),Integer.parseInt("0E8622",16),new Item.Settings()));
    public static final ToolItem DRIED_ROOTS_STAFF = new Dried_roots_staff(DirtySwordMaterial.INSTANCE,8,-2.4f,new Item.Settings());
    public static final ToolItem DRIED_ROOTS_SWORD = new Dried_roots_sword(DirtySwordMaterial.INSTANCE,8,-2.4f,new Item.Settings());
    public static final ToolItem DRIED_ROOTS_PICKAXE = new Dried_roots_pickaxe(DirtyToolMaterial.INSTANCE,6,-2.8f,new Item.Settings());
    public static final ToolItem DRIED_ROOTS_AXE = new Dried_roots_axe(DirtyToolMaterial.INSTANCE,9,-3,new Item.Settings());
    public static final ArmorItem DRIED_ROOTS_HELMET = new DirtyArmor(new DirtyArmorMaterial(480,4,15,0.2f,4,"dirty:", ()->Ingredient.ofItems(DRIED_ROOTS)), ArmorItem.Type.HELMET,new Item.Settings());
    public static final ArmorItem DRIED_ROOTS_CHESTPLATE = new DirtyArmor(new DirtyArmorMaterial(600,9,15,0.2f,4,"dirty:",()->Ingredient.ofItems(DRIED_ROOTS)), ArmorItem.Type.CHESTPLATE,new Item.Settings());
    public static final ArmorItem DRIED_ROOTS_LEGGINGS = new DirtyArmor(new DirtyArmorMaterial(580,6,15,0.2f,4,"dirty:",()->Ingredient.ofItems(DRIED_ROOTS)), ArmorItem.Type.LEGGINGS,new Item.Settings());
    public static final ArmorItem DRIED_ROOTS_BOOTS = new DirtyArmor(new DirtyArmorMaterial(550,4,15,0.2f,4,"dirty:",()->Ingredient.ofItems(DRIED_ROOTS)), ArmorItem.Type.BOOTS,new Item.Settings());
    public static final Item ROOTS_SOUP = regItem("roots_soup", new StewItem(new Item.Settings().maxCount(16).food(FoodComponents.RABBIT_STEW)));
    private static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(DRIED_ROOTS_SWORD))
            .displayName(Text.translatable("itemGroup.dirty.maingroup"))
            .entries((context, entries) -> {
                entries.add(DRIED_ROOTS_SWORD);
                entries.add(PIECE_OF_DIRT);
                entries.add(EXPLOSIVE_DIRT);
                entries.add(DRIED_ROOTS);
                entries.add(LIVING_ROOTS);
                entries.add(MUD_SPAWN_EGG);
                entries.add(DRIED_ROOTS_STAFF);
                entries.add(DRIED_ROOTS_PICKAXE);
                entries.add(DRIED_ROOTS_AXE);
                entries.add(DRIED_ROOTS_HELMET);
                entries.add(DRIED_ROOTS_CHESTPLATE);
                entries.add(DRIED_ROOTS_LEGGINGS);
                entries.add(DRIED_ROOTS_BOOTS);
                entries.add(ROOTS_SOUP);
                entries.add(MELTED_DIRT);
                entries.add(PILE_OF_DIRT);
                entries.add(BIOFUEL);
                entries.add(BURNT_IRON_SHEET);
                entries.add(NETHERITE_HAMMER);
                entries.add(DIAMOND_HAMMER);
                entries.add(IRON_HAMMER);
                entries.add(GOLDEN_HAMMER);
                entries.add(STONE_HAMMER);
                entries.add(WOODEN_HAMMER);
                entries.add(IRON_SHEET);
                entries.add(LOOT_BOX);
                entries.add(RANDOM_POTION);
                entries.add(DirtyBlocks.DIRT_BLOCK_ITEM);
                entries.add(DirtyBlocks.DIRT_RECYCLER_ITEM);
                entries.add(DirtyBlocks.PROCESSING_TABLE_ITEM);
                entries.add(DirtyBlocks.SIEVE_ITEM);
            })
            .build();
    private static Item regItem(String id, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, id), item);
    }
    private static void addToCombat(FabricItemGroupEntries entries){
        entries.add(PIECE_OF_DIRT);
        entries.add(EXPLOSIVE_DIRT);
        entries.add(DRIED_ROOTS_SWORD);
        entries.add(DRIED_ROOTS_HELMET);
        entries.add(DRIED_ROOTS_CHESTPLATE);
        entries.add(DRIED_ROOTS_LEGGINGS);
        entries.add(DRIED_ROOTS_BOOTS);
        entries.add(DRIED_ROOTS_STAFF);
    }

    private static void addToTools(FabricItemGroupEntries entries){
        entries.add(DRIED_ROOTS_PICKAXE);
        entries.add(DRIED_ROOTS_AXE);
        entries.add(NETHERITE_HAMMER);
        entries.add(DIAMOND_HAMMER);
        entries.add(IRON_HAMMER);
        entries.add(GOLDEN_HAMMER);
        entries.add(STONE_HAMMER);
        entries.add(WOODEN_HAMMER);
    }
    private static void addToEggs(FabricItemGroupEntries entries){
        entries.add(MUD_SPAWN_EGG);
    }
    private static void addToIngredients(FabricItemGroupEntries entries){
        entries.add(DRIED_ROOTS);
        entries.add(LIVING_ROOTS);
        entries.add(MELTED_DIRT);
        entries.add(PILE_OF_DIRT);
    }

    public static void register(){
        Registry.register(Registries.ITEM_GROUP, new Identifier(Dirty.MOD_ID, "maingroup"), ITEM_GROUP);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_staff"), DRIED_ROOTS_STAFF);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_helmet"), DRIED_ROOTS_HELMET);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_chestplate"), DRIED_ROOTS_CHESTPLATE);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_leggings"), DRIED_ROOTS_LEGGINGS);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_boots"), DRIED_ROOTS_BOOTS);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_sword"), DRIED_ROOTS_SWORD);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_pickaxe"), DRIED_ROOTS_PICKAXE);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "dried_roots_axe"), DRIED_ROOTS_AXE);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "netherite_hammer"), NETHERITE_HAMMER);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "diamond_hammer"), DIAMOND_HAMMER);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "golden_hammer"), GOLDEN_HAMMER);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "iron_hammer"), IRON_HAMMER);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "stone_hammer"), STONE_HAMMER);
        Registry.register(Registries.ITEM, new Identifier(Dirty.MOD_ID, "wooden_hammer"), WOODEN_HAMMER);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(DirtyItems::addToCombat);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(DirtyItems::addToTools);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(DirtyItems::addToEggs);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(DirtyItems::addToIngredients);
    }
}
