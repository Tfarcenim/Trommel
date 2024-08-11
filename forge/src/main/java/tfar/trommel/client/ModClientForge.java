package tfar.trommel.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tfar.trommel.Init;

public class ModClientForge {

    public static void init(IEventBus bus) {
        bus.addListener(ModClientForge::setup);
    }

    static void setup(FMLClientSetupEvent event) {
        ModClient.setup();
        ItemBlockRenderTypes.setRenderLayer(Init.MESH, RenderType.cutoutMipped());
    }

}
