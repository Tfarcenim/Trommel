package tfar.trommel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import tfar.trommel.init.ModItems;

public class TrommelFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK,Trommel.id(Trommel.MOD_ID),Init.BLOCK);
        Registry.register(BuiltInRegistries.BLOCK,Trommel.id("mesh"),Init.MESH);
        Registry.register(BuiltInRegistries.ITEM,Trommel.id(Trommel.MOD_ID), ModItems.TROMMEL);
        Registry.register(BuiltInRegistries.ITEM,Trommel.id("mesh"), ModItems.MESH);
        Registry.register(BuiltInRegistries.ITEM,Trommel.id("pebble"), ModItems.PEBBLE);
        Registry.register(BuiltInRegistries.MENU,Trommel.id(Trommel.MOD_ID),Init.MENU_TYPE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE,Trommel.id(Trommel.MOD_ID),ModRecipeTypes.TROMMEL);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,Trommel.id(Trommel.MOD_ID),ModRecipeSerializers.TROMMEL);

        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,Trommel.id(Trommel.MOD_ID),Init.BLOCK_ENTITY_TYPE);

        Trommel.init();
    }
}
