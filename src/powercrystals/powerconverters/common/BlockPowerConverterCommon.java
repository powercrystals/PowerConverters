package powercrystals.powerconverters.common;

import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
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
	public int getBlockTextureFromSideAndMetadata(int side, int meta)
	{
		if(meta == 0) return 5;
		else if(meta == 2) return 38;
		
		return 4;
	}
	
	@Override
    public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityBridgeComponent<?>)
		{
			int offset = ((TileEntityBridgeComponent<?>)te).isSideConnectedClient(side) ? 1 : 0;
			return getBlockTextureFromSideAndMetadata(side, world.getBlockMetadata(x, y, z)) + offset;
		}
		
    	return getBlockTextureFromSideAndMetadata(side, world.getBlockMetadata(x, y, z));
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
		if(md == 2) return new TileEntityCharger();
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
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityBridgeComponent<?>)
		{
			TileEntityEnergyBridge bridge = ((TileEntityBridgeComponent<?>)te).getFirstBridge();
			if(bridge != null)
			{
				player.openGui(PowerConverterCore.instance, 0, world, bridge.xCoord, bridge.yCoord, bridge.zCoord);
			}
		}
		else if(te != null && te instanceof TileEntityEnergyBridge)
		{
			player.openGui(PowerConverterCore.instance, 0, world, x, y, z);
		}
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
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 2)
		{
			float shrinkAmount = 0.125F;
			return AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(x, y, z, x + 1, y + 1 - shrinkAmount, z + 1);
		}
		else
		{
			return super.getCollisionBoundingBoxFromPool(world, x, y, z);
		}
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(entity instanceof EntityPlayer && world.getBlockMetadata(x, y, z) == 2)
		{
			TileEntityCharger charger = (TileEntityCharger)world.getBlockTileEntity(x, y, z);
			charger.setPlayer((EntityPlayer)entity);
		}
	}
}
