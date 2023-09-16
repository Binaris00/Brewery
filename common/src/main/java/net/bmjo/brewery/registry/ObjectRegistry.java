package net.bmjo.brewery.registry;

import com.google.common.collect.Lists;
import de.cristelknight.doapi.Util;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bmjo.brewery.Brewery;
import net.bmjo.brewery.block.*;
import net.bmjo.brewery.block.crops.BarleyCropBlock;
import net.bmjo.brewery.block.crops.CornCropBlock;
import net.bmjo.brewery.block.multiblockparts.*;
import net.bmjo.brewery.item.*;
import net.bmjo.brewery.util.BreweryIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Brewery.MOD_ID,  Registry.ITEM_REGISTRY);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Brewery.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    //CROPS
    //TODO Texturen
    public static final RegistrySupplier<Block> WILD_HOPS = registerWithoutItem("wild_hops", () -> new DeadBushBlock(BlockBehaviour.Properties.copy(Blocks.ALLIUM)));
    public static final RegistrySupplier<Block> HOPS_CROP = registerWithoutItem("hops_crop", () -> new CropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> HOPS_SEEDS = registerItem("hops_seeds", () -> new BlockItem(HOPS_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> HOPS = registerItem("hops", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> BARLEY_CROP = registerWithoutItem("barley_crop", () -> new BarleyCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> BARLEY_SEEDS = registerItem("barley_seeds", () -> new BlockItem(BARLEY_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> BARLEY = registerItem("barley", () -> new Item(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Block> CORN_CROP = registerWithoutItem("corn_crop", () -> new CornCropBlock(getBushSettings()));
    public static final RegistrySupplier<Item> CORN_SEEDS = registerItem("corn_seeds", () -> new BlockItem(CORN_CROP.get(), getSettings()));
    public static final RegistrySupplier<Item> CORN = registerItem("corn", () -> new Item(getSettings().food(Foods.APPLE)));
    //MISC
    public static final RegistrySupplier<Block> DRIED_WHEAT = registerBI("dried_wheat", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistrySupplier<Block> DRIED_BARLEY = registerBI("dried_barley", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistrySupplier<Block> DRIED_CORN = registerBI("dried_corn", () -> new Block(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    //BREWING STATION
    public static final RegistrySupplier<Block> WATER_BASIN = registerWithoutItem("water_basin", () -> new WaterBasinBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> STEAM_WHISTLE = registerWithoutItem("steam_whistle", () -> new SteamWhistleBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> OVEN = registerWithoutItem("oven", () -> new OvenBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> TIMER = registerWithoutItem("timer", () -> new TimerBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    public static final RegistrySupplier<Block> BREWINGSTATION = registerBI("brewingstation", () -> new BrewKettleBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_BLOCK)));
    //NETHERITE BREWING STATION
    //TODO
    //SILO
    //TODO
    //FURNITURE
    public static final RegistrySupplier<Block> BENCH = registerBI("bench", () -> new BenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> TABLE = registerBI("table", () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> BAR_COUNTER = registerBI("bar_counter", () -> new TableBlock(BlockBehaviour.Properties.of(Material.WOOD).requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> PATTERNED_WOOL = registerBI("patterned_wool", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL)));
    public static final RegistrySupplier<Block> PATTERNED_CARPET = registerBI("patterned_carpet", () -> new CarpetBlock(BlockBehaviour.Properties.copy(Blocks.BLACK_CARPET)));

    //BEER & WHISKEY
    //TODO Models
    public static final RegistrySupplier<Block> BEER_WHEAT = registerBeverage("beer_wheat", () -> new BeverageBlock(getBeverageSettings()), MobEffects.DAMAGE_BOOST, 30 * 20);
    public static final RegistrySupplier<Block> BEER_HOPS = registerBeverage("beer_hops", () -> new BeverageBlock(getBeverageSettings()), MobEffects.LUCK, 30 * 20);
    public static final RegistrySupplier<Block> BEER_BARLEY = registerBeverage("beer_barley", () -> new BeverageBlock(getBeverageSettings()), MobEffects.DIG_SPEED, 30 * 20);
    public static final RegistrySupplier<Block> BEER_CHORUS = registerBeverage("beer_chorus", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.TELEPORT.get(), 5 * 20);
    public static final RegistrySupplier<Block> WHISKEY_MAGGOALLAN = registerBeverage("whiskey_maggoallan", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.GRAVEDIGGER.get(), 2 * 20);
    public static final RegistrySupplier<Block> WHISKEY_MOJANGLABEL = registerBeverage("whiskey_mojanglabel", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.HEARTHSTONE.get(), 2 * 20);
    public static final RegistrySupplier<Block> WHISKEY_LILITUSINGLEMALT = registerBeverage("whiskey_lilitusinglemalt", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.CUDDLYWARM.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_JOJANNIK = registerBeverage("whiskey_jojannik", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.DOUBLEJUMP.get(), 30 * 20);
    public static final RegistrySupplier<Block> WHISKEY_CRISTELWALKER = registerBeverage("whiskey_cristelwalker", () -> new BeverageBlock(getBeverageSettings()), EffectRegistry.SLIDING.get(), 30 * 20);
    //MISC
    public static final RegistrySupplier<Block> BEER_KEG = registerBI("beer_keg", () -> new BeerKegFlowerPotBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    //TODO barrel logic
    public static final RegistrySupplier<Block> SMALL_BARREL = registerBI("small_barrel", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item> BREATHALYZER = registerItem("breathalyzer", () -> new Breathalyzer(getSettings()));
    //ROPE
    public static final RegistrySupplier<Item> HOP_ROPE = registerItem("hop_rope", () -> new Rope(getSettings()));
    public static final RegistrySupplier<Block> HANGING_ROPE = registerB("hanging_rope", () -> new HangingRope(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL)));

    //FOOD
    //TODO nutrition / saturation values
    public static final RegistrySupplier<Item> PORK_KNUCKLE = registerItem("pork_knuckle", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> SAUSAGE = registerItem("sausage", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> POTATO_SALAD = registerItem("potato_salad", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    //TODO conditional recipes -> candlelight
    public static final RegistrySupplier<Item> DUMPLINGS = registerItem("dumplings", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> MASHED_POTATOES = registerItem("mashed_potatoes", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> FRIED_CHICKEN = registerItem("fried_chicken", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    //TODO conditional recipes -> bakery
    public static final RegistrySupplier<Item> PRETZEL = registerItem("pretzel", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    public static final RegistrySupplier<Item> GINGERBREAD = registerItem("gingerbread", () -> new SaturatedItem(getFoodItemSettings(6, 1.2f, EffectRegistry.SATURATED.get(), 6000)));
    //BREWFEST ARMOR
    //TODO HAT 3D MODEL, färbbar
    public static final RegistrySupplier<Item> BREWFEST_HAT = registerItem("brewfest_hat", () -> new ArmorItem(MaterialRegistry.BREWFEST_LEATHER, EquipmentSlot.HEAD, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> BREWFEST_DRESS = registerItem("brewfest_dress", () -> new DyeableBrewfestArmorItem(MaterialRegistry.BREWFEST_ARMOR, EquipmentSlot.CHEST, 0x800000, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BREWFEST_REGALIA = registerItem("brewfest_regalia", () -> new DyeableBrewfestArmorItem(MaterialRegistry.BREWFEST_ARMOR, EquipmentSlot.CHEST, 0xADD8E6, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> BREWFEST_TROUSERS = registerItem("brewfest_trousers", () -> new ArmorItem(MaterialRegistry.BREWFEST_LEATHER, EquipmentSlot.LEGS, getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BREWFEST_BOOTS = registerItem("brewfest_boots", () -> new ArmorItem(MaterialRegistry.BREWFEST_ARMOR, EquipmentSlot.FEET, getSettings().rarity(Rarity.UNCOMMON)));


    public static void register() {
        Brewery.LOGGER.debug("Registering Mod Block and Items for " + Brewery.MOD_ID);
        ITEMS.register();
        BLOCKS.register();
        createStandards();
    }

    public static final List<Supplier<Block>> STANDARD_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_WALL_BLOCKS = Lists.newArrayList();
    public static final List<Supplier<Block>> STANDARD_FLOOR_BLOCKS = Lists.newArrayList();
    public static Supplier<Block> BEER_STANDARD;

    private static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> item) {
        final ResourceLocation id = new BreweryIdentifier(path);
        return ITEM_REGISTRAR.register(id, item);
    }

    private static <T extends Block> RegistrySupplier<T> registerB(String path, Supplier<T> block) {
        final ResourceLocation id = new BreweryIdentifier(path);
        return BLOCK_REGISTRAR.register(id, block);
    }

    private static <T extends Block> RegistrySupplier<T> registerBI(String path, Supplier<T> block) {
        RegistrySupplier<T> blockSupplier = registerB(path, block);
        registerItem(path, () -> new BlockItem(blockSupplier.get(), getSettings()));
        return blockSupplier;
    }

    private static void createStandards() {

        BEER_STANDARD = BLOCKS.register(Brewery.MOD_ID("beer_standard"), () -> new BeerStandardBlock(properties(Material.WOOD, 1F).noCollission().sound(SoundType.WOOD)));
        Supplier<Block> adjWall = BLOCKS.register(Brewery.MOD_ID("beer_wall_standard"), () -> new BeerStandardWallBlock(properties(Material.WOOD, 1F).noCollission().sound(SoundType.WOOD).dropsLike(BEER_STANDARD.get())));

        ITEMS.register(Brewery.MOD_ID("beer_standard"), () -> new StandingAndWallBlockItem(BEER_STANDARD.get(), adjWall.get(), new Item.Properties().tab(Brewery.CREATIVE_TAB).stacksTo(16).rarity(Rarity.EPIC)));
        STANDARD_BLOCKS.add(BEER_STANDARD);
        STANDARD_BLOCKS.add(adjWall);
    }

    public static void commonInit() {
        FuelRegistry.register(300, BEER_STANDARD.get(), BEER_KEG.get(), SMALL_BARREL.get(), BENCH.get(), TABLE.get(), BAR_COUNTER.get());
        FuelRegistry.register(100, CORN.get(), BARLEY.get(), HOPS.get());
        FuelRegistry.register(75, PATTERNED_WOOL.get(), PATTERNED_CARPET.get());
        FuelRegistry.register(50, BREWFEST_BOOTS.get(), BREWFEST_HAT.get(), BREWFEST_DRESS.get(), BREWFEST_REGALIA.get(), BREWFEST_TROUSERS.get());
    }

    public static BlockBehaviour.Properties properties(Material material, float hardness) {
        return BlockBehaviour.Properties.of(material).strength(hardness, hardness);
    }

    private static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties().tab(Brewery.CREATIVE_TAB);
        consumer.accept(settings);
        return settings;
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration) {
        return getFoodItemSettings(nutrition, saturationMod, effect, duration, false, false);
    }

    private static Item.Properties getFoodItemSettings(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        return getSettings().food(createFood(nutrition, saturationMod, effect, duration, alwaysEat, fast));
    }

    private static BlockBehaviour.Properties getBeverageSettings() {
        return BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().instabreak();
    }


    private static BlockBehaviour.Properties getBushSettings() {
        return BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH);
    }

    private static FoodProperties createFood(int nutrition, float saturationMod, MobEffect effect, int duration, boolean alwaysEat, boolean fast) {
        FoodProperties.Builder food = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
        if (alwaysEat) food.alwaysEat();
        if (fast) food.fast();
        if (effect != null) food.effect(new MobEffectInstance(effect, duration), 1.0f);
        return food.build();
    }


    private static <T extends Block> RegistrySupplier<T> registerBeverage(String name, Supplier<T> block, MobEffect effect, int duration) {
        RegistrySupplier<T> toReturn = registerWithoutItem(name, block);
        registerItem(name, () -> new DrinkBlockItem(toReturn.get(), getSettings(settings -> settings.food(beverageFoodComponent(effect, duration)))));
        return toReturn;
    }

    private static FoodProperties beverageFoodComponent(MobEffect effect, int durationInTicks) {
        FoodProperties.Builder component = new FoodProperties.Builder().nutrition(2).saturationMod(1);
        if (effect != null) {
            component.effect(new MobEffectInstance(effect, durationInTicks), 1.0f);
        }
        return component.build();
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return registerWithItem(name, block, Brewery.CREATIVE_TAB);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block, @Nullable CreativeModeTab tab) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new BreweryIdentifier(name), block, tab);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new BreweryIdentifier(path), block);
    }
}