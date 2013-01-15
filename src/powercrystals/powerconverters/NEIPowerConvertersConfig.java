package powercrystals.powerconverters;

import powercrystals.powerconverters.PowerConverterCore;
import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIPowerConvertersConfig implements IConfigureNEI
{

    @Override
    public void loadConfig()
    {
        try
        {
            addSubSet();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void addSubSet()
    {
        MultiItemRange subTypes = new MultiItemRange();
        subTypes.add(PowerConverterCore.converterBlockCommon, 0, 0);
        subTypes.add(PowerConverterCore.converterBlockBuildCraft, 0, 1);
        subTypes.add(PowerConverterCore.converterBlockIndustrialCraft, 0, 7);
        subTypes.add(PowerConverterCore.converterBlockSteam, 0, 1);
        subTypes.add(PowerConverterCore.converterBlockUniversalElectricity, 0, 5);
        API.addSetRange("Blocks.PowerConverters", subTypes);
    }

    @Override
    public String getName()
    {
        return PowerConverterCore.modName;
    }

    @Override
    public String getVersion()
    {
        return PowerConverterCore.version;
    }
}
