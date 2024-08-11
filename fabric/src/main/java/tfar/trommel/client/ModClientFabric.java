package tfar.trommel.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import tfar.trommel.Init;

public class ModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Init.MESH, RenderType.cutoutMipped());
        ModClient.setup();
    }
}
