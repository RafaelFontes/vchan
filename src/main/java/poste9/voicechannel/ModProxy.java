package poste9.voicechannel;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ModProxy  {
	public EntityPlayer getPlayerEntity( MessageContext ctx )
	{
		return ctx.getServerHandler().playerEntity;
	}
}