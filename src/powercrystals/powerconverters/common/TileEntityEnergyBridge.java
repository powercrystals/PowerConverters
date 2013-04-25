package powercrystals.powerconverters.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityEnergyBridge extends TileEntity implements INeighboorUpdateTile
{
	private int _energyStored;
	private int _energyStoredMax = PowerConverterCore.bridgeBufferSize.getInt();
	private int _energyScaledClient;
	
	private int _energyStoredLast;
	private boolean _isInputLimited;
	
	private Map<ForgeDirection, TileEntityEnergyProducer<?>> _producerTiles;
	private Map<ForgeDirection, BridgeSideData> _clientSideData;
	private Map<ForgeDirection, Integer> _producerOutputRates;
	
	private boolean _initialized;
	
	public TileEntityEnergyBridge()
	{
		_producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
		_clientSideData = new HashMap<ForgeDirection, BridgeSideData>();
		_producerOutputRates = new HashMap<ForgeDirection, Integer>();
		for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
		{
			_clientSideData.put(d, new BridgeSideData());
			_producerOutputRates.put(d, 0);
		}
	}
	
	public int getEnergyStored()
	{
		return _energyStored;
	}
	
	public int getEnergyStoredMax()
	{
		return _energyStoredMax;
	}
	
	public int storeEnergy(int energy)
	{
		int toStore = Math.min(energy, _energyStoredMax - _energyStored);
		_energyStored += toStore;
		return energy - toStore;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!_initialized)
		{
			onNeighboorChanged();
			_initialized = true;
		}

		if(!worldObj.isRemote)
		{
			int energyRemaining = Math.min(_energyStored, _energyStoredMax);
			int energyNotProduced = 0;
			for(Entry<ForgeDirection, TileEntityEnergyProducer<?>> prod : _producerTiles.entrySet())
			{
				if(energyRemaining > 0)
				{
					energyNotProduced = prod.getValue().produceEnergy(energyRemaining);
					if(energyNotProduced > energyRemaining)
					{
						energyNotProduced = energyRemaining;
					}
					_producerOutputRates.put(prod.getKey(), (energyRemaining - energyNotProduced) / prod.getValue().getPowerSystem().getInternalEnergyPerOutput());
					energyRemaining = energyNotProduced;
				}
				else
				{
					prod.getValue().produceEnergy(0);
					_producerOutputRates.put(prod.getKey(), 0);
				}
			}
			_energyStored = Math.max(0, energyRemaining);
			
			if((_energyStored == _energyStoredLast && _energyStored == _energyStoredMax) || _energyStored > _energyStoredLast)
			{
				_isInputLimited = false;
			}
			else
			{
				_isInputLimited = true;
			}
			
			_energyStoredLast = _energyStored;
		}
	}

	@Override
	public void onNeighboorChanged()
	{
		Map<ForgeDirection, TileEntityEnergyProducer<?>> producerTiles = new HashMap<ForgeDirection, TileEntityEnergyProducer<?>>();
		for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
		{
			BlockPosition p = new BlockPosition(this);
			p.orientation = d;
			p.moveForwards(1);
			TileEntity te = worldObj.getBlockTileEntity(p.x, p.y, p.z);
			if(te != null && te instanceof TileEntityEnergyProducer)
			{
				producerTiles.put(d, (TileEntityEnergyProducer<?>)te);
			}
		}
		_producerTiles = producerTiles;
	}
	
	public BridgeSideData getDataForSide(ForgeDirection dir)
	{
		if(!worldObj.isRemote)
		{
			BridgeSideData d = new BridgeSideData();
			BlockPosition p = new BlockPosition(this);
			p.orientation = dir;
			p.moveForwards(1);
			
			TileEntity te = worldObj.getBlockTileEntity(p.x, p.y, p.z);
			if(te != null && te instanceof TileEntityBridgeComponent)
			{
				if(te instanceof TileEntityEnergyConsumer)
				{
					d.isConsumer = true;
					d.outputRate = ((TileEntityEnergyConsumer<?>)te).getInputRate();
				}
				if(te instanceof TileEntityEnergyProducer)
				{
					d.isProducer = true;
					d.outputRate = _producerOutputRates.get(dir);
				}
				TileEntityBridgeComponent<?> c = (TileEntityBridgeComponent<?>)te;
				d.powerSystem = c.getPowerSystem();
				d.isConnected = c.isConnected();
				d.side = dir;
				d.voltageNameIndex = c.getVoltageIndex();
			}
			
			return d;
		}
		else
		{
			return _clientSideData.get(dir);
		}
	}
	
	public boolean isInputLimited()
	{
		return _isInputLimited;
	}
	
	@SideOnly(Side.CLIENT)
	public void setIsInputLimited(boolean isInputLimited)
	{
		_isInputLimited = isInputLimited;
	}
	
	public int getEnergyScaled()
	{
		if(worldObj.isRemote)
		{
			return _energyScaledClient;
		}
		else
		{
			return 120 * _energyStored / _energyStoredMax;
		}
	}
	
	public void setEnergyScaled(int scaled)
	{
		_energyScaledClient = scaled;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound)
	{
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("energyStored", _energyStored);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound)
	{
		super.readFromNBT(par1nbtTagCompound);
		_energyStored = par1nbtTagCompound.getInteger("energyStored");
	}
}
