package top.whitecola.wblite.commands;

import java.util.NoSuchElementException;
import java.util.Vector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import top.dsbbs2.whitelist.commands.IChildCommand;
import top.dsbbs2.whitelist.util.CommandUtil;
import top.dsbbs2.whitelist.util.VectorUtil;
import top.whitecola.wblite.WBLite;
import top.whitecola.wlbot.util.GroupUtil;

public class SendMsg implements IChildCommand{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg3.length<2) {
			arg0.sendMessage("请填写要发送的内容");
			return false;
		}
		if(arg3.length==2) {
			GroupUtil.sendMsgToAllGroup(arg3[1]);
			return true;
		}else if(arg3.length==3) {
			if(!CommandUtil.ArgumentUtil.isLong(arg3[2])) {
				arg0.sendMessage("群号必须是数字");
				return false;
			}
			try {
			WBLite.instance.bot.getGroup(Long.parseLong(arg3[2])).sendMessage(arg3[1]);
			return true;
			}catch (NoSuchElementException e) {
				arg0.sendMessage("机器人没有加群"+arg3[2]+",请先让机器人加群");
				return true;
			}
		}
		return false;
	}
	@NotNull
    @Override
    public String getUsage() {
        return "/wb sendmsg <msg> [groupid]";
    }

    @NotNull
    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(String.class,long.class);
    }

    @NotNull
    @Override
    public Vector<String> getArgumentsDescriptions()
    {
    	Vector<String> ret=new Vector<>();
    	ret.add(0, "msg");
    	StringBuilder tmp=new StringBuilder();
    	ContactList<Group> tmp2=WBLite.instance.bot.getGroups();
    	for(int i=0;i<tmp2.size();i++) {
    		try {
    		tmp.append(tmp2.get(i).getId());
    		}catch (Throwable e) {
				continue;
			}
    		if(i+1<tmp2.size())
    			tmp.append("/");
    	}
    	ret.add(tmp.toString());
    	return ret;
    }

    @NotNull
    @Override
    public String getPermission()
    {
        return "wb.sendmsg";
    }
    @NotNull
    @Override
    public String getDescription(){
        return "让机器人发消息到指定群(不指定就是所有群)";
    }
}
