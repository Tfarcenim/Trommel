package tfar.trommel.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import tfar.trommel.ModRecipeSerializers;
import tfar.trommel.recipe.RangedEntry;
import tfar.trommel.recipe.TrommelRecipe;

import java.util.List;
import java.util.function.Consumer;

public class TrommelRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final int processingTime;
    private final double outputChance;
    private final SimpleWeightedRandomList.Builder<RangedEntry> builder;

    @Nullable
    private String group;


    public TrommelRecipeBuilder(Ingredient input, int processing_time, double outputChance) {
        this.input = input;
        processingTime = processing_time;
        this.outputChance = outputChance;
        this.builder = SimpleWeightedRandomList.builder();
    }

    public static TrommelRecipeBuilder trommel(ItemLike input, int processingTime, double outputChance) {
        return trommel(Ingredient.of(input),processingTime,outputChance);
    }

    public static TrommelRecipeBuilder trommel(TagKey<Item> input, int processingTime, double outputChance) {
        return trommel(Ingredient.of(input),processingTime,outputChance);
    }

    public static TrommelRecipeBuilder trommel(Ingredient input, int processingTime, double outputChance) {
        return new TrommelRecipeBuilder(input, processingTime, outputChance);
    }

    @Override
    public TrommelRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return this;
    }

    public TrommelRecipeBuilder addEntry(RangedEntry rangedEntry,int weight) {
        builder.add(rangedEntry,weight);
        return this;
    }

    @Override
    public TrommelRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, group == null ? "" : group, input,processingTime,outputChance,builder.build()));

    }
    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient input;
        private final int processingTime;
        private final double outputChance;
        private final SimpleWeightedRandomList<RangedEntry> outputs;

        public Result(ResourceLocation id, String group, Ingredient input, int processingTime, double outputChance, SimpleWeightedRandomList<RangedEntry> outputs) {
            this.id = id;
            this.group = group;
            this.input = input;
            this.processingTime = processingTime;
            this.outputChance = outputChance;
            this.outputs = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            if (!group.isEmpty()) {
                pJson.addProperty("group", group);
            }

            pJson.add("input", this.input.toJson());
            pJson.addProperty("time", processingTime);
            pJson.addProperty("chance", outputChance);

            List<WeightedEntry.Wrapper<RangedEntry>> unwrap = outputs.unwrap();
            JsonArray array = new JsonArray();
            for (WeightedEntry.Wrapper<RangedEntry> rangedEntryWrapper : unwrap) {
                JsonObject object2 = new JsonObject();
                JsonObject object = rangedEntryWrapper.getData().toJson();

                object2.add("entry",object);
                object2.addProperty("weight", rangedEntryWrapper.getWeight().asInt());

                array.add(object2);
            }
            pJson.add("outputs",array);

        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipeSerializers.TROMMEL;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
