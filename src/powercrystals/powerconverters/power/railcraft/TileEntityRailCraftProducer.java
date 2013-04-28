package powercrystals.powerconverters.power.railcraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import powercrystals.core.position.BlockPosition;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityRailCraftProducer extends TileEntityEnergyProducer<ITankContainer> implements ITankContainer
{
	private LiquidTank _tank;
	
	public TileEntityRailCraftProducer()
	{
		super(PowerConverterCore.powerSystemSteam, 0, ITankContainer.class);
		_tank = new LiquidTank(1);
	}
	
	@Override
	public int produceEnergy(int energy)
	{
		int steam = Math.min(energy / PowerConverterCore.powerSystemSteam.getInternalEnergyPerOutput(), PowerConverterCore.throttleSteamProducer.getInt());
		for(int i = 0; i < 6; i++)
		{
			BlockPosition bp = new BlockPosition(this);
			bp.orientation = ForgeDirection.getOrientation(i);
			bp.moveForwards(1);
			TileEntity te = worldObj.getBlockTileEntity(bp.x, bp.y, bp.z);
			
			if(te != null && te instanceof ITankContainer)
			{
				steam -= ((ITankContainer)te).fill(bp.orientation.getOpposite(), new LiquidStack(PowerConverterCore.steamId, steam), true);
			}
			if(steam <= 0)
			{
				return 0;
			}
		}

		return steam * PowerConverterCore.powerSystemSteam.getInternalEnergyPerOutput();
	}

	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill)
	{
		return 0;
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill)
	{
		return 0;
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
		return new ILiquidTank[] { _tank };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type)
	{
		return _tank;
	}
}
