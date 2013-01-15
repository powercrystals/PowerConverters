package powercrystals.powerconverters.power;

import net.minecraftforge.common.Configuration;

public enum PowerSystem
{
	BuildCraft("BuildCraft", "BC", 4375, 4375, null, "MJ/t"),
	IndustrialCraft2("IndustrialCraft", "IC2", 1800, 1800, new String[] { "LV", "MV", "HV", "EV" }, "EU/t"),
	Steam("Steam", "STEAM", 875, 875, null, "mB/t"),
	UniversalElectricity("UniversalElectricity", "UE", 10, 10, new String[] { "LV", "MV", "HV" }, "W");
	
	private String _abbreviation;
	private String _name;
	private int _internalEnergyPerInput;
	private int _internalEnergyPerOutput;
	private String[] _voltageNames;
	private String _unit;
	
	private PowerSystem(String name, String abbreviation, int energyPerInput, int energyPerOutput, String[] voltageNames, String unit)
	{
		_name = name;
		_abbreviation = abbreviation;
		_internalEnergyPerInput = energyPerInput;
		_internalEnergyPerOutput = energyPerOutput;
		_voltageNames = voltageNames;
		_unit = unit;
	}
	
	public String getAbbreviation()
	{
		return _abbreviation;
	}
	
	public String getName()
	{
		return _name;
	}
	
	public void loadConfig(Configuration c)
	{
		_internalEnergyPerInput = c.get("RATIOS", _name + "InternalEnergyPerEachInput", _internalEnergyPerInput).getInt();
		_internalEnergyPerOutput = c.get("RATIOS", _name + "InternalEnergyPerEachOutput", _internalEnergyPerOutput).getInt();
	}
	
	public int getInternalEnergyPerInput()
	{
		return _internalEnergyPerInput;
	}
	
	public int getInternalEnergyPerOutput()
	{
		return _internalEnergyPerOutput;
	}
	
	public String[] getVoltageNames()
	{
		return _voltageNames;
	}
	
	public String getUnit()
	{
		return _unit;
	}
}
