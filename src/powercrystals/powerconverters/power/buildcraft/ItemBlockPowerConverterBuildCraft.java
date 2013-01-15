package powercrystals.powerconverters.power.buildcraft;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterBuildCraft extends ItemBlock
{
	public ItemBlockPowerConverterBuildCraft(int id)
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
		if(md == 0) return "bcConsumer";
		if(md == 1) return "bcProducer";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i <= 1; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
