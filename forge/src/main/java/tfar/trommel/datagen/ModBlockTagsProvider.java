package tfar.trommel.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.craftingstation.CraftingStation;
import tfar.craftingstation.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput,lookup, CraftingStation.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.crafting_station,ModBlocks.crafting_station_slab);
        tag(Tags.Blocks.PLAYER_WORKSTATIONS_CRAFTING_TABLES).add(ModBlocks.crafting_station,ModBlocks.crafting_station_slab);
    }
}
