package poste9.voicechannel;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class VoiceMessage implements IMessage {

	private byte[] packet;
	
	public VoiceMessage()
	{
		packet = null;
	}
	
	public void set_packet( byte[] packet )
	{
		this.packet = packet;
	}
	
	public VoiceMessage(byte[] packet)
	{
		this.packet = packet;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		packet = buf.array();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBytes( packet );		
	}
	
	public static class Handler implements IMessageHandler<VoiceMessage, IMessage> {

		@Override
		public IMessage onMessage(VoiceMessage message, MessageContext ctx) {
			
			switch ( ctx.side )
			{
			case CLIENT:
				
				break;
			case SERVER:
				//System.out.println( "Received " + message.packet.length + " bytes " + " from " + ctx.getServerHandler().playerEntity.getDisplayName() );

				break;
			default:
				break;
			
			}
			
			return null;
		}
		
	}

}
