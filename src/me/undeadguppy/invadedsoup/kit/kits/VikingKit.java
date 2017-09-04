package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class VikingKit extends Kit {

	public VikingKit() {
		super(ChatColor.GREEN + "Viking", new ItemStack(Material.IRON_AXE), KitType.VIKING, "invadedsoup.viking");
	}

	@Override
	public void applyItems(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.GOLD_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
		ItemStack sword = new ItemStack(Material.IRON_AXE);
		p.getInventory().addItem(sword);
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}

	}

}
