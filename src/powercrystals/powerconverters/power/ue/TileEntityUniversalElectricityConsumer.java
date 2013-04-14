package powercrystals.powerconverters.power.ue;

import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IVoltage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;

public class TileEntityUniversalElectricityConsumer extends TileEntityEnergyConsumer<IConductor> implements IVoltage
{
	private int _wattsLastTick;
	
	public TileEntityUniversalElectricityConsumer()
	{
		this(0);
	}
	
	public TileEntityUniversalElectricityConsumer(int voltageIndex)
	{
		super(PowerConverterCore.powerSystemUniversalElectricity, voltageIndex, IConductor.class);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			int desiredWatts = getTotalEnergyDemand() / PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerInput();
		
			ElectricityPack powerRequested = new ElectricityPack(desiredWatts / getVoltage(), getVoltage());
			ElectricityPack powerPack = ElectricityNetworkHelper.consumeFromMultipleSides(this, powerRequested);
			double watts = powerPack.getWatts();
		
			storeEnergy(MathHelper.floor_double(watts * PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerInput()));
			_wattsLastTick = (int)watts;
		}
	}

	@Override
	public double getVoltage()
	{
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}

	@Override
	public int getInputRate()
	{
		return _wattsLastTick;
	}
}
