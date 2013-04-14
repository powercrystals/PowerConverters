package powercrystals.powerconverters.power.factorization;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockPowerConverterFactorization extends ItemBlock
{
	public ItemBlockPowerConverterFactorization(int par1)
	{
		super(par1);
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
		if (md == 0) return "powerconverters.fz.consumer";
		if (md == 1) return "powerconverters.fz.producer";
		return "unknown";
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		for (int i = 0; i <= 1; i++)
		{
			subTypes.add(new ItemStack(itemId, 1, i));
		}
	}
}
