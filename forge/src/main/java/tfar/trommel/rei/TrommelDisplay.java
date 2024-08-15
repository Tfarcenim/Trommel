package tfar.trommel.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import tfar.trommel.recipe.RangedEntry;
import tfar.trommel.recipe.TrommelRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrommelDisplay extends BasicDisplay {


    SimpleWeightedRandomList<RangedEntry> outputs;

    public TrommelDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<ResourceLocation> location) {
        super(inputs, outputs, location);
    }

    public TrommelDisplay(TrommelRecipe trommelRecipe) {
        this(List.of(EntryIngredients.ofIngredient(trommelRecipe.getInput())),createOutputs(trommelRecipe),Optional.of(trommelRecipe.getId()));
        this.outputs = trommelRecipe.getOutputs();
    }

    static List<EntryIngredient> createOutputs(TrommelRecipe recipe) {
        List<ItemLike> items = new ArrayList<>();
        for (WeightedEntry.Wrapper<RangedEntry> rangedEntry : recipe.getOutputs().unwrap()) {
            items.add(rangedEntry.getData().item());
        }
        return items.stream().map(EntryIngredients::of).toList();
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ReiPlugin.TROMMEL;
    }

}
