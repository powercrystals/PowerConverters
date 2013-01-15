package powercrystals.powerconverters.gui;

import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class PCGUIHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityEnergyBridge)
		{
			return new ContainerEnergyBridge((TileEntityEnergyBridge)te, player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityEnergyBridge)
		{
			return new GuiEnergyBridge(new ContainerEnergyBridge((TileEntityEnergyBridge)te, player.inventory), (TileEntityEnergyBridge)te);
		}
		return null;
	}
}
