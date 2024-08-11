package tfar.trommel.platform;

import tfar.trommel.TrommelBlockEntity;
import tfar.trommel.TrommelInventory;
import tfar.trommel.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public TrommelInventory create(TrommelBlockEntity trommelBlockEntity) {
        return null;
    }
}