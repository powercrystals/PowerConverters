package powercrystals.powerconverters.power.buildcraft;

import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerFramework;

public class TileEntityBuildCraftConsumer extends TileEntityEnergyConsumer<IPowerReceptor> implements IPowerReceptor
{
	private IPowerProvider _powerProvider;
	private int _mjLastTick = 0;
	
	public TileEntityBuildCraftConsumer()
	{
		super(PowerConverterCore.powerSystemBuildCraft, 0, IPowerReceptor.class);
		_powerProvider = PowerFramework.currentFramework.createPowerProvider();
		_powerProvider.configure(25, 10, 10, 1, 1000);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(getPowerProvider() != null)
		{
			getPowerProvider().update(this);
		}
		
		int mjStored = MathHelper.floor_float(_powerProvider.getEnergyStored());
		if(mjStored > 0)
		{
			float used = _powerProvider.useEnergy(1, mjStored, true);
			_mjLastTick = MathHelper.floor_float(used);
			storeEnergy((int)(used * PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerInput()));
		}
		else
		{
			_mjLastTick = 0;
		}
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
	public int powerRequest()
	{
		return getTotalEnergyDemand() / PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerInput();
	}

	@Override
	public int getInputRate()
	{
		return _mjLastTick;
	}
}
