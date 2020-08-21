package top.whitecola.wlbot.util;

import java.util.Vector;
import java.util.stream.Collectors;
import net.mamoe.mirai.contact.Group;
import top.whitecola.wblite.WBLite;

public class GroupUtil {
	public static boolean isBotInGroup(long group) {
		if(WBLite.instance.bot.getGroup(group)!=null) {
			return true;
		}
		return false;
	}
	public static boolean isInGroup(long qq,long group) {
		if(isBotInGroup(group)) {
			return WBLite.instance.bot.getGroup(group).contains(qq);
		}else {
			return false;
		}
	}
	public static boolean isInConfigGroup(long group) {
		return WBLite.instance.config.getConfig().useBotGroup.contains(group);
	}
	public static boolean isQQInConfigGroup(long qq)
	{
		
		for(long i : WBLite.instance.config.getConfig().useBotGroup)
			if(isBotInGroup(i)) {
				if(WBLite.instance.bot.getGroup(i).contains(qq))
					return true;
			}
		return false;
	}
	public static boolean isQQInTargetConfigGroup(long qq,long group)
	{
		return isInConfigGroup(group)&&isInGroup(qq,group);
	}
	public static boolean isQQInGroupAuto(long qq) {
		if(WBLite.instance.config.getConfig().isAllGroupUseBot||WBLite.instance.config.getConfig().useBotGroup.parallelStream().map(GroupUtil::getGroup).filter(i->i!=null).collect(Collectors.toList()).isEmpty()) {
			
			for(Group group : WBLite.instance.bot.getGroups()) {
				if (group.getMembers().parallelStream().anyMatch(i->i.getId()==qq)) {
					return true;
				}
			}
			
		}else {
			return isQQInConfigGroup(qq);
		}
		return false;
	}
	public static void sendMsgToAllGroup(String msg)
	{
		for(Group group : WBLite.instance.bot.getGroups()) {
			group.sendMessage(msg);
		}

	}
	public static Group getGroup(long i)
	{
		try {
			return WBLite.instance.bot.getGroup(i);
		}catch (Throwable e) {
			
		}
		return null;
	}
	public static void sendMsgToAllTrueConfigGroup(String msg)
	{
		if(WBLite.instance.config.getConfig().useBotGroup.isEmpty()) {
			WBLite.instance.config.getConfig().isAllGroupUseBot = true;
			sendMsgToAllGroup(msg);
			return;
		}
		for(Group group : WBLite.instance.config.getConfig().useBotGroup.parallelStream().map(GroupUtil::getGroup).filter(i->i!=null).collect(Collectors.toList())) {
			if(isBotInGroup(group.getId())) {
				group.sendMessage(msg);
			}
		}
	}
	
	public static void sendMsgToGroupAuto(String msg) {
		if(WBLite.instance.config.getConfig().isAllGroupUseBot||WBLite.instance.config.getConfig().useBotGroup.parallelStream().map(GroupUtil::getGroup).filter(i->i!=null).collect(Collectors.toList()).isEmpty()) {
			sendMsgToAllGroup(msg);
			return;
		}else {
			sendMsgToAllTrueConfigGroup(msg);
			return;
		}
	}
	
	
	public static boolean isBotInAllConfigGroup() {
		for(long group : WBLite.instance.config.getConfig().useBotGroup) {
			if(!isBotInGroup(group)) {
				return false;
			}
		}
		return true;
	}
	public static Vector<Long> getAllTrueBotInConfigGroup() {
		Vector<Long> allTrueGroup = new Vector<Long>();
		for(long group : WBLite.instance.config.getConfig().useBotGroup) {
			if(isBotInGroup(group)) {
				allTrueGroup.add(group);
			}
		}
		if(!allTrueGroup.isEmpty()) {
			return allTrueGroup;
		}
		return null;
	}
}
