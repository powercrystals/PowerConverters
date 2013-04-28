package powercrystals.powerconverters.power.ic2;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;

public class TileEntityIndustrialCraftConsumer extends TileEntityEnergyConsumer<IEnergyEmitter> implements IEnergySink
{
	private boolean _isAddedToEnergyNet;
	private boolean _didFirstAddToNet;
	private int _euLastTick;
	private long _lastTickInjected;
	
	public TileEntityIndustrialCraftConsumer()
	{
		this(0);
	}
	
	public TileEntityIndustrialCraftConsumer(int voltageIndex)
	{
		super(PowerConverterCore.powerSystemIndustrialCraft, voltageIndex, IEnergyEmitter.class);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!_didFirstAddToNet && !worldObj.isRemote)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			_didFirstAddToNet = true;
			_isAddedToEnergyNet = true;
		}
		
		if(worldObj.getWorldTime() - _lastTickInjected > 2)
		{
			_euLastTick = 0;
		}
	}
	
	@Override
	public void validate()
	{
		super.validate();
		if(!_isAddedToEnergyNet)
		{
			_didFirstAddToNet = false;
		}
	}
	
	@Override
	public void invalidate()
	{
		if(_isAddedToEnergyNet)
		{
			if(!worldObj.isRemote)
			{
				MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			}
			_isAddedToEnergyNet = false;
		}
		super.invalidate();
	}
	
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction)
	{
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet()
	{
		return _isAddedToEnergyNet;
	}

	@Override
	public int demandsEnergy()
	{
		return getTotalEnergyDemand() / PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerInput();
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount)
	{
		int pcuNotStored = storeEnergy(amount * PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerInput());
		int euNotStored = pcuNotStored / PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerInput();
		
		int euThisInjection = (amount - euNotStored);
		
		if(_lastTickInjected == worldObj.getWorldTime())
		{
			_euLastTick += euThisInjection;
		}
		else
		{
			_euLastTick = euThisInjection;
			_lastTickInjected = worldObj.getWorldTime();
		}
		
		return euNotStored;
	}

	@Override
	public int getMaxSafeInput()
	{
		if(getVoltageIndex() == 3) return Integer.MAX_VALUE;
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}

	@Override
	public int getInputRate()
	{
		return _euLastTick;
	}
}
