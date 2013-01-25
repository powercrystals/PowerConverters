package powercrystals.powerconverters.power.ue;

import java.util.EnumSet;
import java.util.Map.Entry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityConnections;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.implement.IConductor;
import universalelectricity.core.implement.IVoltage;

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
		ElectricityConnections.registerConnector(this, EnumSet.range(ForgeDirection.DOWN, ForgeDirection.EAST));
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		double watts = 0;
		boolean foundInput = false;
		
		for(Entry<ForgeDirection, IConductor> conductor : getTiles().entrySet())
		{
			ElectricityNetwork inputNetwork = ElectricityNetwork.getNetworkFromTileEntity((TileEntity)conductor.getValue(), conductor.getKey());
			
			if(inputNetwork != null)
			{
				if(foundInput || getTotalEnergyDemand() <= 0)
				{
					inputNetwork.stopRequesting(this);
				}
				else
				{
					int desiredWatts = getTotalEnergyDemand() / PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerInput();
					inputNetwork.startRequesting(this, desiredWatts / getVoltage(), getVoltage());
					ElectricityPack pack = inputNetwork.consumeElectricity(this);
					
					if(UniversalElectricity.isVoltageSensitive)
					{
						if (pack.voltage > this.getVoltage())
						{
							this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 2f, true);
						}
					}
					
					watts += pack.getWatts();
					foundInput = true;
				}
			}
		}
		
		storeEnergy(MathHelper.floor_double(watts * PowerConverterCore.powerSystemUniversalElectricity.getInternalEnergyPerInput()));
		_wattsLastTick = (int)watts;
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
