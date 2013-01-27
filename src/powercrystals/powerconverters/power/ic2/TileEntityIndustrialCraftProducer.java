package powercrystals.powerconverters.power.ic2;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityIndustrialCraftProducer extends TileEntityEnergyProducer<IEnergyAcceptor> implements IEnergySource
{
	private boolean _isAddedToEnergyNet;
	private boolean _didFirstAddToNet;
	
	public TileEntityIndustrialCraftProducer()
	{
		this(0);
	}
	
	public TileEntityIndustrialCraftProducer(int voltageIndex)
	{
		super(PowerConverterCore.powerSystemIndustrialCraft, voltageIndex, IEnergyAcceptor.class);
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
	public int produceEnergy(int energy)
	{
		if(!_isAddedToEnergyNet)
		{
			return energy;
		}
		
		int eu = energy / PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
		if(eu < getMaxEnergyOutput())
		{
			return energy;
		}
		int producedEu = Math.min(eu, getMaxEnergyOutput());
		EnergyTileSourceEvent e = new EnergyTileSourceEvent(this, producedEu);
		MinecraftForge.EVENT_BUS.post(e);
		
		return (eu - (producedEu - e.amount)) * PowerConverterCore.powerSystemIndustrialCraft.getInternalEnergyPerOutput();
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
		return getPowerSystem().getVoltageValues()[getVoltageIndex()];
	}
}
