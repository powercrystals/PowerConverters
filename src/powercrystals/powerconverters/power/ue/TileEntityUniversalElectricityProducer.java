package powercrystals.powerconverters.power.ue;

import java.util.EnumSet;
import java.util.Map.Entry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;
import universalelectricity.core.electricity.Electricity;
import universalelectricity.core.electricity.ElectricityConnections;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.implement.IConductor;
import universalelectricity.core.implement.IVoltage;

public class TileEntityUniversalElectricityProducer extends TileEntityEnergyProducer<IConductor> implements IVoltage
{
	public TileEntityUniversalElectricityProducer()
	{
		this(0);
	}
	
	public TileEntityUniversalElectricityProducer(int voltageIndex)
	{
		super(PowerConverterCore.powerSystemUniversalElectricity, voltageIndex, IConductor.class);
		ElectricityConnections.registerConnector(this, EnumSet.range(ForgeDirection.DOWN, ForgeDirection.EAST));
	}

	@Override
	public int produceEnergy(int energy)
	{
		double watts = energy / PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerOutput();
		
		for(Entry<ForgeDirection, IConductor> conductor : getTiles().entrySet())
		{
			ElectricityNetwork network = ElectricityNetwork.getNetworkFromTileEntity((TileEntity)conductor.getValue(), conductor.getKey());

			if(network != null)
			{
				double request = Math.min(network.getRequest().getWatts(), watts);
				if(request > 0)
				{
					conductor.getValue().getNetwork().startProducing(this, request / this.getVoltage(), this.getVoltage());
				}
				else
				{
					conductor.getValue().getNetwork().stopProducing(this);
				}
				watts -= request;
			}
			//break; // UE cannot handle multiple output faces, oh well~
		}
		
		return MathHelper.floor_double(watts * PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerOutput());
	}

	@Override
	public double getVoltage(Object... data)
	{
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}
	
	@Override
	public void invalidate()
	{
		Electricity.instance.unregister(this);
		super.invalidate();
	
	}
}