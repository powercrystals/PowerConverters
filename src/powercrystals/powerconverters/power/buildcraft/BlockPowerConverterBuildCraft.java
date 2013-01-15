package powercrystals.powerconverters.power.buildcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPowerConverterBuildCraft extends BlockPowerConverter
{
	public BlockPowerConverterBuildCraft(int blockId)
	{
		super(blockId);
		setBlockName("powerConverterBC");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		int offset = ((TileEntityBridgeComponent<?>)world.getBlockTileEntity(x, y, z)).isSideConnectedClient(side) ? 1 : 0;
		return getBlockTextureFromSideAndMetadata(side, world.getBlockMetadata(x, y, z)) + offset;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		if(metadata == 0) return 0;
		if(metadata == 1) return 2;
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityBuildCraftConsumer();
		else if(metadata == 1) return new TileEntityBuildCraftProducer();
		
		return createNewTileEntity(world);
	}
}
