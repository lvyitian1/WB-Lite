package top.whitecola.wlbot.util;

import java.util.List;

import org.bukkit.entity.Player;

public class PlayerUtil {
	public static String getPlayerListString(List<Player> pl) {
		if(pl.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		pl.forEach(i->{
			sb.append(i.getName()+" ");
		});
		return sb.toString();
	}
}
