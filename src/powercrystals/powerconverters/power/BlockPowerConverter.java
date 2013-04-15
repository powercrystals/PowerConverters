package powercrystals.powerconverters.power;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPowerConverter extends BlockContainer
{
	protected Icon[] _icons;
	
	public BlockPowerConverter(int blockId, int metaCount)
	{
		super(blockId, Material.clay);
		setHardness(1.0F);
		_icons = new Icon[metaCount * 2];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		int offset = ((TileEntityBridgeComponent<?>)world.getBlockTileEntity(x, y, z)).isSideConnectedClient(side) ? 1 : 0;
		return _icons[world.getBlockMetadata(x, y, z) * 2 + offset];
	}
	
	@Override
	public Icon getIcon(int side, int metadata)
	{
		return _icons[metadata * 2];
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof INeighboorUpdateTile)
		{
			((INeighboorUpdateTile)te).onNeighboorChanged();
			world.markBlockForUpdate(x, y, z);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBridgeComponent<?>)
		{
			TileEntityEnergyBridge bridge = ((TileEntityBridgeComponent<?>)te).getFirstBridge();
			if(bridge != null)
			{
				player.openGui(PowerConverterCore.instance, 0, world, bridge.xCoord, bridge.yCoord, bridge.zCoord);
			}
		}
		return true;
	}
}
