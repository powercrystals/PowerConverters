package powercrystals.powerconverters.common;

import powercrystals.powerconverters.power.PowerSystem;
import net.minecraft.item.ItemStack;

public interface IChargeHandler
{
	public PowerSystem getPowerSystem();
	
	boolean canHandle(ItemStack stack);
	
	int charge(ItemStack stack, int energyInput);
	
	int discharge(ItemStack stack, int energyRequest);
}
