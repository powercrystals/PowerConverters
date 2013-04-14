package powercrystals.powerconverters.power.factorization;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;

public class BlockPowerConverterFactorization extends BlockPowerConverter
{
	public BlockPowerConverterFactorization(int blockId)
	{
		super(blockId, 2);
		setUnlocalizedName("powerconverters.fz");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityPowerConverterFactorizationConsumer();
		else if(metadata == 1) return new TileEntityPowerConverterFactorizationProducer();
		
		return createNewTileEntity(world);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir)
	{
		_icons[0] = ir.registerIcon("powercrystals/powerconverters/" + getUnlocalizedName() + ".consumer.off");
		_icons[1] = ir.registerIcon("powercrystals/powerconverters/" + getUnlocalizedName() + ".consumer.on");
		_icons[2] = ir.registerIcon("powercrystals/powerconverters/" + getUnlocalizedName() + ".producer.off");
		_icons[3] = ir.registerIcon("powercrystals/powerconverters/" + getUnlocalizedName() + ".producer.on");
	}
}
