package org.tio.showcase.websocket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.Objects;

/**
 * @author tanyaowu
 * 2017年6月28日 下午5:32:38
 */
public class ShowcaseWsMsgHandler implements IWsMsgHandler {
    private static Logger log = LoggerFactory.getLogger(ShowcaseWsMsgHandler.class);

    public static final ShowcaseWsMsgHandler me = new ShowcaseWsMsgHandler();

    private ShowcaseWsMsgHandler() {

    }

    /**
     * 握手时走这个方法，业务可以在这里获取cookie，request参数等
     */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String clientip = request.getClientIp();
        log.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
        return httpResponse;
    }

    /**
     * @param httpRequest
     * @param httpResponse
     * @param channelContext
     * @throws Exception
     * @author tanyaowu
     */
    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String groupId = httpRequest.getParam("gid");
        //Long clientTime = httpRequest.getParam("now") != null ? Long.valueOf(httpRequest.getParam("now")) : 0;
        if (groupId == null) {
            groupId = Const.GROUP_ID;
        }
        //绑定到群组，后面会有群发
        Tio.bindGroup(channelContext, groupId);
        int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

        String msg = channelContext.getClientNode().toString() + " 进来了，现在共有【" + count + "】人在线";
        //msg += "时差：" + (System.currentTimeMillis() - clientTime);
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, groupId, wsResponse);
    }

    /**
     * 字节消息（binaryType = arraybuffer）过来后会走这个方法
     */
    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        return null;
    }

    /**
     * 当客户端发close flag时，会走这个方法
     */
    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        Tio.remove(channelContext, "receive close flag");
        System.out.println("client close");
        //System.exit(0);
        return null;
    }

    /*
     * 字符消息（binaryType = blob）过来后会走这个方法
     */
    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
        WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
        HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包
        if (log.isDebugEnabled()) {
            log.debug("握手包:{}", httpRequest);
        }

        log.info("收到ws消息:{}", text);

        if (Objects.equals("心跳内容", text)) {
            return null;
        }
        String groupId = httpRequest.getParam("gid");
        if (groupId == null) {
            groupId = Const.GROUP_ID;
        }
        Long now = System.currentTimeMillis();
        String msg = channelContext.getClientNode().toString() + " 说：" + text + "###" + now;
        //用tio-websocket，服务器发送到客户端的Packet都是WsResponse
        WsResponse wsResponse = WsResponse.fromText(msg, ShowcaseServerConfig.CHARSET);
        //群发
        Tio.sendToGroup(channelContext.groupContext, groupId, wsResponse);

        //返回值是要发送给客户端的内容，一般都是返回null
        return null;
    }

}
