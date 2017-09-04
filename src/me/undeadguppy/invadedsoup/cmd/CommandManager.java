package me.undeadguppy.invadedsoup.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.undeadguppy.invadedsoup.Main;
import me.undeadguppy.invadedsoup.kit.KitManager;
import net.md_5.bungee.api.ChatColor;

public class CommandManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (KitManager.getInstance().getKit((Player) sender) != null) {
				sender.sendMessage(ChatColor.RED + "You already have a kit! Use /spawn or die to change it!");
				return true;
			}
			Player p = (Player) sender;
			Main.getInstance().getKitGUI().setupIcons(p);
			return true;
		}
		return true;
	}

}
