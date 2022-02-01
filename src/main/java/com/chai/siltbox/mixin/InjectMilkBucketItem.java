package com.chai.siltbox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.world.World;

@Mixin(MilkBucketItem.class)
public class InjectMilkBucketItem
{
	@Inject(
		method = "finishUsing",
		at = @At("TAIL"),
		cancellable = true)
	private void workWithStacks(ItemStack stack, World world,
		LivingEntity user, CallbackInfoReturnable<ItemStack> info)
	{
		PlayerEntity player = user instanceof PlayerEntity ? (PlayerEntity)user : null;

		if (player != null && !player.abilities.creativeMode)
		{
			player.inventory.insertStack(new ItemStack(Items.BUCKET));
		}
	}
}