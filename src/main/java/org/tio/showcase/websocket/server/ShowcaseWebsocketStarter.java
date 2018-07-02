package org.tio.showcase.websocket.server;

import java.io.IOException;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author tanyaowu
 * 2017年6月28日 下午5:34:04
 */
public class ShowcaseWebsocketStarter {

	private WsServerStarter wsServerStarter;
	private ServerGroupContext serverGroupContext;

	/**
	 *
	 * @author tanyaowu
	 */
	public ShowcaseWebsocketStarter(int port, ShowcaseWsMsgHandler wsMsgHandler) throws Exception {
		wsServerStarter = new WsServerStarter(port, wsMsgHandler);

		serverGroupContext = wsServerStarter.getServerGroupContext();
		serverGroupContext.setName(ShowcaseServerConfig.PROTOCOL_NAME);
		serverGroupContext.setServerAioListener(ShowcaseServerAioListener.me);

		//设置ip统计时间段
		serverGroupContext.ipStats.addDurations(ShowcaseServerConfig.IpStatDuration.IPSTAT_DURATIONS);
		//设置ip监控
		serverGroupContext.setIpStatListener(ShowcaseIpStatListener.me);
		//设置心跳超时时间
		serverGroupContext.setHeartbeatTimeout(ShowcaseServerConfig.HEARTBEAT_TIMEOUT);
		//如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
//		String keyStoreFile = "classpath:config/ssl/keystore.jks";
//		String trustStoreFile = "classpath:config/ssl/keystore.jks";
//		String keyStorePwd = "214323428310224";
//		serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
		
	}

	/**
	 * @param args
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void start() throws Exception {
		ShowcaseWebsocketStarter appStarter = new ShowcaseWebsocketStarter(ShowcaseServerConfig.SERVER_PORT, ShowcaseWsMsgHandler.me);
		appStarter.wsServerStarter.start();
	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext getServerGroupContext() {
		return serverGroupContext;
	}

	public WsServerStarter getWsServerStarter() {
		return wsServerStarter;
	}
	
	public static void main(String[] args) throws Exception {
		start();
	}

}
