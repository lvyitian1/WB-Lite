package top.whitecola.wblite;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.spigotmc.WatchdogThread;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import top.dsbbs2.common.config.IConfig;
import top.dsbbs2.common.config.SimpleConfig;
import top.dsbbs2.common.file.FileUtils;
import top.dsbbs2.common.lambda.INoThrowsRunnable;
import top.dsbbs2.common.lambda.IThrowsRunnable;
import top.dsbbs2.whitelist_shared.IChildCommand;
import top.dsbbs2.whitelist_shared.CommandUtil;
import top.dsbbs2.whitelist_shared.ListUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist_shared.TabUtil;
import top.dsbbs2.whitelist_shared.VectorUtil;
import top.whitecola.wblite.commands.Reload;
import top.whitecola.wblite.commands.SendMsg;
import top.whitecola.wblite.listener.BlockListener;
import top.whitecola.wblite.listener.EntityListener;
import top.whitecola.wblite.listener.PlayerListener;
import top.whitecola.wblite.listener.WordListener;

public class WBLite extends JavaPlugin{
	public volatile Vector<IChildCommand> childCmds=new Vector<>();
	{
		initCommands();
		instance = this;
	}
	public IConfig<Config> config = new SimpleConfig<Config>(getDataFolder().getAbsolutePath().toString()+File.separator+"config.json","UTF8",Config.class) {{
		INoThrowsRunnable.invoke(this::loadConfig);
		System.out.println("已经尝试加载配置文件,如果出现nullPointerException,很可能是配置文件因更改而损坏,或者有选项没有填写!");
		if (!this.g.toJson(this.getConfig()).trim().equals(FileUtils.readTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"config.json"), StandardCharsets.UTF_8).trim())) {
			FileUtils.writeTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"config.json.bak"),FileUtils.readTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"config.json"), StandardCharsets.UTF_8),StandardCharsets.UTF_8,false);
			INoThrowsRunnable.invoke(this::saveConfig);
		}
	}};
	public IConfig<UUIDStorage> uuid = new SimpleConfig<UUIDStorage>(getDataFolder().getAbsolutePath().toString()+File.separator+"uuid.json","UTF8",UUIDStorage.class) {{
		INoThrowsRunnable.invoke(this::loadConfig);
		if (!this.g.toJson(this.getConfig()).trim().equals(FileUtils.readTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"uuid.json"), StandardCharsets.UTF_8).trim())) {
			FileUtils.writeTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"uuid.json.bak"),FileUtils.readTextFile(new File(getDataFolder().getAbsolutePath().toString()+File.separator+"uuid.json"), StandardCharsets.UTF_8),StandardCharsets.UTF_8,false);
			INoThrowsRunnable.invoke(this::saveConfig);
		}
	}};
	public static WBLite instance;
	public static Plugin wl;
	public Bot bot;
	public String awa = "";
	private final CopyOnWriteArrayList<IThrowsRunnable> tasks=new CopyOnWriteArrayList<>();
	public final Object lock=new Object();
	public final Thread taskThread=new Thread(()->{
		while(true)
		{
			synchronized (lock) {
				for(IThrowsRunnable i : tasks)
				{
					try {
						i.runInternal();
					} catch (Throwable e) {
						if(e instanceof ThreadDeath)
							throw (ThreadDeath)e;
						e.printStackTrace();
					}
				}
				tasks.clear();
			}
			INoThrowsRunnable.invoke(()->Thread.sleep(1));
		}
	});
	public void addTask(IThrowsRunnable t)
	{
		synchronized (lock) {
			tasks.add(t);
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		wl=Bukkit.getPluginManager().getPlugin("WhiteList");
		if(wl==null)
			Bukkit.getServer().getConsoleSender().sendMessage("§e未安装白名单插件，相关功能将不可用!");
		taskThread.start();
		if(config.getConfig().password==null||config.getConfig().password.isEmpty())
		{
			try {
			getLogger().severe("请先填写"+((SimpleConfig<?>)this.config).conf.getAbsolutePath().toString()+"中的qq(账号)和password(密码)！再重载本插件");
			this.setEnabled(false);
			return;
		       }finally {
			 taskThread.stop();
		       }
			
		}
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new WordListener(),this);
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Thread temp=new Thread(()->{
			while(true)
			{
				try {
				WatchdogThread.tick();
				}catch (Throwable e) {
					return;
				}
				try {
					Thread.sleep(1);
				}catch (InterruptedException e) {
					return;
				}
			}
		});
		temp.setDaemon(true);
		try {
		temp.start();
		bot=BotFactoryJvm.newBot(this.config.getConfig().qq, this.config.getConfig().password,new BotConfiguration() {{
			fileBasedDeviceInfo("devicenfo.json");
			if(!config.getConfig().robotShowDebugMsg) {
				noNetworkLog();
			}
			if(!config.getConfig().robotShowLogMsgDebug) {
				noBotLog();
			}
			System.out.println("§b机器人配置文件已就绪.");		
		}});
		
		System.out.println("§bWB精简版");
		if("你好,反编译者!".equals(awa)) {
			System.out.println("反编译干嘛awa");
		}
		EventUtils.registerEvents(new BotListener());
		bot.login();
		}finally {
			temp.stop();
		}
		System.out.println("§b机器人已经成功启动.");
		
	}
	public void initCommands() {
		this.childCmds.add(new Reload());
		this.childCmds.add(new SendMsg());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		try {
		taskThread.stop();
		}finally {
			Optional.ofNullable(this.bot).ifPresent(i->i.close(new Throwable()));
		}
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length>0)
		{
			IChildCommand c=CommandUtil.getCommand(this.childCmds,args[0]);
			if(c==null)
			{

				sender.sendMessage("命令 "+args[0]+" 不存在");
			}else {
				if(!c.getPermission().trim().equals("") && !sender.hasPermission(c.getPermission()))
				{
					sender.sendMessage("你没有权限这么做,你需要"+c.getPermission()+"权限!");
					return true;
				}
				if(!c.onCommand(sender, command, label, args))
				{
					String usage=c.getUsage();
					if(!usage.trim().equals(""))
						sender.sendMessage(usage);
				}
			}
			return true;
		}
		return super.onCommand(sender, command, label, args);
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length>0)
			return TabUtil.betterGetStartsWithList(realOnTabComplete(sender,command,alias,args),args[args.length-1]);
		else
			return realOnTabComplete(sender,command,alias,args);
	}
	
	public List<String> realOnTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length<=1)
			return VectorUtil.toArrayList(CommandUtil.commandListToCommandNameList(childCmds));
		if(args.length>1)
		{
			IChildCommand c=CommandUtil.getCommand(childCmds, args[0]);
			if(c!=null)
			{
				Vector<Class<?>> cats=c.getArgumentsTypes();
				if(cats.size()>args.length-2)
				{
					Class<?> argType=cats.get(args.length-2);
					Vector<String> des=c.getArgumentsDescriptions();
					String desc=null;
					if(des.size()>args.length-2)
						desc=des.get(args.length-2);
					if(desc==null)
					{
						return ListUtil.toList(argType.getSimpleName());
					}else if(desc.equals("player"))
					{
						return wl!=null?PlayerUtil.getOfflinePlayersNameList():new ArrayList<>();
					}else if(desc.contains("/")){
						return ListUtil.toList(desc.split("/"));
					}else{
						return ListUtil.toList(desc);
					}
				}
			}
		}
		return new ArrayList<>();
	}
}
