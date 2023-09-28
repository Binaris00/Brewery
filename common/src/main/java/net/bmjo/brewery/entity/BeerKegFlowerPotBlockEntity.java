package net.bmjo.brewery.entity;

import net.bmjo.brewery.registry.BlockEntityRegistry;
import net.bmjo.brewery.util.BreweryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BeerKegFlowerPotBlockEntity extends BlockEntity {
    private Item flower;
    public static final String FLOWER_KEY ="flower";

    public BeerKegFlowerPotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.BEER_MUG_FLOWER_POT_BLOCK_ENTITY.get(), pos, state);
    }

    public Item getFlower() {
        return flower;
    }

    public void setFlower(Item flower) {
        this.flower = flower;
        setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        writeFlower(nbt, flower);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        flower = readFlower(nbt);
    }

    public void writeFlower(CompoundTag nbt, Item flower) {
        CompoundTag nbtCompound = new CompoundTag();
        if (flower != null) {
            flower.getDefaultInstance().save(nbtCompound);
        }
        nbt.put(FLOWER_KEY, nbtCompound);
    }

    public Item readFlower(CompoundTag nbt) {
        super.load(nbt);
        if(nbt.contains(FLOWER_KEY)) {
            CompoundTag nbtCompound = nbt.getCompound(FLOWER_KEY);
            if (!nbtCompound.isEmpty()) {
                return ItemStack.of(nbtCompound).getItem();
            }
        }
        return null;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void setChanged() {
        if(level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();

            for (ServerPlayer player : BreweryUtil.tracking((ServerLevel) level, getBlockPos())) {
                player.connection.send(updatePacket);
            }
        }
        super.setChanged();
    }
}