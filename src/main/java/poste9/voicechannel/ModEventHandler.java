package poste9.voicechannel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class ModEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			VCM.logger.info("Registering extended properties for player");
		}
	}
	
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event) {
		// If you have any non-DataWatcher fields in your extended properties that
		// need to be synced to the client, you must send a packet each time the
		// player joins the world; this takes care of dying, changing dimensions, etc.
		if (event.entity instanceof EntityPlayerMP) {
			VCM.logger.info("Player joined world, sending extended properties to client");
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent event) {
		VCM.logger.info("Cloning player extended properties");
	}
}
