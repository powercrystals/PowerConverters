package powercrystals.powerconverters.power.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import net.minecraft.util.MathHelper;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.TileEntityEnergyConsumer;

public class TileEntityPowerConverterFactorizationConsumer extends TileEntityEnergyConsumer<IChargeConductor> implements IChargeConductor {

    private Charge charge = new Charge(this);
    private int _Chargelasttick = 0;
    private static final int maxCG = 1000;

    public TileEntityPowerConverterFactorizationConsumer(PowerSystem powerSystem, int voltageNameIndex, Class adjacentClass) {
        super(powerSystem, voltageNameIndex, adjacentClass);
    }

    public TileEntityPowerConverterFactorizationConsumer() {
        this(PowerConverterCore.powerSystemFactorization, 0, IChargeConductor.class);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (this.charge.getValue() < maxCG) {
            this.charge.update();
        }
        if (this.charge.getValue() > 0) {
            int used = this.charge.tryTake(this.charge.getValue());
            this._Chargelasttick = MathHelper.floor_float(used);
            this.storeEnergy((int) (used * PowerConverterCore.powerSystemFactorization.getInternalEnergyPerInput()));
        } else {
            this._Chargelasttick = 0;
        }
    }

    @Override
    public int getInputRate() {
        return this._Chargelasttick;
    }

    @Override
    public Charge getCharge() {
        return this.charge;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Coord getCoord() {
        return new Coord(this);
    }
}
