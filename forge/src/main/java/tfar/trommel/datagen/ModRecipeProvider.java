package tfar.trommel.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Blocks;
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

        //Block of Clay Trommeling
        //Item	Amount	Chance
        //Clay	4-8	49.18%
        //Pebble	1-3	32.79%
        //Sulphur	1	16.39%
        //Raw Gold Ore	1	1.64%

        TrommelRecipeBuilder.trommel(Blocks.CLAY,50,.5)
                .addEntry(new RangedEntry(Items.CLAY_BALL,4,8),30)
                .addEntry(new RangedEntry(ModItems.PEBBLE,1,3),20)
                .addEntry(new RangedEntry(Items.GUNPOWDER,1,1),10)
                .addEntry(new RangedEntry(Items.RAW_GOLD,1,1),1)
                .save(pWriter, Trommel.id("clay"));
    }
}
