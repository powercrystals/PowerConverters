package powercrystals.powerconverters.power.buildcraft;

import net.minecraft.item.ItemStack;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.power.PowerSystem;
import thermalexpansion.api.item.IChargeableItem;

public class ChargeHandlerThermalExpansion implements IChargeHandler
{
	@Override
	public PowerSystem getPowerSystem()
	{
		return PowerConverterCore.powerSystemBuildCraft;
	}

	@Override
	public boolean canHandle(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof IChargeableItem;
	}

	@Override
	public int charge(ItemStack stack, int energyInput)
	{
		int mj = energyInput / getPowerSystem().getInternalEnergyPerOutput();
		mj -= ((IChargeableItem)stack.getItem()).receiveEnergy(stack, mj, true);
		return mj * getPowerSystem().getInternalEnergyPerOutput();
	}

	@Override
	public int discharge(ItemStack stack, int energyRequest)
	{
		int mj = energyRequest / getPowerSystem().getInternalEnergyPerInput();
		mj = (int)((IChargeableItem)stack.getItem()).transferEnergy(stack, mj, true);
		return mj * getPowerSystem().getInternalEnergyPerInput();
	}
}
