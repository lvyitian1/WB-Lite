package top.whitecola.wblite.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig.WLPlayer;
import top.dsbbs2.whitelist_shared.CommandUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.whitecola.wblite.WBLite;
import top.whitecola.wlbot.util.BotUtil;
import top.whitecola.wlbot.util.GroupUtil;

public class PlayerListener implements Listener {
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent e) {
		if (e.getResult()==Result.ALLOWED) {
			WBLite.instance.addTask(()->{
				if(WBLite.instance.config.getConfig().playerJoinServerGroupMsg) {
					sendJoinOrLeaveServerGroupMsg(1, e.getPlayer());
				}
				if(WBLite.wl==null)
					return;
				WLPlayer wlp = PlayerUtil.getWLPlayerByName(e.getPlayer().getName());
				if(wlp!=null&&wlp.QQ!=-1) {
					if(!GroupUtil.isQQInGroupAuto(wlp.QQ)) {
						Bukkit.getScheduler().runTask(WBLite.instance, ()->{
							top.dsbbs2.whitelist.util.CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "remove").onCommand(Bukkit.getConsoleSender(), WhiteListPlugin.instance.getCommand("wl"), "wl", new String[] {"remove",e.getPlayer().getName()});
						});
					}
				}
			});
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(WBLite.instance.config.getConfig().playerLeaveServerGroupMsg) {
			WBLite.instance.addTask(()->{
				sendJoinOrLeaveServerGroupMsg(2, e.getPlayer());
			});
		}
	}


	public void sendJoinOrLeaveServerGroupMsg(int type,Player p) {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		//type ==1 join
		Object wlp=WBLite.wl!=null?PlayerUtil.getWLPlayerByName(p.getName()):null;
		if(wlp!=null)
		{
			if(((WhiteListConfig.WLPlayer)wlp).QQ!=-1) {
				if(type==1) {
					GroupUtil.sendMsgToGroupAuto("玩家 "+p.getName()+"["+((WhiteListConfig.WLPlayer)wlp).QQ+"] 加入了游戏.");
					GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size())+" 人.");
					return;
				}else {
					GroupUtil.sendMsgToGroupAuto("玩家 "+p.getName()+"["+((WhiteListConfig.WLPlayer)wlp).QQ+"] 退出了游戏.");
					GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size())+" 人.");
					return;
				}
			}else {
				if(type==1) {
					GroupUtil.sendMsgToGroupAuto("玩家 "+p.getName()+" 加入了游戏.");
					GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size())+" 人.");
					return;
				}else {
					GroupUtil.sendMsgToGroupAuto("玩家 "+p.getName()+" 退出了游戏.");
					GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size())+" 人.");
					return;
				}

			}
		}else {
			if(type==1) {
				GroupUtil.sendMsgToGroupAuto((WBLite.wl!=null?"没有白名单的":"")+"玩家 "+p.getName()+" 加入了游戏.");
				GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size()+1)+" 人.");
				return;
			}else {
				GroupUtil.sendMsgToGroupAuto((WBLite.wl!=null?"没有白名单的":"")+"玩家 "+p.getName()+" 退出了游戏.");
				GroupUtil.sendMsgToGroupAuto("目前服务器人数: "+(p.getServer().getOnlinePlayers().size()-1)+" 人.");
				return;
			}
		}




	}

	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=false)
	public void onPlayerMsg(AsyncPlayerChatEvent e) {
		if(WBLite.instance.config.getConfig().synchronousMsgFunction) {
			WBLite.instance.addTask(()->{
				BotUtil.sendServerMsgToGroup(e.getMessage(),e.getPlayer());
			});

		}


	}

	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=false)
	public void onPlayerDie(PlayerDeathEvent e) {
		if(!WBLite.instance.config.getConfig().sendDieMsgToServerWhenPlayerDie) {
			return;
		}
		
		WBLite.instance.addTask(()->{
			BotUtil.sendEventMessageToGroup("玩家"+e.getEntity().getPlayer().getName()+"已死亡 , 死亡消息: "+e.getDeathMessage());
			if(WBLite.wl!=null){
			WLPlayer wlp = PlayerUtil.getWLPlayerByName(e.getEntity().getName());
			if(wlp!=null&&wlp.QQ!=-1) {
				GroupUtil.sendMsgToGroupAuto("死亡坐标已经在服务器中发给"+wlp.name+"["+wlp.QQ+"]");
			}
			}else{
				GroupUtil.sendMsgToGroupAuto("死亡坐标已经在服务器中发给"+e.getEntity().getName());
			}
		});
		


		Location loc = e.getEntity().getLocation();
		e.getEntity().getPlayer().sendMessage("[WB]§e你上次死亡在了 "+loc.getWorld().getName()+"世界 "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ());
	}


}
