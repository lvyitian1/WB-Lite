package top.whitecola.wlbot.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class CommandUtil {
	public static double getDis(Location a, Location b) {
        double i = Math.pow(a.getX() - b.getX(), 2);
        double j = Math.pow(a.getZ() - b.getZ(), 2);
        double k = Math.pow(a.getY() - b.getY(), 2);
        return Math.sqrt(i + j + k);
    }
	
	public static List<Player> getNearlyPlayer(double dis,Entity en){
		List<Player> pl = new ArrayList<>();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(p.getWorld().equals(en.getWorld())) {
				if(getDis(en.getLocation(), p.getLocation())<=dis) {
					pl.add(p);
				}
			}
		}
		return pl;
	}
}
