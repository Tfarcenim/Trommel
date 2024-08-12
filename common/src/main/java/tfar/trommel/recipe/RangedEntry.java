package tfar.trommel.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record RangedEntry(Item item,int min,int max) {

    public ItemStack getItem(RandomSource randomSource) {
        int count = min + randomSource.nextInt(max - min + 1);
        return new ItemStack(item,count);
    }

    public void writeBuf(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ITEM, item);
        buf.writeInt(min);
        buf.writeInt(max);
    }

    public static RangedEntry fromJson(JsonObject jsonObject) {
        Item item = GsonHelper.getAsItem(jsonObject,"item");
        int min = GsonHelper.getAsInt(jsonObject,"min");
        int max = GsonHelper.getAsInt(jsonObject,"max");
        return new RangedEntry(item,min,max);
    }

    public static RangedEntry fromNetwork(FriendlyByteBuf buf) {
        Item item = buf.readById(BuiltInRegistries.ITEM);
        int min = buf.readInt();
        int max = buf.readInt();
        return new RangedEntry(item,min,max);
    }

}
