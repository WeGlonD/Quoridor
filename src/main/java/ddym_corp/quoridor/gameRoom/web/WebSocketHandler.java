package ddym_corp.quoridor.gameRoom.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddym_corp.quoridor.user.auth.web.uitls.SessionConst;
import ddym_corp.quoridor.gameRoom.service.GameRoomManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final GameRoomManager roomManager;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("WebSocketHandler - afterConnectionClosed");
        log.info("Disconnected session - gameId: {}, uid: {}", getGameId(session), (Long) session.getAttributes().get(SessionConst.USER_ID));
        roomManager.sendMessageToRoom(getGameId(session), session, new MoveMessage(4, 0L, 0, 0));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        //moveMessage parsing
        String payload = message.getPayload();
        log.info("payload: {}", payload);
        log.info("getAttributes {}", session.getAttributes().toString());
        log.info("get gameId {}", getGameId(session));

        //parsing
        MoveMessage moveMessage = null;
        try {
            moveMessage = objectMapper.readValue(payload, MoveMessage.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //send message to client's room
        roomManager.sendMessageToRoom(getGameId(session), session, moveMessage);
    }

    private Long getGameId(WebSocketSession session){
        return (Long) session.getAttributes().get(SessionConst.GAME_ID);
    }
}
