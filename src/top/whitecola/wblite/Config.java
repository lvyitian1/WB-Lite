package top.whitecola.wblite;

import java.util.Vector;

public class Config {
	public long qq = 0;
	public String password = "";
	public String tip = "请上方输入密码.";
	public boolean memberAddWhiteListByOneself = true;
	public boolean robotShowDebugMsg = false;
	public boolean robotShowLogMsgDebug = false;
	public boolean canGroupMsgExcuteCommand = true;
	public long[] groupCommandOperator = {3213824019L,535481388L,123456789L};
	public String tip2 = "上方填入可远程机器人执行命令者的QQ号.";
	public long[] limitedOperators = {1234567,7654321};
	public String tip3 = "上方填入要限制权限的可远程机器人执行命令者的QQ号. 限制权限后,被限制者只能使用wl的命令.";
	public String serverName = "WL实验室";
	public String serverIp = "mc.whitecola.com[测试用,非真实地址.]";
	public int serverPort = 25565;
	public boolean quitGroupAutoBan = false;
	public boolean joinGroupWelcoming = true;
	public String[] joinGroupGreetings= {"欢迎来到这个服务器","请遵守服务器规则","申请白名单可以用 .申请白名单 <id> 或 。申请白名单 <id>","希望您能在本服玩得愉快!"};
	public boolean isAllGroupUseBot = true;
	public Vector<Long> useBotGroup = new Vector<>();
	{
		useBotGroup.add(703246558L);
		useBotGroup.add(123456L);
	}
	public boolean playerJoinServerGroupMsg = true;
	public boolean playerLeaveServerGroupMsg = true;
	public boolean allowAddingWhitelistBeforeChangingNameInGroup = false;

	public boolean synchronousMsgFunction = false;
	public boolean creeperEventMsg = true;
	public boolean TNTEventMsg = false;
	public boolean serverMsgSendToGroup = true;
	public boolean groupMsgSendToGame = true;
	public boolean isMsgSynchronousStartWithString = false;
	public String msgSynchronousStartWithStringContent = "#";
	public boolean serverEventSentToGroup = true;
	public String tip4 = "以上(msgSynchronousFunction以下)配置的前提是 msgSynchronousFunction 为true";
	public boolean sendDieMsgToServerWhenPlayerDie = true;
	public boolean removeColorText = false;
        public boolean forbidColorInGroupMsg = false;
        public boolean forbidNewLineInGroupMsg = true;
        
	//public boolean noWhitelistFunction = false;
}
