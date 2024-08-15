package tfar.trommel.rei;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.client.categories.beacon.DefaultBeaconBaseCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedEntry;
import tfar.trommel.Trommel;
import tfar.trommel.init.ModItems;
import tfar.trommel.recipe.RangedEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrommelCategory implements DisplayCategory<TrommelDisplay> {


    @Override
    public CategoryIdentifier<TrommelDisplay> getCategoryIdentifier() {
        return ReiPlugin.TROMMEL;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.TROMMEL);
    }

    @Override
    public Component getTitle() {
        return Trommel.TROMMEL;
    }

    @Override
    public List<Widget> setupDisplay(TrommelDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 31, bounds.getCenterY() - 13);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 6, startPoint.y + 18)).entries(display.getInputEntries().get(0)).markInput());

        List<EntryIngredient> outputIcons = display.getOutputEntries();
        List<WeightedEntry.Wrapper<RangedEntry>> unwrap = display.outputs.unwrap();

        widgets.add(new ScrollableSlotsWidget(bounds, CollectionUtils.map(unwrap,
                t -> Widgets.createSlot(new Point(0, 0)).disableBackground().entry(EntryStacks.of(t.getData().item())))));

  //      widgets.add(Widgets.createLabel(new Point(bounds.x + 90, bounds.getMaxY() - 15),Component.literal(""))
  //              .color(0xFF007f00, 0xFF447f44).noShadow().leftAligned());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 140;
    }

    private static class ScrollableSlotsWidget extends WidgetWithBounds {
        private Rectangle bounds;
        private List<Slot> widgets;
        private final ScrollingContainer scrolling = new ScrollingContainer() {
            @Override
            public Rectangle getBounds() {
                Rectangle bounds = ScrollableSlotsWidget.this.getBounds();
                return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
            }

            @Override
            public int getMaxScrollHeight() {
                return Mth.ceil(widgets.size() / 8f) * 18;
            }
        };

        public ScrollableSlotsWidget(Rectangle bounds, List<Slot> widgets) {
            this.bounds = Objects.requireNonNull(bounds);
            this.widgets = Lists.newArrayList(widgets);
        }

        @Override
        public boolean mouseScrolled(double double_1, double double_2, double double_3) {
            if (containsMouse(double_1, double_2)) {
                scrolling.offset(ClothConfigInitializer.getScrollStep() * -double_3, true);
                return true;
            }
            return false;
        }

        @Override
        public Rectangle getBounds() {
            return bounds;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (scrolling.updateDraggingState(mouseX, mouseY, button))
                return true;
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
                return true;
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
            scrolling.updatePosition(delta);
            Rectangle innerBounds = scrolling.getScissorBounds();
            try (CloseableScissors scissors = scissor(graphics, innerBounds)) {
                for (int y = 0; y < Mth.ceil(widgets.size() / 8f); y++) {
                    for (int x = 0; x < 8; x++) {
                        int index = y * 8 + x;
                        if (widgets.size() <= index)
                            break;
                        Slot widget = widgets.get(index);
                        widget.getBounds().setLocation(bounds.x + 1 + x * 18, bounds.y + 1 + y * 18 - scrolling.scrollAmountInt());
                        widget.render(graphics, mouseX, mouseY, delta);
                    }
                }
            }
            try (CloseableScissors scissors = scissor(graphics, scrolling.getBounds())) {
                scrolling.renderScrollBar(graphics, 0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
            }
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return widgets;
        }
    }

}
