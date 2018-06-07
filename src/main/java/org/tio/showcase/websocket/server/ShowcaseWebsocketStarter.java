package org.tio.showcase.websocket.server;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.tio.core.ssl.SslConfig;
import org.tio.server.ServerGroupContext;
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
		//如果你希望通过wss来访问，就加上下面这一行吧，不过首先你得有证书哦
		//initSsl(serverGroupContext);
	}
	
	private static void initSsl(ServerGroupContext serverGroupContext) throws Exception {
		String keyStoreFile = "classpath:config/ssl/keystore.jks";
		String trustStoreFile = "classpath:config/ssl/keystore.jks";
		String keyStorePwd = "214323428310224";

		if (StringUtils.isNotBlank(keyStoreFile) && StringUtils.isNotBlank(trustStoreFile)) {
			SslConfig sslConfig = SslConfig.forServer(keyStoreFile, trustStoreFile, keyStorePwd);
			serverGroupContext.setSslConfig(sslConfig);
		}
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
