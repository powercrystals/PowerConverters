package powercrystals.powerconverters.power.ue;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;

public class BlockPowerConverterUniversalElectricity extends BlockPowerConverter
{
	public BlockPowerConverterUniversalElectricity(int blockId)
	{
		super(blockId);
		setBlockName("powerConverterUE");
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
		if(metadata == 0) return 26;
		if(metadata == 1) return 28;
		if(metadata == 2) return 30;
		if(metadata == 3) return 32;
		if(metadata == 4) return 34;
		if(metadata == 5) return 36;
		return 0;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityUniversalElectricityConsumer(0);
		else if(metadata == 1) return new TileEntityUniversalElectricityProducer(0);
		else if(metadata == 2) return new TileEntityUniversalElectricityConsumer(1);
		else if(metadata == 3) return new TileEntityUniversalElectricityProducer(1);
		else if(metadata == 4) return new TileEntityUniversalElectricityConsumer(2);
		else if(metadata == 5) return new TileEntityUniversalElectricityProducer(2);
		
		return createNewTileEntity(world);
	}
}
