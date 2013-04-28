package powercrystals.powerconverters.power;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.position.BlockPosition;
import powercrystals.core.position.INeighboorUpdateTile;
import powercrystals.powerconverters.common.TileEntityEnergyBridge;

public class TileEntityBridgeComponent<T> extends TileEntity implements INeighboorUpdateTile
{
	private Map<ForgeDirection, TileEntityEnergyBridge> _adjacentBridges = new HashMap<ForgeDirection, TileEntityEnergyBridge>();
	private Map<ForgeDirection, T> _adjacentTiles = new HashMap<ForgeDirection, T>();
	
	private Class<?> _adjacentClass;
	protected PowerSystem _powerSystem;
	protected int _voltageIndex;

	private boolean _initialized;
	
	protected TileEntityBridgeComponent(PowerSystem powersystem, int voltageNameIndex, Class<T> adjacentClass)
	{
		_powerSystem = powersystem;
		_voltageIndex = voltageNameIndex;
		_adjacentClass = adjacentClass;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!_initialized && !tileEntityInvalid)
		{
			onNeighboorChanged();
			_initialized = true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onNeighboorChanged()
	{
		Map<ForgeDirection, TileEntityEnergyBridge> adjacentBridges = new HashMap<ForgeDirection, TileEntityEnergyBridge>();
		Map<ForgeDirection, T> adjacentTiles = new HashMap<ForgeDirection, T>();
		
		for(ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity te = BlockPosition.getAdjacentTileEntity(this, d);
			if(te != null && te instanceof TileEntityEnergyBridge)
			{
				adjacentBridges.put(d, (TileEntityEnergyBridge)te);
			}
			else if(te != null && _adjacentClass.isAssignableFrom(te.getClass()))
			{
				adjacentTiles.put(d, (T)te);
			}
		}
		
		_adjacentBridges = adjacentBridges;
		_adjacentTiles = adjacentTiles;
	}
	
	public PowerSystem getPowerSystem()
	{
		return _powerSystem;
	}
	
	public boolean isConnected()
	{
		return _adjacentTiles.size() > 0;
	}
	
	public boolean isSideConnected(int side)
	{
		return _adjacentTiles.get(ForgeDirection.getOrientation(side)) != null;
	}
	
	public boolean isSideConnectedClient(int side)
	{
		TileEntity te = BlockPosition.getAdjacentTileEntity(this, ForgeDirection.getOrientation(side));
		return te != null && _adjacentClass.isAssignableFrom(te.getClass());
	}
	
	public int getVoltageIndex()
	{
		return _voltageIndex;
	}
	
	public TileEntityEnergyBridge getFirstBridge()
	{
		return _adjacentBridges.size() == 0 ? null : (TileEntityEnergyBridge)_adjacentBridges.values().toArray()[0];
	}
	
	protected Map<ForgeDirection, TileEntityEnergyBridge> getBridges()
	{
		return _adjacentBridges;
	}
	
	protected Map<ForgeDirection, T> getTiles()
	{
		return _adjacentTiles;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		if(_voltageIndex == 0)
		{
			_voltageIndex = tagCompound.getInteger("voltageIndex");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("voltageIndex", _voltageIndex);
	}
}
