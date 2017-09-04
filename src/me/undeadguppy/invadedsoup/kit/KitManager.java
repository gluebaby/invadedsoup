package me.undeadguppy.invadedsoup.kit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.undeadguppy.invadedsoup.kit.kits.ArcherKit;
import me.undeadguppy.invadedsoup.kit.kits.FisherKit;
import me.undeadguppy.invadedsoup.kit.kits.GrandpaKit;
import me.undeadguppy.invadedsoup.kit.kits.KangarooKit;
import me.undeadguppy.invadedsoup.kit.kits.NinjaKit;
import me.undeadguppy.invadedsoup.kit.kits.PotionKit;
import me.undeadguppy.invadedsoup.kit.kits.PvPKit;
import me.undeadguppy.invadedsoup.kit.kits.SnailKit;
import me.undeadguppy.invadedsoup.kit.kits.SnowmanKit;
import me.undeadguppy.invadedsoup.kit.kits.SonicKit;
import me.undeadguppy.invadedsoup.kit.kits.StomperKit;
import me.undeadguppy.invadedsoup.kit.kits.SwitcherKit;
import me.undeadguppy.invadedsoup.kit.kits.ThorKit;
import me.undeadguppy.invadedsoup.kit.kits.UrgalKit;
import me.undeadguppy.invadedsoup.kit.kits.VikingKit;
import me.undeadguppy.invadedsoup.kit.kits.ViperKit;
import net.md_5.bungee.api.ChatColor;

public class KitManager {

	private static KitManager inst;
	private HashSet<Kit> kitlist;

	public static KitManager getInstance() {
		if (inst == null) {
			inst = new KitManager();
		}
		return inst;
	}

	private HashMap<UUID, Kit> kits;

	public void setup() {
		kits = new HashMap<UUID, Kit>();
		kitlist = new HashSet<Kit>();
		kitlist.add(new ArcherKit());
		kitlist.add(new FisherKit());
		kitlist.add(new GrandpaKit());
		kitlist.add(new KangarooKit());
		kitlist.add(new PotionKit());
		kitlist.add(new PvPKit());
		kitlist.add(new SnailKit());
		kitlist.add(new SnowmanKit());
		kitlist.add(new SwitcherKit());
		kitlist.add(new ThorKit());
		kitlist.add(new ViperKit());
		kitlist.add(new NinjaKit());
		kitlist.add(new SonicKit());
		kitlist.add(new VikingKit());
		kitlist.add(new UrgalKit());
		kitlist.add(new StomperKit());
		kits.clear();
	}

	public void applyKitSelector(Player p) {
		p.getActivePotionEffects().clear();
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		ItemStack chest = new ItemStack(Material.CHEST);
		ItemMeta meta = chest.getItemMeta();
		meta.setDisplayName(ChatColor.GREEN + "Kit Selector");
		chest.setItemMeta(meta);
		p.getInventory().setItem(4, chest);
	}

	public HashSet<Kit> getRegisteredKits() {
		return kitlist;
	}

	public void setKit(Player p, Kit k) {
		kits.put(p.getUniqueId(), k);
		k.applyItems(p);
		p.sendMessage(ChatColor.GREEN + "You have equipped the " + k.getName() + " kit!");
	}

	public void removeKit(Player p) {
		if (kits.containsKey(p.getUniqueId())) {
			kits.remove(p.getUniqueId());
			applyKitSelector(p);
		}
	}

	public Kit getKit(Player p) {
		if (kits.containsKey(p.getUniqueId())) {
			return kits.get(p.getUniqueId());
		}
		return null;
	}

}
