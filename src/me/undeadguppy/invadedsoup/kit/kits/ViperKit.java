package me.undeadguppy.invadedsoup.kit.kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class ViperKit extends Kit {

	public ViperKit() {
		super(ChatColor.GREEN + "Viper", new ItemStack(Material.SPIDER_EYE), KitType.VIPER, "invadedsoup.viper");
	}

	@Override
	public void applyItems(Player p) {
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().addItem(sword);
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (e.getEntity() instanceof Player) {
				if (KitManager.getInstance().getKit(p) != null
						&& KitManager.getInstance().getKit(p).getType() == KitType.VIPER) {
					Random r = new Random();
					int i = r.nextInt(30);
					if (i <= 10) {
						Player v = (Player) e.getEntity();
						v.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 75, 0));
					}
				}
			}
		}
	}
}
