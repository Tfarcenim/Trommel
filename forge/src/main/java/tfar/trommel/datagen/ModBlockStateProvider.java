package tfar.trommel.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import tfar.trommel.Init;
import tfar.trommel.Trommel;
import tfar.trommel.TrommelBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Trommel.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Init.MESH);
        getVariantBuilder(Init.BLOCK).forAllStates(state -> {
            Direction facing = state.getValue(LecternBlock.FACING);
            boolean lit = state.getValue(TrommelBlock.LIT);
            ModelFile modelFile;

            if (lit){
                modelFile = models().cube("trommel_lit",Trommel.id("block/trommel_bottom"),Trommel.id("block/trommel_top"),Trommel.id("block/trommel_front_lit"),
                        Trommel.id("block/trommel_back_lit"),Trommel.id("block/trommel_side"),Trommel.id("block/trommel_output"));
            } else {
                modelFile = models().cube("trommel",Trommel.id("block/trommel_bottom"),Trommel.id("block/trommel_top"),Trommel.id("block/trommel_front"),
                        Trommel.id("block/trommel_back"),Trommel.id("block/trommel_side"),Trommel.id("block/trommel_output"));
            }
            return ConfiguredModel.builder().modelFile(modelFile).rotationY((facing.get2DDataValue() + 3) % 4 * 90).build();
        });
    }
}
