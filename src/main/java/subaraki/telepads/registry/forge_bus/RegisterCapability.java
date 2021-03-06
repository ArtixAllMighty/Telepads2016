package subaraki.telepads.registry.forge_bus;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import subaraki.telepads.capability.player.CapabilityTelepadProvider;
import subaraki.telepads.mod.Telepads;

@EventBusSubscriber(bus = Bus.FORGE, modid = Telepads.MODID)
public class RegisterCapability {

    @SubscribeEvent
    public static void onAttachEventEntity(AttachCapabilitiesEvent<Entity> event)
    {

        final Object entity = event.getObject();

        if (entity instanceof PlayerEntity)
            event.addCapability(CapabilityTelepadProvider.KEY, new CapabilityTelepadProvider((PlayerEntity) entity));
    }
}
