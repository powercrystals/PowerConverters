package powercrystals.powerconverters.net;

import net.minecraftforge.client.MinecraftForgeClient;
import powercrystals.powerconverters.PowerConverterCore;

public class ProxyClient implements IPCProxy
{
	@Override
	public void load()
	{
        MinecraftForgeClient.preloadTexture(PowerConverterCore.itemTexture);
        MinecraftForgeClient.preloadTexture(PowerConverterCore.terrainTexture);
	}

}
