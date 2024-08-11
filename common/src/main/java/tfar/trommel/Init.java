package tfar.trommel;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Init {

    public static final Block MESH = new MeshBlock(BlockBehaviour.Properties.of().strength(1.5f).requiresCorrectToolForDrops().noOcclusion());
    public static final Item MESH_ITEM = new BlockItem(MESH,new Item.Properties());

    public static final Block BLOCK = new TrommelBlock(BlockBehaviour.Properties.of().strength(2.5f,5).requiresCorrectToolForDrops());
    public static final Item ITEM = new BlockItem(BLOCK,new Item.Properties());
    public static final MenuType<TrommelMenu> MENU_TYPE = new MenuType<>(TrommelMenu::new, FeatureFlags.VANILLA_SET);
    public static final BlockEntityType<TrommelBlockEntity> BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(TrommelBlockEntity::new,BLOCK).build(null);


}
