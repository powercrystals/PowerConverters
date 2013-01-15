package powercrystals.powerconverters.power.ic2;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterIndustrialCraft extends ItemBlock
{
	public ItemBlockPowerConverterIndustrialCraft(int id)
	{
		super(id);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int i)
	{
		return i;
	}
	
	@Override
	public int getIconFromDamage(int i)
	{
		return 0;
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack)
	{
		int md = itemstack.getItemDamage();
		if(md == 0) return "ic2LVConsumer";
		if(md == 1) return "ic2LVProducer";
		if(md == 2) return "ic2MVConsumer";
		if(md == 3) return "ic2MVProducer";
		if(md == 4) return "ic2HVConsumer";
		if(md == 5) return "ic2HVProducer";
		if(md == 6) return "ic2EVConsumer";
		if(md == 7) return "ic2EVProducer";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i <= 7; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
