package powercrystals.powerconverters.power;

import java.util.Map.Entry;

import powercrystals.powerconverters.common.TileEntityEnergyBridge;

import net.minecraftforge.common.ForgeDirection;

public abstract class TileEntityEnergyConsumer<T> extends TileEntityBridgeComponent<T>
{
	public TileEntityEnergyConsumer(PowerSystem powerSystem, int voltageNameIndex, Class<T> adjacentClass)
	{
		super(powerSystem, voltageNameIndex, adjacentClass);
	}
	
	protected int storeEnergy(int energy)
	{
		for(Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet())
		{
			energy = bridge.getValue().storeEnergy(energy);
			if(energy <= 0)
			{
				return 0;
			}
		}
		return energy;
	}
	
	protected int getTotalEnergyDemand()
	{
		int demand = 0;
		
		for(Entry<ForgeDirection, TileEntityEnergyBridge> bridge : getBridges().entrySet())
		{
			demand += (bridge.getValue().getEnergyStoredMax() - bridge.getValue().getEnergyStored());
		}
		
		return demand;
	}
	
	public abstract int getInputRate();
}
