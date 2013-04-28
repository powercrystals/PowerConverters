package powercrystals.powerconverters.power.buildcraft;

import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.power.PowerProviderAdvanced;

public class PowerProviderPowerConverter extends PowerProviderAdvanced
{
	private TileEntityBuildCraftConsumer _consumer;
	
	public PowerProviderPowerConverter(TileEntityBuildCraftConsumer consumer)
	{
		_consumer = consumer;
		configure(25, 2, 100, 1, 1000);
	}
	
	@Override
	public void receiveEnergy(float quantity, ForgeDirection from)
	{
		_consumer.receiveEnergy(quantity);
	}
	
	@Override
	public int getMaxEnergyReceived()
	{
		return _consumer.powerRequest(ForgeDirection.UNKNOWN);
	}
}
