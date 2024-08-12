package tfar.trommel.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tfar.trommel.ModRecipeSerializers;
import tfar.trommel.ModRecipeTypes;
import tfar.trommel.invetory.ContainerWrapper;

import java.util.List;
import java.util.stream.IntStream;

public class TrommelRecipe implements Recipe<ContainerWrapper> {

    private final ResourceLocation id;
    private final Ingredient input;
    private final double outputChance;
    private final SimpleWeightedRandomList<RangedEntry> outputs;

    public TrommelRecipe(ResourceLocation id, Ingredient input, double outputChance, SimpleWeightedRandomList<RangedEntry> outputs) {
        this.id = id;
        this.input = input;
        this.outputChance = outputChance;
        this.outputs = outputs;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.TROMMEL;
    }

    //slots 1 - 4 are inputs
    @Override
    public boolean matches(ContainerWrapper container, Level level) {
        return IntStream.of(1, 2, 3, 4).anyMatch(i -> input.test(container.getItem(i)));
    }

    @Override
    public ItemStack assemble(ContainerWrapper container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TROMMEL;
    }


    public static class Serializer implements RecipeSerializer<TrommelRecipe> {

        @Override
        public TrommelRecipe fromJson(ResourceLocation location, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json,"input"));
            double outputChance = GsonHelper.getAsDouble(json,"chance");

            SimpleWeightedRandomList<RangedEntry> outputs = get(GsonHelper.getAsJsonArray(json,"outputs"));

            return new TrommelRecipe(location,input,outputChance,outputs);
        }

        protected static SimpleWeightedRandomList<RangedEntry> get(JsonArray array) {
            SimpleWeightedRandomList.Builder<RangedEntry> builder = SimpleWeightedRandomList.builder();
            for (JsonElement jsonElement : array) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                builder.add(RangedEntry.fromJson(jsonObject),GsonHelper.getAsInt(jsonObject,"weight"));
            }

            return builder.build();
        }

        @Override
        public TrommelRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf friendlyByteBuf) {
            Ingredient input = Ingredient.fromNetwork(friendlyByteBuf);
            double outputChance = friendlyByteBuf.readDouble();
            int size = friendlyByteBuf.readInt();
            SimpleWeightedRandomList.Builder<RangedEntry> builder = SimpleWeightedRandomList.builder();
            for (int i = 0; i < size;i++) {
                builder.add(RangedEntry.fromNetwork(friendlyByteBuf), friendlyByteBuf.readInt());
            }

            return new TrommelRecipe(location,input,outputChance,builder.build());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, TrommelRecipe trommelRecipe) {
            trommelRecipe.input.toNetwork(friendlyByteBuf);
            friendlyByteBuf.writeDouble(trommelRecipe.outputChance);
            List<WeightedEntry.Wrapper<RangedEntry>> unwrap = trommelRecipe.outputs.unwrap();
            friendlyByteBuf.writeInt(unwrap.size());
            for (int i = 0; i < unwrap.size(); i++) {
                WeightedEntry.Wrapper<RangedEntry> rangedEntryWrapper = unwrap.get(i);
                rangedEntryWrapper.getData().writeBuf(friendlyByteBuf);
                friendlyByteBuf.writeInt(rangedEntryWrapper.getWeight().asInt());
            }
        }
    }
}
