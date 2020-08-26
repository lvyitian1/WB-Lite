package top.whitecola.wlbot.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.GroupMessageEvent;
import top.whitecola.wblite.WBLite;

public class BotUtil {
	public static void sendGroupMsgToServer(String msg,GroupMessageEvent ge) { 

		if(!WBLite.instance.config.getConfig().groupMsgSendToGame) {
			return;
		}
		if(WBLite.instance.config.getConfig().isMsgSynchronousStartWithString) {
			if(!msg.startsWith(WBLite.instance.config.getConfig().msgSynchronousStartWithStringContent)) {
				return;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("§l["+ge.getGroup().getId()+"]§r");
		if(ge.getSender().getPermission() == MemberPermission.OWNER) {
			sb.append("§c[群主]§r");
		}else if(ge.getSender().getPermission() == MemberPermission.ADMINISTRATOR) {
			sb.append("§6[管理员]§r");
		}
		sb.append("{"+ge.getSenderName()+"} ");
		sb.append("§a"+msg);
		Bukkit.getScheduler().runTask(WBLite.instance, ()->{
			Bukkit.getServer().broadcastMessage(sb.toString());
		});
	}
	
	public static void sendServerMsgToGroup(String msg,Player p) {
		if(!WBLite.instance.config.getConfig().serverMsgSendToGroup) {
			return;
		}
		if(WBLite.instance.config.getConfig().isMsgSynchronousStartWithString) {
			if(!msg.startsWith(WBLite.instance.config.getConfig().msgSynchronousStartWithStringContent)) {
				return;
			}
		}
		GroupUtil.sendMsgToGroupAuto("["+p.getName()+"]"+msg);
	}
	
	public static void sendEventMessageToGroup(String msg) {
		GroupUtil.sendMsgToGroupAuto("[服务器事件]"+msg);
	}

}
