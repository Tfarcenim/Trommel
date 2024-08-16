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
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import tfar.trommel.Trommel;
import tfar.trommel.init.ModItems;
import tfar.trommel.recipe.RangedEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrommelCategory implements DisplayCategory<TrommelDisplay> {


    private static Slot apply(WeightedEntry.Wrapper<RangedEntry> wrapper) {
        RangedEntry rangedEntry = wrapper.getData();
        return Widgets.createSlot(new Point(0, 0)).entry(EntryStacks.of(rangedEntry.item()));
    }


    private static Label apply2(SimpleWeightedRandomList<RangedEntry> list,WeightedEntry.Wrapper<RangedEntry> wrapper) {
        RangedEntry rangedEntry = wrapper.getData();

        double currentWeight = wrapper.getWeight().asInt();
        double totalWeight = WeightedRandom.getTotalWeight(list.unwrap());

        double percentage = 100 * currentWeight / totalWeight;

        Component component = Component.literal(rangedEntry.min()+"-"+rangedEntry.max()).append("   "+String.format("%.3f", percentage)+"%");
        return Widgets.createLabel(new Point(0, 5),component).color(0xFF404040, 0xFFBBBBBB).noShadow().leftAligned();
    }

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
        Point startPoint = new Point(bounds.getCenterX() , bounds.getCenterY() - 14);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(startPoint.x - 6, startPoint.y - 48)).entries(display.getInputEntries().get(0)).markInput());

        List<WeightedEntry.Wrapper<RangedEntry>> unwrap = display.outputs.unwrap();

        widgets.add(new ScrollableSlotsWidget(new Rectangle(bounds.x,bounds.y+28,bounds.width, bounds.height - 28), CollectionUtils.map(unwrap,
                TrommelCategory::apply),CollectionUtils.map(unwrap, wrapper -> apply2(display.outputs, wrapper))));

              widgets.add(Widgets.createLabel(new Point(bounds.x +90, bounds.getMinY() + 11),Component.literal(100 * display.outputChance+"%"))
                      .color(0xFF007f00, 0xFF447f44).noShadow().leftAligned());

        widgets.add(Widgets.createLabel(new Point(bounds.x +10, bounds.getMinY() + 11),Component.translatable("category.rei.trommel.processing_time",display.processingTime))
                .color(0xFF007f00, 0xFF447f44).noShadow().leftAligned());

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 140;
    }

    private static class ScrollableSlotsWidget extends WidgetWithBounds {
        private Rectangle bounds;

        private List<Slot> icon_widgets;
        private List<Label> text_widgets;

        private List<Widget> all_widgets = new ArrayList<>();

        private final ScrollingContainer scrolling = new ScrollingContainer() {
            @Override
            public Rectangle getBounds() {
                Rectangle bounds = ScrollableSlotsWidget.this.getBounds();
                return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 4);
            }

            @Override
            public int getMaxScrollHeight() {
                return icon_widgets.size() * 18+4;
            }
        };

        public ScrollableSlotsWidget(Rectangle bounds, List<Slot> icon_widgets,List<Label> text_widgets) {
            this.bounds = Objects.requireNonNull(bounds);
            this.icon_widgets = Lists.newArrayList(icon_widgets);
            this.text_widgets = text_widgets;
            all_widgets.addAll(icon_widgets);
            all_widgets.addAll(text_widgets);
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
                for (int y = 0; y < icon_widgets.size(); y++) {
                    Slot widget = icon_widgets.get(y);
                    widget.getBounds().setLocation(bounds.x + 4, bounds.y + 4 + y * 18 - scrolling.scrollAmountInt());
                    widget.render(graphics, mouseX, mouseY, delta);
                }
                for (int y = 0; y < text_widgets.size(); y++) {

                    Label label = text_widgets.get(y);
                    label.setPoint(new Point(bounds.x + 4 + 24, bounds.y + 10 + y * 18 - scrolling.scrollAmountInt()));
                    label.render(graphics, mouseX, mouseY, delta);
                }
            }


            try (CloseableScissors scissors = scissor(graphics, scrolling.getBounds())) {
                scrolling.renderScrollBar(graphics, 0xff000000, 1, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8f : 1f);
            }
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return all_widgets;
        }
    }

}
