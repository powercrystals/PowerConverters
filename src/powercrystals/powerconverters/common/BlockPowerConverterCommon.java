package powercrystals.powerconverters.common;

import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.powerconverters.PowerConverterCore;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPowerConverterCommon extends BlockContainer
{
	public BlockPowerConverterCommon(int i)
	{
		super(i, 0, Material.clay);
		setHardness(1.0F);
		setBlockName("powerConverterCommon");
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		return 5;
	}
	
	@Override
    public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
    	return getBlockTextureFromSideAndMetadata(side, iblockaccess.getBlockMetadata(x, y, z));
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof INeighboorUpdateTile)
		{
			((INeighboorUpdateTile)te).onNeighboorChanged();
		}
	}

    @Override
    public TileEntity createNewTileEntity(World world, int md)
    {
		if(md == 0) return new TileEntityEnergyBridge();
		return createNewTileEntity(world);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		player.openGui(PowerConverterCore.instance, 0, world, x, y, z);
		return true;
	}
	
	@Override
    public int damageDropped(int i)
	{
		return i;
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public String getTextureFile()
	{
		return PowerConverterCore.terrainTexture;
	}
}
