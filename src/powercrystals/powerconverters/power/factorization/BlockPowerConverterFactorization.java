package powercrystals.powerconverters.power.factorization;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;

public class BlockPowerConverterFactorization extends BlockPowerConverter
{
	public BlockPowerConverterFactorization(int blockId)
	{
		super(blockId);
		setBlockName("powerConverterFZ");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityPowerConverterFactorizationConsumer();
		else if(metadata == 1) return new TileEntityPowerConverterFactorizationProducer();
		
		return createNewTileEntity(world);
	}

	@Override
	public int getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
		int offset = ((TileEntityBridgeComponent<?>) world.getBlockTileEntity(x, y, z)).isSideConnectedClient(side) ? 1 : 0;
		return getBlockTextureFromSideAndMetadata(side, world.getBlockMetadata(x, y, z)) + offset;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		if(metadata == 0) return 42;
		else if(metadata == 1) return 43;
		return 0;
	}
}
