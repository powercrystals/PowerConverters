package powercrystals.powerconverters.power.factorization;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import powercrystals.powerconverters.gui.PCCreativeTab;
import powercrystals.powerconverters.power.BlockPowerConverter;
import powercrystals.powerconverters.power.TileEntityBridgeComponent;

public class BlockPowerConverterFactorization extends BlockPowerConverter {

    public BlockPowerConverterFactorization(int blockId) {
        super(blockId);
        setBlockName("powerConverterFZ");
        setCreativeTab(PCCreativeTab.tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        switch (metadata) {
            case 0:
                return new TileEntityPowerConverterFactorizationConsumer();
            case 1:
                return new TileEntityPowerConverterFactorizationProducer();
        }
        return createNewTileEntity(world);
    }

    @Override
    public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        int offset = ((TileEntityBridgeComponent<?>) par1IBlockAccess.getBlockTileEntity(par2, par3, par4)).isSideConnectedClient(par5) ? 1 : 0;
        return getBlockTextureFromSideAndMetadata(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4)) + offset;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
        switch (par2) {
            case 0:
                return 42;
            case 1:
                return 43;
        }
        return 0;
    }
}
