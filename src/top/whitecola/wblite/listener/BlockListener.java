package top.whitecola.wblite.listener;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import top.whitecola.wblite.WBLite;
import top.whitecola.wlbot.util.BotUtil;

public class BlockListener implements Listener{
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=false)
	public void onTNTExplosionPrime(ExplosionPrimeEvent e) {
		if((!WBLite.instance.config.getConfig().synchronousMsgFunction )||(!WBLite.instance.config.getConfig().TNTEventMsg)) {
			return;
		}
		if(e.getEntity()==null) {
			return;
		}
		if(e.getEntityType()== EntityType.PRIMED_TNT) {
			Location loc = e.getEntity().getLocation().clone();
			WBLite.instance.addTask(()->
				BotUtil.sendEventMessageToGroup("一个TNT爆炸在"+loc.getWorld().getName()+"世界 "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ())
			);
			
			
		}

	}
}
