package powercrystals.powerconverters.power.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;

public class TileEntityPowerConverterFactorizationConsumer extends TileEntityEnergyConsumer<IChargeConductor> implements IChargeConductor
{
	private Charge _charge = new Charge(this);
	private int _chargeLasTtick = 0;
	private static final int _maxCG = 1000;

	public TileEntityPowerConverterFactorizationConsumer()
	{
		super(PowerConverterCore.powerSystemFactorization, 0, IChargeConductor.class);
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (this._charge.getValue() < _maxCG)
		{
			this._charge.update();
		}
		if (this._charge.getValue() > 0)
		{
			int used = this._charge.tryTake(this._charge.getValue());
			this._chargeLasTtick = MathHelper.floor_float(used);
			this.storeEnergy((int) (used * PowerConverterCore.powerSystemFactorization.getInternalEnergyPerInput()));
		}
		else
		{
			this._chargeLasTtick = 0;
		}
	}

	@Override
	public int getInputRate()
	{
		return this._chargeLasTtick;
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
