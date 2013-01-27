package powercrystals.powerconverters.power.ic2;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPowerConverterIndustrialCraft extends BlockPowerConverter
{
	public BlockPowerConverterIndustrialCraft(int blockId)
	{
		super(blockId);
		setBlockName("powerConverterIC2");
		setCreativeTab(PCCreativeTab.tab);
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
		if(metadata == 0) return 6;
		if(metadata == 1) return 8;
		if(metadata == 2) return 10;
		if(metadata == 3) return 12;
		if(metadata == 4) return 14;
		if(metadata == 5) return 16;
		if(metadata == 6) return 18;
		if(metadata == 7) return 20;
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityIndustrialCraftConsumer(0);
		else if(metadata == 1) return new TileEntityIndustrialCraftProducer(0);
		else if(metadata == 2) return new TileEntityIndustrialCraftConsumer(1);
		else if(metadata == 3) return new TileEntityIndustrialCraftProducer(1);
		else if(metadata == 4) return new TileEntityIndustrialCraftConsumer(2);
		else if(metadata == 5) return new TileEntityIndustrialCraftProducer(2);
		else if(metadata == 6) return new TileEntityIndustrialCraftConsumer(3);
		else if(metadata == 7) return new TileEntityIndustrialCraftProducer(3);
		
		return createNewTileEntity(world);
	}
}
