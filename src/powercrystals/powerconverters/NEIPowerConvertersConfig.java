package powercrystals.powerconverters;

import powercrystals.powerconverters.PowerConverterCore;
import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Loader;

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
        if(Loader.isModLoaded("BuildCraft|Energy") || Loader.isModLoaded("ThermalExpansion|Energy")) subTypes.add(PowerConverterCore.converterBlockBuildCraft, 0, 1);
        if(Loader.isModLoaded("IC2")) subTypes.add(PowerConverterCore.converterBlockIndustrialCraft, 0, 7);
        if(Loader.isModLoaded("Railcraft")) subTypes.add(PowerConverterCore.converterBlockSteam, 0, 1);
        if(Loader.isModLoaded("BasicComponents")) subTypes.add(PowerConverterCore.converterBlockUniversalElectricity, 0, 7);
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
