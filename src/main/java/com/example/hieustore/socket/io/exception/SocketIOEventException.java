package com.example.hieustore.socket.io.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.example.hieustore.constant.CommonConstant;
import com.example.hieustore.constant.ErrorMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SocketIOEventException implements ExceptionListener {

    @Override
    public void onEventException(Exception e, List<Object> list, SocketIOClient client) {
        log.error(e.getMessage(), e);
        if (e.getCause().getClass().equals(EventException.class)) {
            sendErrorMessageToClient(client, e.getCause().getMessage());
        } else {
            sendErrorMessageToClient(client, ErrorMessage.ERR_EXCEPTION_GENERAL);
        }

    }

    private void sendErrorMessageToClient(SocketIOClient client, Object errorMessage) {
        client.sendEvent(CommonConstant.Event.SERVER_SEND_ERROR, errorMessage);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void onPongException(Exception e, SocketIOClient socketIOClient) {
        log.error(e.getMessage(), e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error(e.getMessage(), e);
        return true;
    }
}
