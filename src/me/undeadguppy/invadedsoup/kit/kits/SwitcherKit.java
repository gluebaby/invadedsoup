package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class SwitcherKit extends Kit {

	public SwitcherKit() {
		super(ChatColor.GREEN + "Switcher", new ItemStack(Material.SNOW_BALL), KitType.SWITCHER,
				"invadedsoup.switcher");
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
		p.getInventory().setItem(8, new ItemStack(Material.SNOW_BALL, 16));

	}

	@EventHandler
	public void onSwitch(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball) {
			Snowball ball = (Snowball) e.getDamager();
			if (ball.getShooter() instanceof Player) {
				Player shooter = (Player) ball.getShooter();
				if (KitManager.getInstance().getKit(shooter) != null
						&& KitManager.getInstance().getKit(shooter).getType() == KitType.SWITCHER) {
					if (e.getEntity() instanceof Player) {
						Player hit = (Player) e.getEntity();
						Location hitloc = hit.getLocation();
						Location shooterloc = shooter.getLocation();
						shooter.teleport(hitloc);
						hit.teleport(shooterloc);
						hit.sendMessage(ChatColor.GREEN + "You have been switched!");
						shooter.sendMessage(ChatColor.GREEN + "You have switched " + hit.getName() + "!");
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getEntity().getKiller() instanceof Player) {
				if (KitManager.getInstance().getKit(e.getEntity().getKiller()) != null
						&& KitManager.getInstance().getKit(e.getEntity().getKiller()).getType() == KitType.SWITCHER) {
					e.getEntity().getKiller().getInventory().setItem(8, new ItemStack(Material.SNOW_BALL, 8));

				}
			}
		}
	}

}
