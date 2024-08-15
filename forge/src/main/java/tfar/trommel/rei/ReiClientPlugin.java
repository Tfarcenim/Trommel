package tfar.trommel.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginCommon;
import me.shedaniel.rei.plugin.common.displays.DefaultSmithingDisplay;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import tfar.trommel.ModRecipeTypes;
import tfar.trommel.TrommelMenu;
import tfar.trommel.TrommelScreen;
import tfar.trommel.init.ModItems;
import tfar.trommel.recipe.TrommelRecipe;


@REIPluginCommon
public class ReiClientPlugin implements REIClientPlugin {

  @Override
  public void registerCategories(CategoryRegistry registry) {

    registry.add(new TrommelCategory());
    registry.addWorkstations(ReiPlugin.TROMMEL, EntryStacks.of(ModItems.TROMMEL));
  }

  @Override
  public void registerDisplays(DisplayRegistry registry) {
    registry.registerRecipeFiller(TrommelRecipe.class, ModRecipeTypes.TROMMEL,trommelRecipe -> new TrommelDisplay(trommelRecipe));
  }

  @Override
  public void registerTransferHandlers(TransferHandlerRegistry registry) {
    registry.register(SimpleTransferHandler.create(TrommelMenu.class, ReiPlugin.TROMMEL,
            new SimpleTransferHandler.IntRange(1, 4)));
  }

  @Override
  public void registerScreens(ScreenRegistry registry) {
    registry.registerContainerClickArea(new Rectangle(88, 32, 20, 20), TrommelScreen.class, ReiPlugin.TROMMEL);
  }
}
