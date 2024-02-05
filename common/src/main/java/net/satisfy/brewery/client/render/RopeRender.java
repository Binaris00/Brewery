package net.satisfy.brewery.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.satisfy.brewery.client.model.RopeModel;
import net.satisfy.brewery.util.rope.RopeHelper;
import net.satisfy.brewery.util.rope.UVCord;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RopeRender {
    private static final float SCALE = 2.0F;
    private static final float QUALITY = 4.0F;
    private static final int MAX_SEGMENTS = 2048;
    private static final Vec3 POSITIVE_Y = new Vec3(0.0F, 1.0F, 0.0F);
    private static final Vec3 NEGATIVE_Y = new Vec3(0.0F, -1.0F, 0.0F);
    private final Map<Integer, RopeModel> models = new HashMap<>(256);

    public void render(final VertexConsumer vertexConsumer, final PoseStack poseStack, final Vec3 ropeVec, final int entityId, final int blockLight0, final int blockLight1, final int skyLight0, final int skyLight1) {
        int hash = getVectorHash(ropeVec, entityId);
        RopeModel model;
        if (models.containsKey(hash)) {
            model = models.get(hash);
        } else {
            model = buildModel(ropeVec);
            models.put(hash, model);
        }
        model.render(vertexConsumer, poseStack, blockLight0, blockLight1, skyLight0, skyLight1);
    }

    private RopeModel buildModel(final Vec3 ropeVec) {
        float desiredSegmentLength = 1F / 4;
        int initialCapacity = (int) (2F * ropeVec.lengthSqr() / desiredSegmentLength);
        RopeModel.Builder builder = RopeModel.builder(initialCapacity);

        createModel(builder, ropeVec, 45, UVCord.DEFAULT_ROPE_H);
        createModel(builder, ropeVec, -45, UVCord.DEFAULT_ROPE_V);

        return builder.build();
    }

    private void createModel(final RopeModel.Builder builder, final Vec3 ropeVec, final int degrees, final UVCord uv) {
        float length = (float) ropeVec.length();
        Vec3 ropeNormal = ropeVec.normalize();

        Quaternionf quaternion = new Quaternionf().rotationAxis(degrees * (float)Math.PI / 180, (float) ropeNormal.x, (float) ropeNormal.y, (float) ropeNormal.z);

        Vector3f crossVec = new Vector3f((float) ropeNormal.x, (float) ropeNormal.y, (float) ropeNormal.z);
        quaternion.transform(crossVec);
        crossVec.mul(((uv.x1() - uv.x0()) / 16.0F) * SCALE);
        crossVec.mul(0.5F);


        float uvStart, uvEnd = 0;
        double segmentLength = Math.min(length, 1.0F / QUALITY), actuallySegmentLength;
        Vector3f currentPos = new Vector3f(), lastPos = new Vector3f();
        Vec3 tempVec = ropeNormal.multiply(segmentLength, segmentLength, segmentLength);
        Vector3f segmentVector = new Vector3f((float) tempVec.x, (float) tempVec.y, (float) tempVec.z), segmentPos = new Vector3f();

        boolean lastIter = false, straight = (ropeVec.x == 0 && ropeVec.z == 0) || RopeHelper.HANGING_AMOUNT == 0;
        for (int segment = 0; segment < MAX_SEGMENTS; segment++) {
            lastPos = new Vector3f(currentPos);
            segmentPos.add(segmentVector);

            if (straight || new Vec3(segmentPos).length() > length) {
                lastIter = true;
                segmentPos.set((float) ropeVec.x, (float) ropeVec.y, (float) ropeVec.z);
            }

            currentPos = new Vector3f(segmentPos);
            if (!straight)
                currentPos.add(0.0F, (float) RopeHelper.getYHanging(new Vec3(segmentPos).length(), ropeVec), 0.0F); // add hanging

            actuallySegmentLength = new Vec3(currentPos).distanceTo(new Vec3(lastPos));

            uvStart = uvEnd;
            uvEnd += (float) (actuallySegmentLength / SCALE);

            builder.vertex(lastPos.x() - crossVec.x(), lastPos.y() - crossVec.y(), lastPos.z() - crossVec.z()).uv(uv.x0() / 16.0F, uvStart).next();
            builder.vertex(lastPos.x() + crossVec.x(), lastPos.y() + crossVec.y(), lastPos.z() + crossVec.z()).uv(uv.x1() / 16.0F, uvStart).next();
            builder.vertex(currentPos.x() + crossVec.x(), currentPos.y() + crossVec.y(), currentPos.z() + crossVec.z()).uv(uv.x1() / 16.0F, uvEnd).next();
            builder.vertex(currentPos.x() - crossVec.x(), currentPos.y() - crossVec.y(), currentPos.z() - crossVec.z()).uv(uv.x0() / 16.0F, uvEnd).next();

            if (lastIter) break;
        }
    }

    private int getVectorHash(Vec3 ropeVec, int id) {
        return Objects.hash(id, ropeVec.x, ropeVec.y, ropeVec.z);
    }
}
