package powercrystals.powerconverters.power.railcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;

public class BlockPowerConverterRailCraft extends BlockPowerConverter
{
	public BlockPowerConverterRailCraft(int blockId)
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
		if(metadata == 0) return 22;
		if(metadata == 1) return 24;
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityRailCraftConsumer();
		else if(metadata == 1) return new TileEntityRailCraftProducer();
		
		return createNewTileEntity(world);
	}
}
