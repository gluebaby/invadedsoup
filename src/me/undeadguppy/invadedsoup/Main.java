package me.undeadguppy.invadedsoup;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.undeadguppy.invadedsoup.cmd.CommandManager;
import me.undeadguppy.invadedsoup.cmd.KitShopCMD;
import me.undeadguppy.invadedsoup.kit.KitManager;
import me.undeadguppy.invadedsoup.kit.shop.KitShop;
import me.undeadguppy.invadedsoup.kitselector.ClickEvent;
import me.undeadguppy.invadedsoup.kitselector.KitGUI;
import me.undeadguppy.invadedsoup.listeners.PlayerListener;
import me.undeadguppy.invadedsoup.listeners.SpawnManager;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Main inst;
	public static Economy econ = null;

	public static Main getInstance() {
		return inst;
	}

	private KitGUI kgui;
	private KitShop kshop;
	private SpawnManager sm;

	public KitGUI getKitGUI() {
		return kgui;
	}

	public KitShop getKitShop() {
		return kshop;
	}

	@Override
	public void onEnable() {
		inst = this;
		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		KitManager.getInstance().setup();
		kgui = new KitGUI(ChatColor.GREEN + "Kit Selector", 27);
		kshop = new KitShop(kgui, ChatColor.GREEN + "Kit Shop");
		PluginManager pm = Bukkit.getServer().getPluginManager();
		sm = new SpawnManager();
		pm.registerEvents(new ClickEvent(), this);
		new PlayerListener(this);
		getCommand("kit").setExecutor(new CommandManager());
		getCommand("kitshop").setExecutor(new KitShopCMD());
		getCommand("spawn").setExecutor(sm);
		pm.registerEvents(sm, this);
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

}
