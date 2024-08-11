package tfar.trommel;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import tfar.trommel.recipe.TrommelRecipe;

public class ModRecipeTypes {
    public static final RecipeType<TrommelRecipe> TROMMEL = new RecipeType<>() {
        @Override
        public String toString() {
            return Trommel.MOD_ID+":trommel";
        }
    };
}
