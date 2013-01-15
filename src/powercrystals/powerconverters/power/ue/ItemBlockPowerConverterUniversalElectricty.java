package powercrystals.powerconverters.power.ue;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterUniversalElectricty extends ItemBlock
{
	public ItemBlockPowerConverterUniversalElectricty(int id)
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
		if(md == 0) return "ueLVConsumer";
		if(md == 1) return "ueLVProducer";
		if(md == 2) return "ueMVConsumer";
		if(md == 3) return "ueMVProducer";
		if(md == 4) return "ueHVConsumer";
		if(md == 5) return "ueHVProducer";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i <= 5; i++)
        {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
