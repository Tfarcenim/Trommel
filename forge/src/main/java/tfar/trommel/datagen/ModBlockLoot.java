package tfar.trommel.datagen;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import tfar.trommel.Init;
import tfar.trommel.Trommel;

public class ModBlockLoot extends VanillaBlockLoot {

    public ModBlockLoot() {
        super();
    }

    @Override
    protected void generate() {
        dropSelf(Init.BLOCK);
        dropSelf(Init.MESH);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Trommel.getKnownBlocks().toList();
    }
}
