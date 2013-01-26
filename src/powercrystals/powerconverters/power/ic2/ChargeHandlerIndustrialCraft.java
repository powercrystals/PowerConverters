package powercrystals.powerconverters.power.ic2;

import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.power.PowerSystem;

public class ChargeHandlerIndustrialCraft implements IChargeHandler
{
	@Override
	public PowerSystem getPowerSystem()
	{
		return PowerConverterCore.powerSystemIndustrialCraft;
	}

	@Override
	public boolean canHandle(ItemStack stack)
	{
		if(stack == null || Item.itemsList[stack.itemID] == null || !(Item.itemsList[stack.itemID] instanceof IElectricItem))
		{
			return false;
		}
		return true;
	}

	@Override
	public int charge(ItemStack stack, int energyInput)
	{
		int eu = energyInput / PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
		eu -= ElectricItem.charge(stack, eu, ((IElectricItem)Item.itemsList[stack.itemID]).getTier(), false, false);
		return eu * PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
	}

	@Override
	public int discharge(ItemStack stack, int energyRequest)
	{
		int eu = energyRequest / PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerInput();
		eu = ElectricItem.discharge(stack, eu, ((IElectricItem)Item.itemsList[stack.itemID]).getTier(), false, false);
		return eu * PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerInput();
	}
}
