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
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int md = itemstack.getItemDamage();
		if(md == 0) return "powerconverters.ue.lv.consumer";
		if(md == 1) return "powerconverters.ue.lv.producer";
		if(md == 2) return "powerconverters.ue.mv.consumer";
		if(md == 3) return "powerconverters.ue.mv.producer";
		if(md == 4) return "powerconverters.ue.hv.consumer";
		if(md == 5) return "powerconverters.ue.hv.producer";
		if(md == 6) return "powerconverters.ue.ev.consumer";
		if(md == 7) return "powerconverters.ue.ev.producer";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
    {
        for (int i = 0; i <= 7; i++)
        {
            subTypes.add(new ItemStack(itemId, 1, i));
        }
    }
}
