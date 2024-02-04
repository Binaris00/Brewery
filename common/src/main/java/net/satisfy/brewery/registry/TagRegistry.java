package net.satisfy.brewery.registry;

import net.minecraft.core.registries.Registries;
import net.satisfy.brewery.util.BreweryIdentifier;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagRegistry {
    public static final TagKey<Item> SEED_CONVERSION = TagKey.create(Registries.ITEM, new BreweryIdentifier("seed_conversion"));
}
