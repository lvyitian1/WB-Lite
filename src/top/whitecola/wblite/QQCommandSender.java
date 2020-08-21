package top.whitecola.wblite;

import java.util.Set;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.At;

public class QQCommandSender implements CommandSender, ConsoleCommandSender{
	public User QQ;
	public long group;
	public QQCommandSender(User qq,long group)
	{
		this.QQ=qq;
		this.group=group;
	}
@Override
public boolean equals(Object o)
{
	if(o instanceof QQCommandSender)
	{
		QQCommandSender oq=(QQCommandSender)o;
		return oq.QQ==this.QQ;
	}
	return false;
}
	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		return new PermissionAttachment(arg0,new Permissible() {

			@Override
			public boolean isOp() {
				return true;
			}

			@Override
			public void setOp(boolean arg0) {
				
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions() {
				return new TreeSet<>();
			}

			@Override
			public boolean hasPermission(String arg0) {
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0) {
				return true;
			}

			@Override
			public void recalculatePermissions() {
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0) {
				
				
			}});
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		return new PermissionAttachment(arg0,new Permissible() {

			@Override
			public boolean isOp() {
				return true;
			}

			@Override
			public void setOp(boolean arg0) {
				
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions() {
				return new TreeSet<>();
			}

			@Override
			public boolean hasPermission(String arg0) {
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0) {
				return true;
			}

			@Override
			public void recalculatePermissions() {
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0) {
				
				
			}});
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		return new PermissionAttachment(arg0,new Permissible() {

			@Override
			public boolean isOp() {
				return true;
			}

			@Override
			public void setOp(boolean arg0) {
				
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions() {
				return new TreeSet<>();
			}

			@Override
			public boolean hasPermission(String arg0) {
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0) {
				return true;
			}

			@Override
			public void recalculatePermissions() {
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0) {
				
				
			}});
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		return new PermissionAttachment(arg0,new Permissible() {

			@Override
			public boolean isOp() {
				return true;
			}

			@Override
			public void setOp(boolean arg0) {
				
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
				return new PermissionAttachment(arg0,this);
			}

			@Override
			public Set<PermissionAttachmentInfo> getEffectivePermissions() {
				return new TreeSet<>();
			}

			@Override
			public boolean hasPermission(String arg0) {
				return true;
			}

			@Override
			public boolean hasPermission(Permission arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(String arg0) {
				return true;
			}

			@Override
			public boolean isPermissionSet(Permission arg0) {
				return true;
			}

			@Override
			public void recalculatePermissions() {
				
			}

			@Override
			public void removeAttachment(PermissionAttachment arg0) {
				
				
			}});
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return new TreeSet<>();
	}

	@Override
	public boolean hasPermission(String arg0) {
		return true;
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		return true;
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		return true;
	}

	@Override
	public void recalculatePermissions() {
		
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		
	}

	@Override
	public boolean isOp() {
		return true;
	}

	@Override
	public void setOp(boolean arg0) {
		
	}

	@Override
	public String getName() {
		return "Group: "+group+" QQ: "+String.valueOf(this.QQ.getId());
	}

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}
	public void sendMessageToQQ(String msg)
	{
		if(group!=-1)
			  WBLite.instance.bot.getGroup(this.group).sendMessage(new At(WBLite.instance.bot.getGroup(this.group).get(this.QQ.getId())).plus(msg));
		else
			this.QQ.sendMessage(msg);
	}
	@Override
	public void sendMessage(String arg0) {
		try {
			sendMessageToQQ(arg0);
			}catch(Throwable e) {throw new RuntimeException(e);}
	}

	@Override
	public void sendMessage(String[] arg0) {
		for(String i : arg0)
			try {
				sendMessageToQQ(i);
				}catch(Throwable e) {throw new RuntimeException(e);}
	}

	@Override
	public Spigot spigot() {
		return new Spigot();
	}
	@Override
	public void abandonConversation(Conversation arg0) {
		
	}
	@Override
	public void abandonConversation(Conversation arg0, ConversationAbandonedEvent arg1) {
		
	}
	@Override
	public void acceptConversationInput(String arg0) {
		try {
			sendMessageToQQ(arg0);
			}catch(Throwable e) {throw new RuntimeException(e);}
	}
	@Override
	public boolean beginConversation(Conversation arg0) {
		return true;
	}
	@Override
	public boolean isConversing() {
		return true;
	}
	@Override
	public void sendRawMessage(String arg0) {
		try {
			sendMessageToQQ(arg0);
		}catch(Throwable e) {throw new RuntimeException(e);}
		//System.out.println("receive: "+arg0);
	}

}
