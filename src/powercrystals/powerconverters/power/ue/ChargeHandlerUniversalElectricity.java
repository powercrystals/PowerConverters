package powercrystals.powerconverters.power.ue;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.common.IChargeHandler;
import powercrystals.powerconverters.power.PowerSystem;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.IItemElectric;

public class ChargeHandlerUniversalElectricity implements IChargeHandler
{
	@Override
	public PowerSystem getPowerSystem()
	{
		return PowerConverterCore.powerSystemUniversalElectricity;
	}

	@Override
	public boolean canHandle(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof IItemElectric;
	}

	@Override
	public int charge(ItemStack stack, int energyInput)
	{
		IItemElectric item = (IItemElectric)Item.itemsList[stack.itemID];
		
		double wattsInput = energyInput / getPowerSystem().getInternalEnergyPerOutput();
		
		ElectricityPack consumed = item.onReceive(ElectricityPack.getFromWatts(wattsInput, 120), stack);
		
		return MathHelper.floor_double((wattsInput - consumed.getWatts()) * getPowerSystem().getInternalEnergyPerOutput());
	}

	@Override
	public int discharge(ItemStack stack, int energyRequest)
	{
		return 0;
	}
}
