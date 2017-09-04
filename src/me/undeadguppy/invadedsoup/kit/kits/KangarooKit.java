package me.undeadguppy.invadedsoup.kit.kits;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class KangarooKit extends Kit {

	public KangarooKit() {
		super(ChatColor.GREEN + "Kangaroo", new ItemStack(Material.FIREWORK), KitType.KANGAROO, "invadedsoup.kangaroo");
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
		p.getInventory().setItem(8, new ItemStack(Material.FIREWORK));

	}

	private HashSet<UUID> kanga = new HashSet<UUID>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if (p.getItemInHand().getType() == Material.FIREWORK) {
			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
					|| event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
				event.setCancelled(true);
			if (!kanga.contains(p.getUniqueId())) {
				if (!(p.isSneaking())) {
					p.setFallDistance(-(4F + 1));
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(0.4F);
					vector.setY(0.8F);
					p.setVelocity(vector);
				} else {
					p.setFallDistance(-(4F + 1));
					Vector vector = p.getEyeLocation().getDirection();
					vector.multiply(0.8F);
					vector.setY(0.6);
					p.setVelocity(vector);
				}
				kanga.add(p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (kanga.contains(p.getUniqueId())) {
			Block b = p.getLocation().getBlock();
			if (b.getType() != Material.AIR || b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				kanga.remove(p.getUniqueId());
			}
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (e instanceof Player) {
			Player player = (Player) e;
			if (event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL
					&& KitManager.getInstance().getKit(player) != null
					&& KitManager.getInstance().getKit(player).getType() == KitType.KANGAROO) {
				if (event.getDamage() >= 7) {
					event.setDamage(7);
				}
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		if (kanga.contains(e.getPlayer().getUniqueId())) {
			kanga.remove(e.getPlayer().getUniqueId());
		}
	}

}
