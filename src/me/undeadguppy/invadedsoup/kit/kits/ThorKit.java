package me.undeadguppy.invadedsoup.kit.kits;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class ThorKit extends Kit {

	public ThorKit() {
		super(ChatColor.GREEN + "Thor", new ItemStack(Material.WOOD_AXE), KitType.THOR, "invadedsoup.thor");
	}

	@Override
	public void applyItems(Player p) {
		ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
		axe.addEnchantment(Enchantment.DAMAGE_ALL, 2);
		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());
		p.getInventory().clear();
		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		p.getInventory().addItem(axe);
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}

	}

	private HashSet<UUID> cooldown = new HashSet<UUID>();

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getMaterial().equals(Material.DIAMOND_AXE)) {

				if (cooldown.contains(player.getUniqueId())) {
					player.sendMessage(ChatColor.RED + "Your thor ability is still on cooldown!");
					return;
				}

				Location strike = new Location(block.getWorld(), block.getLocation().getX(),
						block.getWorld().getHighestBlockYAt(block.getLocation()), block.getLocation().getZ());
				block.getWorld().strikeLightning(strike);

				cooldown.add(player.getUniqueId());

				new BukkitRunnable() {
					@Override
					public void run() {
						cooldown.remove(player.getUniqueId());
					}
				}.runTaskLater(Main.getInstance(), 20L * 10);
			}
		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (cooldown.contains(e.getPlayer().getUniqueId())) {
			cooldown.remove(e.getPlayer().getUniqueId());
		}
	}
}
