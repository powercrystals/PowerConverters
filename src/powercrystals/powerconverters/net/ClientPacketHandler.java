package powercrystals.powerconverters.net;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import powercrystals.core.net.PacketWrapper;
import powercrystals.core.position.INeighboorUpdateTile;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler
{
	@SuppressWarnings("rawtypes")
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int packetID = PacketWrapper.readPacketID(data);
		
		if (packetID == 0)
		{
			Class[] decodeAs = {Integer.class, Integer.class, Integer.class, Integer.class};
			Object[] packetReadout = PacketWrapper.readPacketData(data, decodeAs);
			
			World world = FMLClientHandler.instance().getClient().theWorld;
			int x = (Integer)packetReadout[0];
			int y = (Integer)packetReadout[1];
			int z = (Integer)packetReadout[2];
			
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if (te != null && te instanceof INeighboorUpdateTile)
			{
				((INeighboorUpdateTile)te).onNeighboorChanged();
			}
		}
	}
}
