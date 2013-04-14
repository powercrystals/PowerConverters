package powercrystals.powerconverters.power.buildcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPowerConverterBuildCraft extends BlockPowerConverter
{
	public BlockPowerConverterBuildCraft(int blockId)
	{
		super(blockId, 2);
		setUnlocalizedName("powerconverters.bc");
		setCreativeTab(PCCreativeTab.tab);
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		if(metadata == 0) return new TileEntityBuildCraftConsumer();
		else if(metadata == 1) return new TileEntityBuildCraftProducer();
		
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
