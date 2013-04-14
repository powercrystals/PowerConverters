package powercrystals.powerconverters.common;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterCommon extends ItemBlock
{
	public ItemBlockPowerConverterCommon(int i)
	{
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int md = itemstack.getItemDamage();
		if(md == 0) return "powerconverters.common.bridge";
		if(md == 2) return "powerconverters.common.charger";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
    {
        subTypes.add(new ItemStack(itemId, 1, 0));
        subTypes.add(new ItemStack(itemId, 1, 2));
    }
}
