package top.whitecola.wblite.listener;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import top.whitecola.wblite.WBLite;
import top.whitecola.wlbot.util.BotUtil;
import top.whitecola.wlbot.util.GroupUtil;

public class EntityListener implements Listener {
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=false)
	public void onCreeperExplosionPrime(ExplosionPrimeEvent e) {
		if((!WBLite.instance.config.getConfig().serverEventSentToGroup) || (!WBLite.instance.config.getConfig().creeperEventMsg)) {
			return;
		}
		if(e.getEntity()==null) {
			return;
		}
		if(e.getEntityType()==EntityType.CREEPER) {
			if(e.getEntity() instanceof Creeper) {
				Creeper cr = (Creeper)e.getEntity();
				Location loc = cr.getLocation();
				BotUtil.sendEventMessageToGroup("苦力怕爆炸在 "+loc.getWorld().getName()+"世界 "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
				Player tp = null;
				if(cr.getTarget() instanceof Player) {
					tp = (Player)cr.getTarget();
				}
				if(tp!=null) {
					GroupUtil.sendMsgToGroupAuto("苦力怕爆炸目标玩家为 "+tp.getName());
				}else {
					GroupUtil.sendMsgToGroupAuto("没有找到苦力怕爆炸目标玩家,攻击目标玩家可能已死亡或下线 或者该苦力怕是被打火石引爆");
				}
			}
		}
	}
}
