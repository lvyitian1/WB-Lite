package top.whitecola.wblite;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageEvent;
import net.mamoe.mirai.message.data.At;
import top.dsbbs2.whitelist.WhiteListPlugin;
import top.dsbbs2.whitelist.config.struct.WhiteListConfig.WLPlayer;
import top.dsbbs2.whitelist_shared.CommandUtil;
import top.dsbbs2.whitelist.util.PlayerUtil;
import top.dsbbs2.whitelist.util.ServerUtil;
import top.whitecola.wlbot.util.BotUtil;
import top.whitecola.wlbot.util.GroupUtil;

public final class BotListener {
	public static boolean containsLong(long[] arr,long obj)
	{
		for(long i : arr)
			if(i==obj)
				return true;
		return false;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMessage(MessageEvent e) {
		String msg = e.getMessage().contentToString();
		if (e instanceof GroupMessageEvent) {
			GroupMessageEvent ge = (GroupMessageEvent) e;
			if (ge.getSender().getId() != WBLite.instance.bot.getId()) {

				if(WBLite.instance.config.getConfig().synchronousMsgFunction && !msg.startsWith(".")) {
					if(WBLite.instance.config.getConfig().isAllGroupUseBot || GroupUtil.isInConfigGroup(ge.getGroup().getId())) {
					BotUtil.sendGroupMsgToServer(msg,ge);
					}
				}

				if(WBLite.wl!=null&&(msg.startsWith(".申请白名单") || msg.startsWith("。申请白名单"))&&WBLite.instance.config.getConfig().memberAddWhiteListByOneself) {
					
					String[] temp = msg.split(" ");
					if (temp.length >= 2) {
						if (PlayerUtil.isInWhiteList(temp[1])) {

							WLPlayer wlp = PlayerUtil.getWLPlayerByName(temp[1]);
							ge.getGroup().sendMessage(new At(ge.getSender()).plus("该ID已被" + wlp.QQ + "绑定 !"));
							return;
						}

						if (!WBLite.instance.config.getConfig().allowAddingWhitelistBeforeChangingNameInGroup && !ge.getSender().getNameCard().toLowerCase(Locale.ENGLISH).contains(temp[1].toLowerCase(Locale.ENGLISH))) {
							ge.getGroup()
							.sendMessage(new At(ge.getSender()).plus("您需要在申请白名单之前,在您的群名片上加上您的id！"));
							return;
						}
						String name = temp[1];
						Bukkit.getScheduler().runTask(WBLite.instance, ()->CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "add").onCommand(
								new QQCommandSender(e.getSender(), ge.getGroup().getId()),
								WhiteListPlugin.instance.getCommand("wl"), "wl",
								new String[] { "add",
										name,
										""+ge.getSender().getId() }));

						return;
					}


				}else if(msg.startsWith(".执行") || msg.startsWith("。执行")) {

					if (WBLite.instance.config.getConfig().canGroupMsgExcuteCommand ) {
						if(!containsLong(WBLite.instance.config.getConfig().groupCommandOperator, ge.getSender().getId())) {
							ge.getGroup().sendMessage(new At(ge.getSender()).plus("你没有权限!"));
							return;
						}
						if (msg.length() <= 4 || !msg.contains(" ")) {
							((GroupMessageEvent) e).getGroup().sendMessage("正确用法:.(。)执行 <命令> (注意空格)");
							return;
						}

						if(containsLong(WBLite.instance.config.getConfig().limitedOperators, ge.getSender().getId())) {
							if (!msg.split(" ")[1].equals("wl")
									&& !msg.split(" ")[1].equalsIgnoreCase("whitelist:wl")) {
								((GroupMessageEvent) e).getGroup()
								.sendMessage(new At(((GroupMessageEvent) e).getSender())
										.plus("不允许的命令，您的远程权限已被限制,您只可以使用白名单插件(wl)的命令!"));
								return;
							}
						}

						Bukkit.getScheduler()
						.runTask(WBLite.instance,
								() -> Bukkit.dispatchCommand(
										new QQCommandSender(e.getSender(),
												((GroupMessageEvent) e).getGroup().getId()),
										msg.substring(4)));
						return;
					} else {
						ge.getGroup().sendMessage(new At(ge.getSender()).plus("远程执行命令功能没有开启."));
					}
				}else if(msg.startsWith(".状态") || msg.startsWith("。状态")){



					//						ServerListPing17 p = new ServerListPing17();
					try {
						//							p.setAddress(new InetSocketAddress(
						//									InetAddress.getByName(
						//											WBLite.instance.config.getConfig().serverIp),
						//									WBLite.instance.config.getConfig().serverPort));
						//StatusResponse re = p.fetchData();
						//Message temp = re.getFavicon() != null
						//		? ge.getGroup()
						//				.uploadImage(new ByteArrayInputStream(Base64.getDecoder().decode(
						//						re.getFavicon().substring("data:image/png;base64,".length()))))
						//				: new MessageChainBuilder().build();
						StringBuilder bu = new StringBuilder(/*"\n"*/);
						//bu.append("服务器名: " + Bukkit.getServerName());
						//bu.append("\n");
						bu.append("服务器名 : "+ WBLite.instance.config.getConfig().serverName);
						bu.append("\n");
						bu.append("服务器地址: " + WBLite.instance.config.getConfig().serverIp);
						bu.append("\n");
						bu.append("服务器端口: " + WBLite.instance.config.getConfig().serverPort);
						bu.append("\n");
						//bu.append("服务器Motd: " + re.getDescription().getText());
						//bu.append("\n");

						bu.append("服务器版本: " + Bukkit.getVersion());
						bu.append("\n");
						//bu.append("服务器协议版本号: " + re.getVersion().getProtocol());
						//bu.append("\n");
						//bu.append("延迟: " + re.getTime() + "ms");
						//bu.append("\n");
						try {
						bu.append("服务器当前tps: " + Bukkit.getTPS()[0]);
						bu.append("\n");
						}catch (Throwable e2) {}
						bu.append("人数: " + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
						bu.append("\n");
						if(WBLite.wl!=null)
						bu.append(
								"在线玩家列表: " + Arrays.toString(Bukkit.getOnlinePlayers().stream()
										.map(i -> i.getName() + "[" + Optional
												.ofNullable(ServerUtil.isOnlineStorageMode()
														? PlayerUtil.getWLPlayerByUUID(
																i.getUniqueId())
																: PlayerUtil.getWLPlayerByName(i.getName()))
										.map(i2 -> i2.QQ + "").orElse("未绑定") + "]")
										.toArray(String[]::new)).replace(",", ",\n")
								);
						else bu.append(
								"在线玩家列表: " + Arrays.toString(Bukkit.getOnlinePlayers().stream()
										.map(i -> i.getName())
										.toArray(String[]::new)).replace(",", ",\n")
								);
						bu.append("\n");
						bu.append("加载世界列表: "+Arrays.toString(Bukkit.getWorlds().parallelStream().map(World::getName).toArray(String[]::new)));
						bu.append("\n");
						bu.append("服务器的白名单系统由 更好的白名单插件(wl) 进行守护 ");
						ge.getGroup().sendMessage(/*temp.plus(*/bu.toString()/*)*/);
					} catch (Throwable exc) {
						ge.getGroup().sendMessage("在获取服务器基本信息的过程中出现了未预料到的异常 ");
						System.out.println("§e您是否没有配置服务器地址? 注意:这不是bug,请勿反馈!");
						ge.getGroup().sendMessage("是否没有配置服务器地址?");
						exc.printStackTrace();
						System.out.println("§e您是否没有配置服务器地址? 注意:这不是bug,请勿反馈!");
					}


				}else if(WBLite.wl!=null&&msg.startsWith(".验证") || msg.startsWith("。验证")) {
					if(ServerUtil.isOnlineStorageMode()) {

						CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "confirm").onCommand(
								new QQCommandSender(e.getSender(), ge.getGroup().getId()),
								WhiteListPlugin.instance.getCommand("wl"), "wl",
								new String[] { "confirm",""+ge.getSender().getId() });

					}else {
						ge.getGroup().sendMessage(new At(ge.getSender()).plus("服务器没有开启储存UUID模式 , 无需验证."));
					}
				}else if(msg.startsWith(".查询") || msg.startsWith("。查询")|| msg.startsWith(".whois") || msg.startsWith("。whois")) {
					String[] temp = msg.split(" ");
					if (temp.length == 2) {
						if(temp[1].equalsIgnoreCase("white_cola")||temp[1].equalsIgnoreCase("lvxinlei")||temp[1].equalsIgnoreCase("Chloe__owo")) {
							ge.getGroup().sendMessage("您查询的ID"+temp[1]+"为WL(白名单插件)的开发者的正版ID!");
						}
						StringBuilder bu = new StringBuilder("\n");
						if(WBLite.wl!=null)
						{
						WLPlayer wlp = PlayerUtil.getWLPlayerByName(temp[1]);
						if(wlp==null) {
							ge.getGroup().sendMessage(new At(ge.getSender()).plus("没有在白名单数据中查询到此人,他(她)是否还有没绑定?"));
							return;
						}
						if(wlp.QQ==-1) {
							ge.getGroup().sendMessage(new At(ge.getSender()).plus("确实在白名单数据中查到了他,但是没有查到对应QQ.(在添加该成员白名单时,并没有添加QQ)"));
							return;
						}
						ge.getGroup().sendMessage(new At(ge.getSender()).plus("该玩家为"+wlp.QQ));
						ge.getGroup().sendMessage(new At(ge.getGroup().get(wlp.QQ)));
						if(ServerUtil.isOnlineStorageMode()&&wlp.uuid!=null) {
							bu.append("玩家UUID: "+wlp.uuid+" \n");
						}
						}else{ 
							e.getGroup().sendMessage(new At(ge.getSender()).plus("白名单插件未安装，部分信息不可用!");
							OfflinePlayer op=Bukkit.getOfflinePlayer(temp[1]);
							bu.append("玩家UUID（可能不正确）: "+op.getUniqueId()+" \n");
					        }
						
						if(ge.getSender().getPermission()==MemberPermission.ADMINISTRATOR || ge.getSender().getPermission()==MemberPermission.OWNER) {
							OfflinePlayer op = WBLite.wl!=null?PlayerUtil.getWLPlayerByName(temp[1]).toOfflinePlayer():Bukkit.getOfflinePlayer(temp[1]);
							if(op==null) {
								return;
							}
							boolean o=op.isOnline();
							bu.append("玩家状态 : "+(o?"在线":"离线")+"\n");
							if(o)
							{
								bu.append("玩家经验值: "+op.getPlayer().getExp()+"["+op.getPlayer().getLevel()+"]"+"\n");
								bu.append("玩家所在世界: "+op.getPlayer().getWorld().getName()+"\n");
							}else {
								long temp2=op.getLastPlayed();
								bu.append("上次在线时间 :"+LocalDateTime.ofInstant(Instant.ofEpochMilli(temp2), ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now())).format(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd kk:mm:ss").toFormatter()));
							}
							ge.getGroup().sendMessage(bu.toString());
						}else {
							ge.getGroup().sendMessage(bu.toString());
							ge.getGroup().sendMessage(new At(ge.getSender()).plus("由于你并不是管理员,我们只能给你这些信息."));
						}
					}
				}


			}
		}

	}



	@EventHandler
	public void onMemberLeaveGroup(MemberLeaveEvent e) {

		if(!WBLite.instance.config.getConfig().isAllGroupUseBot) {
			if(!GroupUtil.isInConfigGroup(e.getGroup().getId())) {
				return;
			}
		}
		if (WBLite.wl!=null&&PlayerUtil.getWLPlayerByQQ(e.getMember().getId()) != null) {
			if (e instanceof MemberLeaveEvent.Kick) {
				e.getGroup().sendMessage("玩家" + PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).name + "["
						+ PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).QQ + "]" + "已经被管理员从本群删除,将删除他的白名单,并封禁!");

				Bukkit.getScheduler().runTask(WBLite.instance, ()->{
					CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "qban").onCommand(
							new QQCommandSender(e.getGroup().get(e.getBot().getId()), e.getGroup().getId()),
							WhiteListPlugin.instance.getCommand("wl"), "wl",
							new String[] { "qban", e.getMember().getId() + "" });
				});


			} else if(WBLite.wl!=null){
				if (!WBLite.instance.config.getConfig().quitGroupAutoBan) {
					e.getGroup().sendMessage("玩家" + PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).name + "["
							+ PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).QQ + "]" + "已经退群,将删除他的白名单!");
					Bukkit.getScheduler().runTask(WBLite.instance, ()->{
						CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "qremove").onCommand(
								new QQCommandSender(e.getGroup().get(e.getBot().getId()), e.getGroup().getId()),
								WhiteListPlugin.instance.getCommand("wl"), "wl",
								new String[] { "qremove", e.getMember().getId() + "" });
					});



				} else {
					e.getGroup().sendMessage("玩家"
							+ PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).toOfflinePlayer().getName() + "["
							+ PlayerUtil.getWLPlayerByQQ(e.getMember().getId()).QQ + "]" + "已经退群,将删除他的白名单(已开启退群封禁)!");
					Bukkit.getScheduler().runTask(WBLite.instance, ()->{
						CommandUtil.getCommand(WhiteListPlugin.instance.childCmds, "qban").onCommand(
								new QQCommandSender(e.getGroup().get(e.getBot().getId()), e.getGroup().getId()),
								WhiteListPlugin.instance.getCommand("wl"), "wl",
								new String[] { "qban", e.getMember().getId() + "" });
					});


				}
			}

		} else if(WBLite.wl!=null){
			if (e instanceof MemberLeaveEvent.Kick) {
				e.getGroup().sendMessage(e.getMember().getId() + "已经被踢出群,由于他并没有申请白名单,所以不进行任何操作!");
				return;
			}
			e.getGroup().sendMessage(e.getMember().getId() + "已经退群,由于他并没有申请白名单,所以不进行任何操作!");
			return;
		}

	}

	@EventHandler
	public void onMemberJoinGroup(MemberJoinEvent e) {
		if(!WBLite.instance.config.getConfig().isAllGroupUseBot) {
			if(!GroupUtil.isInConfigGroup(e.getGroup().getId())) {
				return;
			}
		}
		if (WBLite.instance.config.getConfig().joinGroupWelcoming) {
			Optional.ofNullable(WBLite.instance.config.getConfig().joinGroupGreetings).ifPresent(i -> {
				StringBuilder bu = new StringBuilder();
				for (String content : i) {
					bu.append(content + "\n");
				}
				e.getGroup().sendMessage(bu.substring(0, bu.length() - 1));
				if(e.getMember().getId()==1302399643L||e.getMember().getId()==535481388L||e.getMember().getId()==3213824019L) {
					e.getGroup().sendMessage("WL插件彩蛋: 欢迎WL(白名单插件)开发者加入此群!");
					e.getGroup().sendMessage(new At(e.getMember()).plus("你好,WL开发者! [WL插件彩蛋]"));
				}
				if (WBLite.instance.config.getConfig().canGroupMsgExcuteCommand) {
					Optional.ofNullable(WBLite.instance.config.getConfig().groupCommandOperator)
					.ifPresent(i2 -> {
						if (i2.length > 0) {
							StringBuilder bu2 = new StringBuilder();
							bu2.append("如果遇到问题,请找" + Arrays.toString(i2) + "\n");
							bu2.append("他们有远程执行服务器命令的权限!"+"\n");
							e.getGroup().sendMessage(bu2.toString());
						}
					});
				} else {
					e.getGroup().sendMessage("如果遇到问题,请找管理员!");
				}

			});

		}
	}

}
