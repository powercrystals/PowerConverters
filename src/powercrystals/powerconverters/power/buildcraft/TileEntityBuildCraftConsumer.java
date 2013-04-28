package powercrystals.powerconverters.power.buildcraft;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import buildcraft.api.power.IPowerProvider;
import buildcraft.api.power.IPowerReceptor;

public class TileEntityBuildCraftConsumer extends TileEntityEnergyConsumer<IPowerReceptor> implements IPowerReceptor
{
	private IPowerProvider _powerProvider;
	private int _mjLastTick = 0;
	private long _lastTickInjected;
	
	public TileEntityBuildCraftConsumer()
	{
		super(PowerConverterCore.powerSystemBuildCraft, 0, IPowerReceptor.class);
		_powerProvider = new PowerProviderPowerConverter(this);
		_powerProvider.configure(25, 2, 100, 1, 1000);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.getWorldTime() - _lastTickInjected > 1)
		{
			_lastTickInjected = worldObj.getWorldTime();
			_mjLastTick = 0;
		}
	}
	
	public void receiveEnergy(float energy)
	{
		if(_lastTickInjected != worldObj.getWorldTime())
		{
			_lastTickInjected = worldObj.getWorldTime();
			_mjLastTick = 0;
		}
		
		_mjLastTick += MathHelper.floor_float(energy);
		storeEnergy((int)(energy * PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerInput()));
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
		return getTotalEnergyDemand() / PowerConverterCore.powerSystemBuildCraft.getInternalEnergyPerInput();
	}

	@Override
	public int getInputRate()
	{
		return _mjLastTick;
	}
}
