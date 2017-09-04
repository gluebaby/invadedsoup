package me.undeadguppy.invadedsoup.listeners;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerListener implements Listener {

	private Main m;

	public PlayerListener(Main m) {
		this.m = m;
		Bukkit.getServer().getPluginManager().registerEvents(this, this.m);
	}

	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if (e.getEntity() instanceof Player) {
			Player killed = (Player) e.getEntity();
			if (killed.getKiller() instanceof Player) {
				Player killer = (Player) killed.getKiller();
				if (killer == killed) {
					return;
				}
				int amount = ThreadLocalRandom.current().nextInt(10, 25 + 1);

				EconomyResponse response = Main.econ.depositPlayer(killer, amount);
				if (response.transactionSuccess()) {
					killer.sendMessage(
							ChatColor.GREEN + "You have killed " + killed.getName() + " for $" + amount + "!");
				}
			}
		}
	}

	@EventHandler
	public void soup(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
				event.setCancelled(true);
				Damageable damageable = (Damageable) player;
				if (damageable.getHealth() != 20) {
					double newHealth = damageable.getHealth() + 7;
					if (newHealth > 20D) {
						player.setHealth(20D);
					} else {
						player.setHealth(newHealth);
					}
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(),
							new ItemStack(Material.BOWL));
				} else if (player.getFoodLevel() != 20) {
					int newHunger = player.getFoodLevel() + 7;
					if (newHunger > 20) {
						player.setFoodLevel(20);
					} else {
						player.setFoodLevel(newHunger);
					}
					player.getInventory().setItem(player.getInventory().getHeldItemSlot(),
							new ItemStack(Material.BOWL));
				}

			}
		}
	}

	@EventHandler
	public void pickup(PlayerPickupItemEvent e) {
		ItemStack item = e.getItem().getItemStack();
		if (!(item.getType() == Material.MUSHROOM_SOUP)) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onItem(PlayerItemDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.ARMOR) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!(e.getItemDrop().getItemStack().getType() == Material.BOWL)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.GREEN + "Hey, you might want to hold onto that!");
		}
	}

	@EventHandler
	public void respawn(PlayerRespawnEvent e) {
		if (KitManager.getInstance().getKit(e.getPlayer()) != null) {
			KitManager.getInstance().removeKit(e.getPlayer());
		}
		KitManager.getInstance().applyKitSelector(e.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		if (KitManager.getInstance().getKit(e.getPlayer()) != null) {
			KitManager.getInstance().removeKit(e.getPlayer());
		}
		KitManager.getInstance().applyKitSelector(e.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		KitManager.getInstance().removeKit(e.getPlayer());
		KitManager.getInstance().applyKitSelector(e.getPlayer());
	}

}
