package me.undeadguppy.invadedsoup.kit.kits;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class NinjaKit extends Kit {

	public NinjaKit() {
		super(ChatColor.GREEN + "Ninja", new ItemStack(Material.GOLDEN_CARROT), KitType.NINJA, "invadedsoup.ninja");

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
		for (int i = 0; i < 36; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));

		}
	}

	private HashMap<UUID, UUID> ninja = new HashMap<UUID, UUID>();
	private HashSet<UUID> cooldown = new HashSet<UUID>();

	@EventHandler
	public void onAttack(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player attacked = (Player) e.getEntity();
			if (e.getDamager() instanceof Player) {
				Player attacker = (Player) e.getDamager();
				if (KitManager.getInstance().getKit(attacker) != null
						&& KitManager.getInstance().getKit(attacker).getType() == KitType.NINJA) {
					ninja.put(attacker.getUniqueId(), attacked.getUniqueId());
				}
			}
		}
	}

	@EventHandler
	public void onShift(PlayerToggleSneakEvent e) {
		if (KitManager.getInstance().getKit(e.getPlayer()) != null
				&& KitManager.getInstance().getKit(e.getPlayer()).getType() == KitType.NINJA) {
			if (ninja.containsKey(e.getPlayer().getUniqueId())) {
				if (cooldown.contains(e.getPlayer().getUniqueId())) {
					e.getPlayer().sendMessage(ChatColor.RED + "Your ninja ability is still on cooldown!");
					ninja.remove(e.getPlayer().getUniqueId());
					return;
				}
				Player target = Bukkit.getPlayer(ninja.get(e.getPlayer().getUniqueId()));
				if (target != null) {
					e.getPlayer().teleport(target.getLocation());
					e.getPlayer().sendMessage(ChatColor.GREEN + "You have ninjaed behind " + target.getName() + "!");
					target.sendMessage(ChatColor.GREEN + e.getPlayer().getName()
							+ " has used their ninja ability to teleport behind you!");
					cooldown.add(e.getPlayer().getUniqueId());
					ninja.remove(e.getPlayer().getUniqueId());
					new BukkitRunnable() {
						int time = 30;

						@Override
						public void run() {
							if (time == 0) {
								cooldown.remove(e.getPlayer().getUniqueId());
								cancel();
								return;
							}
							time--;

						}

					}.runTaskTimer(Main.getInstance(), 0L, 20L);
				} else {
					e.getPlayer().sendMessage(ChatColor.RED + "The person you attempted to ninja to has logged out!");
					ninja.remove(e.getPlayer().getUniqueId());
				}
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (KitManager.getInstance().getKit(e.getPlayer()) != null
				&& KitManager.getInstance().getKit(e.getPlayer()).getType() == KitType.NINJA) {
			if (ninja.containsKey(e.getPlayer().getUniqueId())) {
				ninja.remove(e.getPlayer().getUniqueId());
				if (cooldown.contains(e.getPlayer().getUniqueId())) {
					cooldown.remove(e.getPlayer().getUniqueId());
				}
			}
		}
	}
}
