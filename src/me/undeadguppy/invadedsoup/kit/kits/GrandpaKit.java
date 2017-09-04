package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class GrandpaKit extends Kit {

	public GrandpaKit() {
		super(ChatColor.GREEN + "Grandpa", new ItemStack(Material.STICK), KitType.GRANDPA, "invadedsoup.grandpa");

	}

	@Override
	public void applyItems(Player p) {
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.KNOCKBACK, 1);
		ItemMeta swordmeta = sword.getItemMeta();
		swordmeta.setDisplayName(ChatColor.GREEN + "Walking Stick");
		swordmeta.spigot().setUnbreakable(true);
		sword.setItemMeta(swordmeta);
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		p.getInventory().addItem(sword);
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}

	}

}
