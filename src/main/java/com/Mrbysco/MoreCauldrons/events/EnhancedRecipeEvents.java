package com.Mrbysco.MoreCauldrons.events;

import java.util.ArrayList;
import java.util.Random;

import com.Mrbysco.MoreCauldrons.blocks.inspirations.BlockEnhancedWoodenCauldron;
import com.Mrbysco.MoreCauldrons.blocks.inspirations.ICauldron;

import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.recipe.cauldron.ICauldronRecipe.CauldronState;
import knightminer.inspirations.recipes.block.BlockEnhancedCauldron.CauldronContents;
import knightminer.inspirations.recipes.tileentity.TileCauldron;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EnhancedRecipeEvents {
	
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void clickCauldron(RightClickBlock event) {
		if(!Config.enableCauldronRecipes) {
			return;
		}
				
		EntityPlayer player = event.getEntityPlayer();
		if(player.isSneaking()) {
			return;
		}

		// basic properties
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		IBlockState state = world.getBlockState(pos);
		if(!(state.getBlock() instanceof ICauldron)) {
			return;
		}
		ItemStack stack = event.getItemStack();
		if(stack.isEmpty()) {
			return;
		}

		boolean result = TileCauldron.interact(world, pos, state, player, event.getHand());
		if(result) {
			event.setCanceled(true);
			event.setCancellationResult(EnumActionResult.SUCCESS);
		}
	}
		
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void clickCauldron(TickEvent.WorldTickEvent event) {
		World world = event.world;
		
		ArrayList<TileEntity> TileList = new ArrayList<>(world.loadedTileEntityList);
		
		for (TileEntity tile : TileList)
		{
			if(tile instanceof TileCauldron)
			{
				TileCauldron cauldron = (TileCauldron)tile;
				BlockPos pos = cauldron.getPos();
				Block block = world.getBlockState(pos).getBlock();
				
				if(cauldron.getContentType() == CauldronContents.FLUID)
				{
					CauldronState state = CauldronState.fromNBT(cauldron.serializeNBT().getCompoundTag(cauldron.TAG_STATE));
					if(block instanceof BlockEnhancedWoodenCauldron)
					{
						if(state.getFluid().getTemperature() >= 350)
						{		
							Random random = world.rand;
							int randInt = random.nextInt(10);

							if(randInt < 2)
							{
								if(world.getBlockState(pos.north()).getMaterial() == Material.AIR && random.nextInt(100) < 1)
									world.setBlockState(pos.north(), Blocks.FIRE.getDefaultState(), 3);
								if(world.getBlockState(pos.south()).getMaterial() == Material.AIR && random.nextInt(100) < 1)
									world.setBlockState(pos.south(), Blocks.FIRE.getDefaultState(), 3);
								if(world.getBlockState(pos.west()).getMaterial() == Material.AIR && random.nextInt(100) < 1)
									world.setBlockState(pos.west(), Blocks.FIRE.getDefaultState(), 3);
								if(world.getBlockState(pos.east()).getMaterial() == Material.AIR && random.nextInt(100) < 1)
									world.setBlockState(pos.east(), Blocks.FIRE.getDefaultState(), 3);
								
								int rand2 = random.nextInt(100);
								if(rand2 < 3 && state.getFluid().getBlock() != null)
									world.setBlockState(pos, state.getFluid().getBlock().getDefaultState(), 6);
							}
						}
					}
				}
			}
		}
	}
}
