package tfar.trommel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MeshBlock extends Block {
    public MeshBlock(Properties $$0) {
        super($$0);
    }


    @Override
    public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext context) {
        return super.getCollisionShape($$0, $$1, $$2, context);
    }
}
