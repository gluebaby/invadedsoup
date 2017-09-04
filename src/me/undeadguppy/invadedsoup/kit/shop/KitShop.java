package me.undeadguppy.invadedsoup.kit.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.undeadguppy.invadedsoup.kit.Kit;
import me.undeadguppy.invadedsoup.kit.KitManager;
import me.undeadguppy.invadedsoup.kitselector.KitGUI;
import net.md_5.bungee.api.ChatColor;

public class KitShop {

	private KitGUI gui;
	private Inventory inv;
	private String title;
	private int size;
	private ArrayList<String> purchased;
	private ArrayList<String> selling;

	public KitShop(KitGUI gui, String title) {
		this.purchased = new ArrayList<String>();
		this.purchased.add(ChatColor.GREEN + "Owned");
		this.selling = new ArrayList<String>();
		this.selling.add(ChatColor.RED + "Purchase: $7500");
		this.gui = gui;
		this.size = gui.getInventory().getSize();
		this.title = title;
		this.inv = Bukkit.getServer().createInventory(null, size, this.title);
	}

	public void setupIcons(Player p) {
		this.inv.clear();
		for (Kit k : KitManager.getInstance().getRegisteredKits()) {
			ItemStack icon = k.getIcon();
			ItemMeta meta = icon.getItemMeta();
			meta.setDisplayName(k.getName());
			if (p.hasPermission(k.getPermission())) {
				meta.setLore(purchased);
			} else {
				meta.setLore(selling);
			}
			icon.setItemMeta(meta);
			inv.addItem(icon);
			p.openInventory(inv);
		}
	}

	public Inventory getInventory() {
		return this.inv;
	}

	public KitGUI getGUI() {
		return this.gui;
	}

	public String getTitle() {
		return this.title;
	}

}
