package top.whitecola.wblite.commands;

import java.util.Optional;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.WatchdogThread;

import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import top.dsbbs2.common.config.SimpleConfig;
import top.dsbbs2.common.lambda.INoThrowsRunnable;
import top.dsbbs2.whitelist.commands.IChildCommand;
import top.dsbbs2.whitelist.util.VectorUtil;
import top.whitecola.wblite.BotListener;
import top.whitecola.wblite.EventUtils;
import top.whitecola.wblite.WBLite;
import top.whitecola.wlbot.util.GroupUtil;

public class Reload implements IChildCommand{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1) {
			Bukkit.getServer().broadcastMessage("群机器人将重启!");
			GroupUtil.sendMsgToGroupAuto("机器人即将重启,重启过程中会出现不回消息等状况!");
			INoThrowsRunnable.invoke(WBLite.instance.config::loadConfig);
			System.out.println("已经尝试加载配置文件,如果出现nullPointerException,很可能是配置文件因更改而损坏,或者有选项没有填写!");
			if(WBLite.instance.config.getConfig().password==null||WBLite.instance.config.getConfig().password.isEmpty())
			{
				WBLite.instance.getLogger().severe("请先填写"+((SimpleConfig<?>)WBLite.instance.config).conf.getAbsolutePath().toString()+"中的qq(账号)和password(密码)！再重载本插件");
				
				return true;
			}
			
			Optional.ofNullable(WBLite.instance.bot).ifPresent(i->i.close(new Throwable()));
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
			WBLite.instance.bot=BotFactoryJvm.newBot(WBLite.instance.config.getConfig().qq, WBLite.instance.config.getConfig().password,new BotConfiguration() {{
				fileBasedDeviceInfo("devicenfo.json");
				if(!WBLite.instance.config.getConfig().robotShowDebugMsg) {
					noNetworkLog();
				}
				if(!WBLite.instance.config.getConfig().robotShowLogMsgDebug) {
					noBotLog();
				}
			}});
			EventUtils.registerEvents(new BotListener());
			WBLite.instance.bot.login();
			}finally {
				temp.stop();
			}
			System.out.println("机器人重启完毕!");
			GroupUtil.sendMsgToGroupAuto("机器人重启完毕!");
			return true;
		}
		return false;
	}
	@NotNull
    @Override
    public String getUsage() {
        return "/wb reload";
    }

    @NotNull
    @Override
    public Vector<Class<?>> getArgumentsTypes()
    {
        return VectorUtil.toVector(String.class);
    }

    @NotNull
    @Override
    public Vector<String> getArgumentsDescriptions()
    {
        return VectorUtil.toVector();
    }

    @NotNull
    @Override
    public String getPermission()
    {
        return "wb.reload";
    }
    @NotNull
    @Override
    public String getDescription(){
        return "重新读取配置文件,重启机器人.";
    }
}
