package powercrystals.powerconverters.power.factorization;

import factorization.api.Charge;
import factorization.api.Coord;
import factorization.api.IChargeConductor;
import java.util.Map.Entry;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.powerconverters.PowerConverterCore;
import powercrystals.powerconverters.power.PowerSystem;
import powercrystals.powerconverters.power.TileEntityEnergyProducer;

public class TileEntityPowerConverterFactorizationProducer extends TileEntityEnergyProducer<IChargeConductor> implements IChargeConductor {

    private Charge charge = new Charge(this);
    private static final int maxCG = 1000;

    public TileEntityPowerConverterFactorizationProducer(PowerSystem powerSystem, int voltageNameIndex, Class<IChargeConductor> adjacentClass) {
        super(powerSystem, voltageNameIndex, adjacentClass);
    }

    public TileEntityPowerConverterFactorizationProducer() {
        super(PowerConverterCore.powerSystemFactorization, 0, IChargeConductor.class);
    }

    @Override
    public int produceEnergy(int energy) {
        int CG = energy / PowerConverterCore.powerSystemFactorization.getInternalEnergyPerInput();
        for (Entry<ForgeDirection, IChargeConductor> output : this.getTiles().entrySet()) {
            IChargeConductor o = output.getValue();
            if (o != null) {
                if (o.getCharge().getValue() < maxCG) {
                    int store = o.getCharge().getValue();
                    int rem = o.getCharge().addValue(CG);
                    rem -= store;
                    return rem * PowerConverterCore.powerSystemFactorization.getInternalEnergyPerInput();
                }
            }
        }
        return energy;
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
