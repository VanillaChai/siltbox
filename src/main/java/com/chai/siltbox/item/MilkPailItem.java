package com.chai.siltbox.item;

import com.chai.siltbox.Main;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkPailItem extends Item
{
	public MilkPailItem(Item.Settings settings)
	{
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if (user instanceof ServerPlayerEntity)
		{
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		PlayerEntity player = user instanceof PlayerEntity ? (PlayerEntity)user : null;

		if (player != null && !player.abilities.creativeMode)
		{
			player.inventory.insertStack(new ItemStack(SiltItems.PAIL));
			stack.decrement(1);
		}

		if (!world.isClient)
		{
			user.clearStatusEffects();
		}

		return stack.isEmpty() ? new ItemStack(SiltItems.PAIL) : stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack)
	{
		return 32;
	}

	@Override
	public UseAction getUseAction(ItemStack stack)
	{
		return UseAction.DRINK;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
	{
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}