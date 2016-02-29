package poste9.voicechannel.net;

import poste9.voicechannel.VoiceChannelMod;
import poste9.voicechannel.VoiceMessage;
import poste9.voicechannel.VoiceMessage.Handler;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class VoicePacketDispatcher {
	
	private static byte packetId = 0;
	
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel( VoiceChannelMod.MOD_ID );
	
	public static final void registerPackets()
	{
		VoicePacketDispatcher.registerMessage( VoiceMessage.Handler.class, VoiceMessage.class, Side.SERVER );
		VoicePacketDispatcher.registerMessage( VoiceMessage.Handler.class, VoiceMessage.class, Side.CLIENT );
	}
	
	private static final void registerMessage(Class handler_class, Class message_class, Side side)
	{
		VoicePacketDispatcher.dispatcher.registerMessage( handler_class , message_class, packetId++, side );
	}
	
	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point )
	{
		VoicePacketDispatcher.dispatcher.sendToAllAround( message, point );
	}
	
	public static final void sendToAllAround( IMessage message, int dimension, double x, double y, double z, double range )
	{
		VoicePacketDispatcher.dispatcher.sendToAllAround( message, new NetworkRegistry.TargetPoint( dimension, x, y, z, range ) );
	}
	
	public static final void sendToAllAround( IMessage message, EntityPlayer player, double range )
	{
		VoicePacketDispatcher.dispatcher.sendToAllAround( message, new NetworkRegistry.TargetPoint( player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ , range ) );
	}
	
	public static final void sendToServer( IMessage message )
	{
		VoicePacketDispatcher.dispatcher.sendToServer( message );
	}
}
