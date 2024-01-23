package net.satisfy.brewery.block;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class WallStorageBlock extends StorageBlock {
    public WallStorageBlock(Properties settings, SoundEvent openSound, SoundEvent closeSound) {
        super(settings, openSound, closeSound);
    }

    public static final Map<Direction, VoxelShape> SHAPES = Util.make(new HashMap<>(), map -> {
        map.put(Direction.NORTH, Block.box(0,0,4, 16, 16, 16));
        map.put(Direction.SOUTH, Block.box(0,0,0, 16, 16, 12));
        map.put(Direction.WEST, Block.box(4,0,0, 16, 16, 16));
        map.put(Direction.EAST, Block.box(0,0,0, 12, 16, 16));
    });

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }
}
