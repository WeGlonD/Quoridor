package ddym_corp.quoridor.game.service;

import ddym_corp.quoridor.game.web.MoveMessage;
import org.springframework.web.socket.WebSocketSession;

public interface RoomManager {
    Room createRoom();

    void sendMessageToRoom(Long gameId, WebSocketSession session, MoveMessage message);
}
