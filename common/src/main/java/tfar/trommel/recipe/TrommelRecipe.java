package tfar.trommel.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tfar.trommel.ModRecipeSerializers;
import tfar.trommel.ModRecipeTypes;

public class TrommelRecipe implements Recipe<Container> {


    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.TROMMEL;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TROMMEL;
    }


    public static class Serializer implements RecipeSerializer<TrommelRecipe> {

        @Override
        public TrommelRecipe fromJson(ResourceLocation location, JsonObject json) {
            return null;
        }

        @Override
        public TrommelRecipe fromNetwork(ResourceLocation $$0, FriendlyByteBuf $$1) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, TrommelRecipe trommelRecipe) {

        }
    }
}
