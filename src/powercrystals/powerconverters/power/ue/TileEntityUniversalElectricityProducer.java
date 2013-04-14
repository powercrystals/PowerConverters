package powercrystals.powerconverters.power.ue;

import net.minecraft.util.MathHelper;

import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IVoltage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;
public class TileEntityUniversalElectricityProducer extends TileEntityEnergyProducer<IConductor> implements IVoltage
{
	public TileEntityUniversalElectricityProducer()
	{
		this(0);
	}
	
	public TileEntityUniversalElectricityProducer(int voltageIndex)
	{
		super(PowerConverterCore.powerSystemUniversalElectricity, voltageIndex, IConductor.class);
	}

	@Override
	public int produceEnergy(int energy)
	{
		double watts = energy / PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerOutput();
		
		ElectricityPack powerRemaining = ElectricityNetworkHelper.produceFromMultipleSides(this, new ElectricityPack(watts / getVoltage(), getVoltage()));
		
		return MathHelper.floor_double(powerRemaining.getWatts() * PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerOutput());
	}

	@Override
	public double getVoltage()
	{
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}
}