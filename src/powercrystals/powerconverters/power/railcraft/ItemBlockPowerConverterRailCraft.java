package powercrystals.powerconverters.power.railcraft;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterRailCraft extends ItemBlock
{
	public ItemBlockPowerConverterRailCraft(int id)
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
		if(md == 0) return "powerconverters.steam.consumer";
		if(md == 1) return "powerconverters.steam.producer";
		return "unknown";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
    {
        for (int i = 0; i <= 1; i++)
        {
            subTypes.add(new ItemStack(itemId, 1, i));
        }
    }
}
