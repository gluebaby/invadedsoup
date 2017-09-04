package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class SonicKit extends Kit {

	public SonicKit() {
		super(ChatColor.GREEN + "Sonic", new ItemStack(new Potion(PotionType.SPEED).toItemStack(1)), KitType.SONIC, "invadedsoup.sonic");
	}

	@Override
	public void applyItems(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 1));
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().addItem(sword);
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}

	}

}
