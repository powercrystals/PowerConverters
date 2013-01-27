package powercrystals.powerconverters.gui;

import powercrystals.powerconverters.PowerConverterCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class PCCreativeTab extends CreativeTabs
{
	public static final PCCreativeTab tab = new PCCreativeTab();

	public PCCreativeTab()
	{
		super("Power Converters");
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(PowerConverterCore.converterBlockCommon, 1, 0);
	}

	@Override
	public String getTranslatedTabLabel()
	{
		return this.getTabLabel();
	}
}
