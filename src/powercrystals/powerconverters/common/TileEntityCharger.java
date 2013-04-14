package powercrystals.powerconverters.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityCharger extends TileEntityEnergyProducer<IInventory>
{
	private static List<IChargeHandler> _chargeHandlers = new ArrayList<IChargeHandler>();
	private EntityPlayer _player;
	
	public static void registerChargeHandler(IChargeHandler handler)
	{
		_chargeHandlers.add(handler);
	}
	
	public TileEntityCharger()
	{
		super(PowerConverterCore.powerSystemIndustrialCraft, 0, IInventory.class);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(_player != null && _player.getDistance(xCoord, yCoord, zCoord) > 2D)
		{
			setPlayer(null);
		}
	}
	
	@Override
	public int produceEnergy(int energy)
	{
		if(energy == 0)
		{
			return 0;
		}
		
		int energyRemaining = energy;
		if(_player != null)
		{
			energyRemaining = chargeInventory(_player.inventory, ForgeDirection.UNKNOWN, energyRemaining);
		}
		
		for(Entry<ForgeDirection, IInventory> inv : getTiles().entrySet())
		{
			energyRemaining = chargeInventory(inv.getValue(), inv.getKey(), energyRemaining);
		}
		
		return energyRemaining;
	}
	
	private int chargeInventory(IInventory inventory, ForgeDirection toSide, int energy)
	{
		PowerSystem nextPowerSystem = getPowerSystem();
		int energyRemaining = energy;
		
		IInventoryManager inv = InventoryManager.create(inventory, toSide.getOpposite());
		for(Entry<Integer, ItemStack> contents : inv.getContents().entrySet())
		{
			for(IChargeHandler chargeHandler : _chargeHandlers)
			{
				ItemStack s = contents.getValue();
				if(s == null)
				{
					continue;
				}
				
				if(chargeHandler.canHandle(s))
				{
					energyRemaining = chargeHandler.charge(s, energyRemaining);
					if(energyRemaining < energy)
					{
						nextPowerSystem = chargeHandler.getPowerSystem();
						energy = energyRemaining;
					}
				}
			}
		}
		
		_powerSystem = nextPowerSystem;
		return energyRemaining;
	}
	
	public void setPlayer(EntityPlayer player)
	{
		_player = player;
		if(worldObj.isRemote)
		{
			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	public boolean isConnected()
	{
		return super.isConnected() || _player != null;
	}
	
	@Override
	public boolean isSideConnected(int side)
	{
		if(side == 1 && _player != null) return true;
		return super.isSideConnected(side);
	}
	
	@Override
	public boolean isSideConnectedClient(int side)
	{
		if(side == 1 && _player != null) return true;
		return super.isSideConnectedClient(side);
	}
}
