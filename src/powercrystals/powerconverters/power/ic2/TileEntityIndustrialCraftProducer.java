package powercrystals.powerconverters.power.ic2;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityIndustrialCraftProducer extends TileEntityEnergyProducer<IEnergyAcceptor> implements IEnergySource
{
	private boolean _isAddedToEnergyNet;
	private boolean _didFirstAddToNet;
	private int _voltageMax;
	
	public TileEntityIndustrialCraftProducer(int voltageIndex, int voltageMax)
	{
		super(PowerSystem.IndustrialCraft2, voltageIndex, IEnergyAcceptor.class);
		_voltageMax = voltageMax;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!_didFirstAddToNet)
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			_didFirstAddToNet = true;
			_isAddedToEnergyNet = true;
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
			_isAddedToEnergyNet = false;
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
		}
		super.invalidate();
	}
	
	@Override
	public int produceEnergy(int energy)
	{
		int eu = energy / PowerSystem.IndustrialCraft2.getInternalEnergyPerOutput();
		if(eu < getMaxEnergyOutput())
		{
			return energy;
		}
		int producedEu = Math.min(eu, getMaxEnergyOutput());
		EnergyTileSourceEvent e = new EnergyTileSourceEvent(this, producedEu);
		MinecraftForge.EVENT_BUS.post(e);
		
		return (eu - (producedEu - e.amount)) * PowerSystem.IndustrialCraft2.getInternalEnergyPerOutput();
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction)
	{
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet()
	{
		return _isAddedToEnergyNet;
	}

	@Override
	public int getMaxEnergyOutput()
	{
		return _voltageMax;
	}
}
