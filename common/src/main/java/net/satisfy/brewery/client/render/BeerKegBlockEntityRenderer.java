package net.satisfy.brewery.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.satisfy.brewery.block.BeerKegFlowerPotBlock;
import net.satisfy.brewery.block.entity.BeerKegFlowerPotBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

import static de.cristelknight.doapi.client.ClientUtil.renderBlock;


public class BeerKegBlockEntityRenderer implements BlockEntityRenderer<BeerKegFlowerPotBlockEntity> {

    public BeerKegBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(BeerKegFlowerPotBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof BeerKegFlowerPotBlock) {
            Item item = entity.getFlower();
            matrices.pushPose();
            if (item instanceof BlockItem) {
                BlockState state = ((BlockItem) item).getBlock().defaultBlockState();
                matrices.translate(0f, 0.4f, 0f);
                renderBlock(state, matrices, vertexConsumers, entity);
            }
        }
        matrices.popPose();
    }

}