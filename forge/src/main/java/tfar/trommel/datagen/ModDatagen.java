package tfar.trommel.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

public class ModDatagen {

    public static void gather(GatherDataEvent e) {
        DataGenerator dataGenerator = e.getGenerator();
        PackOutput packOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = e.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = e.getLookupProvider();
        dataGenerator.addProvider(e.includeServer(),new ModBlockTagsProvider(packOutput,lookup,existingFileHelper));
        dataGenerator.addProvider(e.includeServer(),ModLootTableProvider.create(packOutput));
        dataGenerator.addProvider(e.includeClient(),new ModBlockStateProvider(packOutput,existingFileHelper));
        dataGenerator.addProvider(e.includeClient(),new ModItemModelProvider(packOutput,existingFileHelper));
        dataGenerator.addProvider(e.includeClient(),new ModLangProvider(packOutput));

    }
}
