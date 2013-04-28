package powercrystals.powerconverters.power.buildcraft;

import java.util.Map.Entry;

import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import powercrystals.core.power.PowerProviderAdvanced;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityBuildCraftProducer extends TileEntityEnergyProducer<IPowerReceptor> implements IPowerReceptor
{
	private IPowerProvider _powerProvider;
	
	public TileEntityBuildCraftProducer()
	{
		super(PowerConverterCore.powerSystemBuildCraft, 0, IPowerReceptor.class);
		_powerProvider = new PowerProviderAdvanced();
		_powerProvider.configure(0, 0, 0, 0, 0);
	}
	
	@Override
	public int produceEnergy(int energy)
	{
		int mj = energy / PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerOutput();
		
		for(Entry<ForgeDirection, IPowerReceptor> output : getTiles().entrySet())
		{
			IPowerProvider pp = output.getValue().getPowerProvider();
			if(pp != null && pp.preConditions(output.getValue()) && pp.getMinEnergyReceived() <= mj)
			{
				int mjUsed = Math.min(Math.min(pp.getMaxEnergyReceived(), mj), pp.getMaxEnergyStored() - (int)Math.floor(pp.getEnergyStored()));
				pp.receiveEnergy(mjUsed, output.getKey());
				
				mj -= mjUsed;
				if(mj <= 0)
				{
					return 0;
				}
			}
		}
		return mj * PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerOutput();
	}

	@Override
	public void setPowerProvider(IPowerProvider provider)
	{
		_powerProvider = provider;
	}

	@Override
	public IPowerProvider getPowerProvider()
	{
		return _powerProvider;
	}

	@Override
	public void doWork()
	{
	}

	@Override
	public int powerRequest(ForgeDirection from)
	{
		return 0;
	}
}
