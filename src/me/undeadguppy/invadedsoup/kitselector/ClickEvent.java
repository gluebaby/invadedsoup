package me.undeadguppy.invadedsoup.kitselector;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.EconomyResponse;

public class ClickEvent implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (KitManager.getInstance().getKit(e.getPlayer()) == null) {
			if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				return;
			}
			if (e.getPlayer().getItemInHand() != null) {
				if (e.getPlayer().getItemInHand().getItemMeta() != null
						&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null) {
					if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
							.equals(ChatColor.GREEN + "Kit Selector")) {
						Main.getInstance().getKitGUI().setupIcons(e.getPlayer());
					}
				}
			}
		}

	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getClickedInventory() != null) {
				if (e.getClickedInventory().getTitle().equals(Main.getInstance().getKitGUI().getTitle())) {
					for (Kit kits : KitManager.getInstance().getRegisteredKits()) {
						if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
								&& e.getCurrentItem().getItemMeta().getDisplayName().equals(kits.getName())) {
							if (p.hasPermission(kits.getPermission())) {
								KitManager.getInstance().setKit(p, kits);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
								e.setCancelled(true);
								p.closeInventory();
								return;
							} else {
								p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1F, 1F);
								p.sendMessage(ChatColor.RED
										+ "You can't use that kit! Buy it with coins /kitshop or at www.invadedlands.net/donate!");
								e.setCancelled(true);
								p.closeInventory();
							}
						} else {
							e.setCancelled(true);
						}
					}
					e.setCancelled(true);
				} else if (e.getClickedInventory().getTitle().equals(Main.getInstance().getKitShop().getTitle())) {
					for (Kit kits : KitManager.getInstance().getRegisteredKits()) {
						if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null
								&& e.getCurrentItem().getItemMeta().getDisplayName().equals(kits.getName())) {
							if (p.hasPermission(kits.getPermission())) {
								p.sendMessage(ChatColor.RED + "You already own this kit!");
								p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1F, 1F);
								e.setCancelled(true);
								p.closeInventory();
								return;
							} else {
								EconomyResponse r = Main.econ.withdrawPlayer(p, 7500);
								if (r.transactionSuccess()) {
									p.sendMessage(ChatColor.GREEN + "You have successfuly purchased the "
											+ kits.getName() + " kit for $7500!");
									e.setCancelled(true);
									p.closeInventory();
									Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(),
											"manuaddp " + p.getName() + " " + kits.getPermission());
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
									return;
								} else {
									p.playSound(p.getLocation(), Sound.ANVIL_BREAK, 1F, 1F);
									p.sendMessage(ChatColor.RED
											+ "You don't have enough money for that kit! You need $7500!");
									e.setCancelled(true);
									p.closeInventory();
									return;
								}
							}
						} else {
							e.setCancelled(true);
						}

						e.setCancelled(true);
					}
				}
			}
		}
	}
}