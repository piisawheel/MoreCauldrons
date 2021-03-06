package com.mrbysco.morecauldrons.blocks.inspirations;

import com.mrbysco.morecauldrons.ModReference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockEnhancedGoldCauldron extends BlockEnhancedCauldronBase implements ICauldron{
	
	public BlockEnhancedGoldCauldron(String registryName) {
		super();
		this.setSoundType(SoundType.METAL);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
        
		this.setUnlocalizedName(ModReference.MOD_PREFIX + registryName.replaceAll("_", ""));
		this.setRegistryName(registryName);
	}
	
	@Override
	public Material getMaterial(IBlockState state) {
		return Material.IRON;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.GOLD;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}
}