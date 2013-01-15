package powercrystals.powerconverters.power.railcraft;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;

public class TileEntityRailCraftConsumer extends TileEntityEnergyConsumer<ITankContainer> implements ITankContainer
{
	private LiquidTank _steamTank;
	private int _mBLastTick;
	
	public TileEntityRailCraftConsumer()
	{
		super(PowerSystem.Steam, 0, ITankContainer.class);
		_steamTank = new LiquidTank(1 * LiquidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(_steamTank != null && _steamTank.getLiquid() != null)
		{
			int amount = _steamTank.getLiquid().amount;
			int energy = amount * PowerSystem.Steam.getInternalEnergyPerInput();
			energy = storeEnergy(energy);
			int toDrain = amount - (energy / PowerSystem.Steam.getInternalEnergyPerInput());
			_steamTank.drain(toDrain, true);
			_mBLastTick = toDrain;
		}
		else
		{
			_mBLastTick = 0;
		}
	}
	
	@Override
	public int getVoltageNameIndex()
	{
		return 0;
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID != PowerConverterCore.steamId)
		{
			return 0;
		}
		return _steamTank.fill(resource, doFill);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		if(resource == null || resource.itemID != PowerConverterCore.steamId)
		{
			return 0;
		}
		return _steamTank.fill(resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain)
	{
		return null;
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction)
	{
		return new ILiquidTank [] { _steamTank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		if(type != null && type.itemID == PowerConverterCore.steamId)
		{
			return _steamTank;
		}
		return null;
	}

	@Override
	public int getInputRate()
	{
		return _mBLastTick;
	}
}
