package me.undeadguppy.invadedsoup.kit.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.undeadguppy.invadedsoup.kit.Kit;
import net.md_5.bungee.api.ChatColor;

public class SnowmanKit extends Kit {

	public SnowmanKit() {
		super(ChatColor.GREEN + "Snowman", new ItemStack(Material.JACK_O_LANTERN), KitType.SNOWMAN,
				"invadedsoup.snowman");

	}

	@Override
	public void applyItems(Player p) {
		p.getActivePotionEffects().clear();
		PlayerInventory inv = p.getInventory();
		p.getInventory().clear();
		ItemStack sword = new ItemStack(Material.IRON_SWORD);
		sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		p.getInventory().addItem(sword);
		ItemStack snow = new ItemStack(Material.SUGAR);
		ItemMeta snowmeta = snow.getItemMeta();
		snowmeta.setDisplayName(ChatColor.GREEN + "Sacred Snow");
		snow.setItemMeta(snowmeta);
		inv.addItem(snow);
		p.getInventory().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		for (int i = 0; i <= inv.getSize(); i++) {
			inv.addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (p.getInventory().getItemInHand().getType() == Material.SUGAR) {
				p.launchProjectile(Snowball.class);
				p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 150, 0));
			}

		}
	}

}
