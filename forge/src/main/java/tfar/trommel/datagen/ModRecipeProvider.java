package tfar.trommel.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import tfar.trommel.Init;
import tfar.trommel.Trommel;
import tfar.trommel.init.ModItems;
import tfar.trommel.recipe.RangedEntry;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,Blocks.COBBLESTONE)
                .define('c', ModItems.PEBBLE)
                .pattern("cc")
                .pattern("cc")
                .unlockedBy(getHasName(ModItems.PEBBLE),has(ModItems.PEBBLE))
                .save(pWriter,Trommel.id("cobblestone_from_pebbles"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Init.MESH)
                .define('i', Items.IRON_INGOT)
                .pattern("iii")
                .pattern("i i")
                .pattern("iii")
                .unlockedBy(getHasName(Items.IRON_INGOT),has(Items.IRON_INGOT))
                .save(pWriter,Trommel.id("mesh"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Init.BLOCK)
                .define('m', ModItems.MESH)
                .define('c', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('l', ItemTags.LOGS)

                .pattern("ccc")
                .pattern("cmc")
                .pattern("lll")
                .unlockedBy(getHasName(ModItems.MESH),has(ModItems.MESH))
                .save(pWriter,Trommel.id(Trommel.MOD_ID));

        //Note: This loot table applies to dirt, dirt path, farmland, grass, retro grass, scorched dirt and scorched grass.
        //
        //Dirt Trommeling
        //Item	Amount	Chance
        //Pebble	1-3	60.24%
        //Clay	1-5	24.10%
        //Flint	1-3	12.05%
        //Sulphur	1	2.41%
        //Raw Iron Ore	1	0.6%
        //Olivine	1	0.3%
        //Quartz	1	0.3%

        TrommelRecipeBuilder.trommel(Blocks.DIRT,50,.5)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,3),200)
                .addEntry(new RangedEntry(Items.CLAY_BALL,1,5),80)
                .addEntry(new RangedEntry(Items.FLINT,1,3),40)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1),8)
                .addEntry(new RangedEntry(Items.RAW_IRON,1),2)
                .addEntry(new RangedEntry(Items.AMETHYST_SHARD,1),1)
                .addEntry(new RangedEntry(Items.QUARTZ,1),1)
                .save(pWriter, Trommel.id("dirt"))
;
        TrommelRecipeBuilder.trommel(Blocks.GRASS_BLOCK,50,.5)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,3),200)
                .addEntry(new RangedEntry(Items.CLAY_BALL,1,5),80)
                .addEntry(new RangedEntry(Items.FLINT,1,3),40)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1),8)
                .addEntry(new RangedEntry(Items.RAW_IRON,1),2)
                .addEntry(new RangedEntry(Items.AMETHYST_SHARD,1),1)
                .addEntry(new RangedEntry(Items.QUARTZ,1),1)
                .save(pWriter, Trommel.id("grass"))
        ;


        //Block of Clay Trommeling
        //Item	Amount	Chance
        //Clay	4-8	49.18%
        //Pebble	1-3	32.79%
        //Sulphur	1	16.39%
        //Raw Gold Ore	1	1.64%

        TrommelRecipeBuilder.trommel(Blocks.CLAY,50,.5)
                .addEntry(new RangedEntry(Items.CLAY_BALL,4,8),30)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,3),20)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1),10)
                .addEntry(new RangedEntry(Items.RAW_GOLD,1),1)
                .save(pWriter, Trommel.id("clay"));

        //Gravel Trommeling
        //Item	Amount	Chance
        //Flint	1-2	24.88%
        //Olivine	1-3	29.85%
        //Pebble	1-6	24.88%
        //Raw Iron Ore	1-2	9.95%
        //Lapis Lazuli	2-6	4.98%
        //Sulphur	1	4.98%
        //Quartz	1	0.5%

        TrommelRecipeBuilder.trommel(Blocks.GRAVEL,50,.5)
                .addEntry(new RangedEntry(Items.AMETHYST_SHARD,1,3),60)
                .addEntry(new RangedEntry(Items.FLINT,1,2),50)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,6),50)
                .addEntry(new RangedEntry(Items.RAW_IRON,1,2),20)
                .addEntry(new RangedEntry(Items.LAPIS_LAZULI,2,6),10)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1),10)
                .addEntry(new RangedEntry(Items.QUARTZ,1),1)
                .save(pWriter, Trommel.id("gravel"))
        ;

        //Sand Trommeling
        //Item	Amount	Chance
        //Quartz	1-3	36.76%
        //Clay	4-8	22.06%
        //Pebble	1-5	18.38%
        //Bone	1-3	7.35%
        //Flint	1-3	7.35%
        //Sulphur	1	3.68%
        //Raw Gold Ore	1	0.74%
        //Olivine	1	3.68%

        TrommelRecipeBuilder.trommel(ItemTags.SAND,50,.5)
                .addEntry(new RangedEntry(Items.QUARTZ,1,3),50)
                .addEntry(new RangedEntry(Items.CLAY_BALL,1,2),30)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,6),25)
                .addEntry(new RangedEntry(Items.BONE,1,2),10)
                .addEntry(new RangedEntry(Items.FLINT,2,6),10)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1),5)
                .addEntry(new RangedEntry(Items.AMETHYST_SHARD,1),5)
                .addEntry(new RangedEntry(Items.RAW_GOLD,1),1)
                .save(pWriter, Trommel.id("sand"))
        ;

        //Soul Sand Trommeling
        //Item	Amount	Chance
        //Flint	1-3	45.98%
        //Bone	1-6	22.99%
        //Glowstone Dust	1-6	11.49%
        //Quartz	1-3	11.49%
        //Raw Gold Ore	1	4.6%
        //Raw Iron Ore	1	2.3%
        //Nether Coal	1	1.15%

        TrommelRecipeBuilder.trommel(Blocks.SOUL_SAND,50,.5)
                .addEntry(new RangedEntry(Items.FLINT,2,6),40)
                .addEntry(new RangedEntry(Items.BONE,1,2),20)
                .addEntry(new RangedEntry(Items.QUARTZ,1,3),10)
                .addEntry(new RangedEntry(Items.GLOWSTONE_DUST,1),10)
                .addEntry(new RangedEntry(Items.RAW_GOLD,1),4)
                .addEntry(new RangedEntry(Items.RAW_IRON,1),2)
                .addEntry(new RangedEntry(Items.COAL,1),1)
                .save(pWriter, Trommel.id("soul_sand"))
        ;
    }
}
