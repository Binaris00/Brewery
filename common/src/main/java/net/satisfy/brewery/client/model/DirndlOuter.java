package net.satisfy.brewery.client.model;

import de.cristelknight.doapi.DoApiRL;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;


public class DirndlOuter<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(DoApiRL.asResourceLocation("dirndl_outer"), "main");

    public DirndlOuter(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        modelPartData.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

        modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

        modelPartData.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 24).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}