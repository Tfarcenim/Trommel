package tfar.trommel.platform;

import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;
import tfar.trommel.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public TrommelInventory create(TrommelBlockEntity trommelBlockEntity) {
        return new TrommelInventoryFabric(trommelBlockEntity);
    }
}
