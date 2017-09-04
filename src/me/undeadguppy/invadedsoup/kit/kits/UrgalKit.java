package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class UrgalKit extends Kit {

	public UrgalKit() {
		super(ChatColor.GREEN + "Urgal", new ItemStack(new Potion(PotionType.STRENGTH).toItemStack(1)), KitType.URGAL, "invadedsoup.urgal");

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
	public void onKill(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if (KitManager.getInstance().getKit(p) != null
					&& KitManager.getInstance().getKit(p).getType() == KitType.URGAL) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
			}
		}
	}
}
