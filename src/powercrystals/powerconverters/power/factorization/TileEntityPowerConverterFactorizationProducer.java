package powercrystals.powerconverters.power.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import java.util.Map.Entry;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityPowerConverterFactorizationProducer extends TileEntityEnergyProducer<IChargeConductor> implements IChargeConductor
{
	private Charge _charge = new Charge(this);
	private static final int _maxCG = 1000;

	public TileEntityPowerConverterFactorizationProducer()
	{
		super(PowerConverterCore.powerSystemFactorization, 0, IChargeConductor.class);
	}

	@Override
	public int produceEnergy(int energy)
	{
		int CG = energy / PowerConverterCore.powerSystemFactorization.getInternalEnergyPerOutput();
		for(Entry<ForgeDirection, IChargeConductor> output : this.getTiles().entrySet())
		{
			IChargeConductor o = output.getValue();
			if(o != null)
			{
				if(o.getCharge().getValue() < _maxCG)
				{
					int store = Math.min(_maxCG - o.getCharge().getValue(), CG);
					o.getCharge().addValue(store);
					CG -= store;
					if(CG <= 0)
					{
						break;
					}
				}
			}
		}
		return CG * PowerConverterCore.powerSystemFactorization.getInternalEnergyPerOutput();
	}

	@Override
	public Charge getCharge()
	{
		return this._charge;
	}

	@Override
	public String getInfo()
	{
		return null;
	}

	@Override
	public Coord getCoord()
	{
		return new Coord(this);
	}
}
