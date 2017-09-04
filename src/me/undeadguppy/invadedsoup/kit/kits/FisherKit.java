package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class FisherKit extends Kit {

	public FisherKit() {
		super(ChatColor.GREEN + "Fisherman", new ItemStack(Material.FISHING_ROD), KitType.FISHERMAN,
				"invadedsoup.fisherman");
	}

	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player p = e.getPlayer();
		Player caught = (Player) e.getCaught();
		if (caught != null) {
			if (caught instanceof Player) {
				caught.teleport(p);
				caught.sendMessage(ChatColor.GREEN + "You have been fished by " + p.getName() + "!");
				p.sendMessage(ChatColor.GREEN + "You have fished " + caught.getName() + "!");
			}
		}
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
		p.getInventory().addItem(new ItemStack(Material.FISHING_ROD));
		for (int i = 0; i < 35; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
		p.getInventory().setItem(9, new ItemStack(Material.ARROW));

	}

}
