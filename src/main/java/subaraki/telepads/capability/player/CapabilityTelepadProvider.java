package subaraki.telepads.capability.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import subaraki.telepads.mod.Telepads;

public class CapabilityTelepadProvider implements ICapabilitySerializable<CompoundNBT> {

    /**
     * Unique key to identify the attached provider from others
     */
    public static final ResourceLocation KEY = new ResourceLocation(Telepads.MODID, "telepad_data");

    /**
     * The instance that we are providing
     */
    final TelepadData data = new TelepadData();

    /**
     * gets called before world is initiated. player.worldObj will return null here
     * !
     */
    public CapabilityTelepadProvider(PlayerEntity player) {

        data.setPlayer(player);
    }

    @Override
    public CompoundNBT serializeNBT() {

        return (CompoundNBT) TelePadDataCapability.CAPABILITY.writeNBT(data, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

        TelePadDataCapability.CAPABILITY.readNBT(data, null, nbt);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == TelePadDataCapability.CAPABILITY)
            return (LazyOptional<T>) LazyOptional.of(this::getImpl);

        return LazyOptional.empty();
    }
    
    private TelepadData getImpl() {

        if (data != null) {
            return data;
        }
        return new TelepadData();
    }

}
