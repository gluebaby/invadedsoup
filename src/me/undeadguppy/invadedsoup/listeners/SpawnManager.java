package me.undeadguppy.invadedsoup.listeners;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class SpawnManager implements CommandExecutor, Listener {
	private Set<UUID> teleporting = new HashSet<>();

	public void sendToSpawn(final Player player) {
		int delay = 5;
		teleporting.add(player.getUniqueId());
		new BukkitRunnable() {
			Location spawn = Bukkit.getServer().getWorld("world").getSpawnLocation();

			@Override
			public void run() {
				if (teleporting.contains(player.getUniqueId())) {
					teleporting.remove(player.getUniqueId());
					player.teleport(spawn);
					player.sendMessage(ChatColor.GREEN + "Teleported!");
					if (KitManager.getInstance().getKit(player) != null) {
						KitManager.getInstance().removeKit(player);
					}
					KitManager.getInstance().applyKitSelector(player);
				} else {
					player.sendMessage(ChatColor.RED + "Teleport cancelled!");
				}
			}
		}.runTaskLater(Main.getInstance(), 20 * delay);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawn")) {
			Player p = (Player) sender;
			if (teleporting.contains(p.getUniqueId())) {
				p.sendMessage(ChatColor.GREEN + "You're already teleporting to spawn!");
				return true;
			}
			p.sendMessage(ChatColor.GREEN + "Teleporting to spawn in 5 seconds!");
			sendToSpawn(p);
			return true;
		}
		return true;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (teleporting.contains(p.getUniqueId())) {
				teleporting.remove(p.getUniqueId());
				p.sendMessage(ChatColor.RED + "Teleport cancelled!");
				return;
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();
		if ((from.getBlockX() != to.getBlockX()) || (from.getBlockY() != to.getBlockY())
				|| (from.getBlockZ() != to.getBlockZ()) || (from.getWorld() != to.getWorld())) {
			if (teleporting.contains(e.getPlayer().getUniqueId())) {
				teleporting.remove(e.getPlayer().getUniqueId());
				e.getPlayer().sendMessage(ChatColor.RED + "Teleport cancelled!");
				return;
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			teleporting.remove(p.getUniqueId());
		}
	}

}
