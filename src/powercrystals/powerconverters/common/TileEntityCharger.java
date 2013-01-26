package powercrystals.powerconverters.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import cpw.mods.fml.common.Loader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;
import powercrystals.powerconverters.power.ic2.ChargeHandlerIndustrialCraft;

public class TileEntityCharger extends TileEntityEnergyProducer<IInventory>
{
	private List<IChargeHandler> _chargeHandlers;
	private EntityPlayer _player;
	
	public TileEntityCharger()
	{
		super(PowerConverterCore.powerSystemIndustrialCraft, 0, IInventory.class);
		_chargeHandlers = new ArrayList<IChargeHandler>();
		if(Loader.isModLoaded("IC2"))
		{
			_chargeHandlers.add(new ChargeHandlerIndustrialCraft());
		}
		//if(Loader.isModLoaded("BasicComponents"))
		//{
		//	_chargeHandlers.add(new ChargeHandlerUniversalElectricity());
		//}
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
		
		if(energyRemaining < energy)
		{
			return energyRemaining;
		}
		
		for(Entry<ForgeDirection, IInventory> inv : getTiles().entrySet())
		{
			energyRemaining = chargeInventory(inv.getValue(), inv.getKey(), energyRemaining);
			if(energyRemaining < energy)
			{
				return energyRemaining;
			}
		}
		
		return energyRemaining;
	}
	
	private int chargeInventory(IInventory inventory, ForgeDirection toSide, int energy)
	{
		int invStart = 0;
		int invEnd = inventory.getSizeInventory();
		int energyRemaining = energy;
		
		if(toSide != ForgeDirection.UNKNOWN && inventory instanceof ISidedInventory)
		{
			invStart = ((ISidedInventory)inventory).getStartInventorySide(toSide.getOpposite());
			invEnd = invStart + ((ISidedInventory)inventory).getSizeInventorySide(toSide.getOpposite());
		}
		
		for(int i = invStart; i < invEnd; i++)
		{
			for(IChargeHandler chargeHandler : _chargeHandlers)
			{
				ItemStack s = inventory.getStackInSlot(i);
				if(s == null)
				{
					continue;
				}
				
				if(chargeHandler.canHandle(s))
				{
					energyRemaining = chargeHandler.charge(s, energyRemaining);
					if(energyRemaining < energy)
					{
						_powerSystem = chargeHandler.getPowerSystem();
						System.out.println("Power System set to " + getPowerSystem().getName());
						return energyRemaining;
					}
				}
			}
		}
		
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
