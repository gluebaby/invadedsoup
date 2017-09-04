package me.undeadguppy.invadedsoup.kit;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.undeadguppy.invadedsoup.Main;

public abstract class Kit implements Listener {

	public enum KitType {
		SNOWMAN, SOUP, ARCHER, POTION, SWITCHER, VIPER, FISHERMAN, SNAIL, THOR, GRANDPA, KANGAROO, URGAL, VIKING, SONIC, NINJA, STOMPER
	}

	private Main core;
	private String name;
	private ItemStack icon;
	private KitType type;
	private String permission;

	public Kit(String name, ItemStack icon, KitType type, String permisson) {
		this.permission = permisson;
		this.name = name;
		this.icon = icon;
		this.type = type;
		this.core = Main.getInstance();
		core.getServer().getPluginManager().registerEvents(this, core);
	}

	public String getName() {
		return name;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public KitType getType() {
		return type;
	}

	public String getPermission() {
		return permission;
	}

	public abstract void applyItems(Player p);

}
