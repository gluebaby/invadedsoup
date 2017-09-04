package me.undeadguppy.invadedsoup.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.undeadguppy.invadedsoup.Main;

public class KitShopCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kitshop")) {
			Player p = (Player) sender;
			Main.getInstance().getKitShop().setupIcons(p);
			return true;
		}
		return true;
	}

}
