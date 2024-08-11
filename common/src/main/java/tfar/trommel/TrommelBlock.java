package tfar.trommel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrommelBlock extends Block {
    public TrommelBlock(Properties $$0) {
        super($$0);
        this.registerDefaultState(this.stateDefinition.any().setValue(LecternBlock.FACING, Direction.NORTH));
    }

    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    @Override
    public RenderShape getRenderShape(BlockState $$0) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
        return LecternBlock.SHAPE_COMMON;
    }

    @Override
    public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
        if ($$1.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            $$3.openMenu($$0.getMenuProvider($$1, $$2));
            $$3.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext $$0) {
        return this.defaultBlockState().setValue(LecternBlock.FACING, $$0.getHorizontalDirection().getOpposite());
    }


    @Override
    public BlockState rotate(BlockState $$0, Rotation $$1) {
        return $$0.setValue(LecternBlock.FACING, $$1.rotate($$0.getValue(LecternBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState $$0, Mirror $$1) {
        return $$0.rotate($$1.getRotation($$0.getValue(LecternBlock.FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        switch ($$0.getValue(LecternBlock.FACING)) {
            case NORTH -> {
                return LecternBlock.SHAPE_NORTH;
            }
            case SOUTH -> {
                return LecternBlock.SHAPE_SOUTH;
            }
            case EAST -> {
                return LecternBlock.SHAPE_EAST;
            }
            case WEST -> {
                return LecternBlock.SHAPE_WEST;
            }
            default -> {
                return LecternBlock.SHAPE_COMMON;
            }
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
        return new SimpleMenuProvider(($$2x, $$3, $$4) -> {
            return new TrommelMenu($$2x, $$3, ContainerLevelAccess.create($$1, $$2));
        }, CONTAINER_TITLE);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
        $$0.add(LecternBlock.FACING);
    }

}
