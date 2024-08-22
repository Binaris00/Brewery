package net.satisfy.brewery.neoforge.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.brewery.Brewery;
import net.satisfy.brewery.client.BreweryClient;
import net.satisfy.brewery.client.model.BeerElementalModel;
import net.satisfy.brewery.client.model.RopeKnotEntityModel;
import net.satisfy.brewery.client.render.*;
import net.satisfy.brewery.registry.EntityRegistry;
import net.satisfy.brewery.registry.ModelRegistry;

@EventBusSubscriber(modid = Brewery.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class BreweryClientForge {

    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        BreweryClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        BreweryClient.onInitializeClient();
    }

    public static void entityRendererSetup() {
        EntityModelLayerRegistry.register(ModelRegistry.BEER_ELEMENTAL, BeerElementalModel::createBodyLayer);
        EntityRendererRegistry.register(EntityRegistry.BEER_ELEMENTAL, BeerElementalRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.BEER_ELEMENTAL_ATTACK, BeerElementalAttackRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.ROPE_COLLISION, RopeCollisionEntityRenderer::new);

        EntityModelLayerRegistry.register(ModelRegistry.ROPE_KNOT, RopeKnotEntityModel::createBodyLayer);
        EntityRendererRegistry.register(EntityRegistry.ROPE_KNOT, RopeKnotRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HANGING_ROPE, HangingRopeRenderer::new);

    }
}
