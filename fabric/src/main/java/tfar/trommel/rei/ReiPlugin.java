package tfar.trommel.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import tfar.trommel.Trommel;

public class ReiPlugin implements REIServerPlugin {
    public static final CategoryIdentifier<TrommelDisplay> TROMMEL = CategoryIdentifier.of(Trommel.MOD_ID, "trommel");
}
