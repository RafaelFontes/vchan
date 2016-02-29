package poste9.voicechannel;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ModProxyClient extends ModProxy {
	public EntityPlayer getPlayerEntity( MessageContext ctx )
	{
		return (ctx.side.isClient()) ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx);
	}
}