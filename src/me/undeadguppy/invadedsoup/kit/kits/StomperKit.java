package me.undeadguppy.invadedsoup.kit.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class StomperKit extends Kit {

	public StomperKit() {
		super(ChatColor.GREEN + "Stomper", new ItemStack(Material.IRON_BOOTS), KitType.STOMPER, "invadedsoup.stomper");
		// TODO Auto-generated constructor stub
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

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			DamageCause cause = e.getCause();
			if (cause == DamageCause.FALL) {
				Player stomper = (Player) e.getEntity();
				if (KitManager.getInstance().getKit(stomper) != null
						&& KitManager.getInstance().getKit(stomper).getType() == KitType.STOMPER) {
					List<Entity> list = stomper.getNearbyEntities(4, 4, 4);
					for (Entity ent : list) {
						if (ent instanceof Player) {
							Player victims = (Player) ent;
							if (victims.isSneaking()) {
								return;
							} else {
								victims.damage(e.getDamage() / 3);
							}
						}
					}
					e.setDamage(e.getDamage() / 4);
				}
			}
		}

	}
}
