package tfar.trommel;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import tfar.trommel.client.ModClientForge;
import tfar.trommel.datagen.ModDatagen;

@Mod(Trommel.MOD_ID)
public class TrommelForge {
    
    public TrommelForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::register);
        if (FMLEnvironment.dist.isClient()) {
            ModClientForge.init(bus);
        }
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Trommel.init();
    }

    private void register(RegisterEvent event) {
        event.register(Registries.BLOCK,Trommel.id(Trommel.MOD_ID),() -> Init.BLOCK);
        event.register(Registries.BLOCK,Trommel.id("mesh"),() -> Init.MESH);

        event.register(Registries.ITEM,Trommel.id(Trommel.MOD_ID),() -> Init.ITEM);
        event.register(Registries.ITEM,Trommel.id("mesh"),() -> Init.MESH_ITEM);

        event.register(Registries.MENU,Trommel.id(Trommel.MOD_ID),() -> Init.MENU_TYPE);

        event.register(Registries.RECIPE_TYPE,Trommel.id(Trommel.MOD_ID),() -> ModRecipeTypes.TROMMEL);

        event.register(Registries.RECIPE_SERIALIZER,Trommel.id(Trommel.MOD_ID),() -> ModRecipeSerializers.TROMMEL);

        event.register(Registries.BLOCK_ENTITY_TYPE,Trommel.id(Trommel.MOD_ID),() -> Init.BLOCK_ENTITY_TYPE);

    }
}