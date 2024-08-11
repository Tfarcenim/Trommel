package tfar.trommel.client;

import net.minecraft.client.gui.screens.MenuScreens;
import tfar.trommel.Init;
import tfar.trommel.TrommelScreen;

public class ModClient {

    public static void setup() {
        MenuScreens.register(Init.MENU_TYPE, TrommelScreen::new);
    }

}
