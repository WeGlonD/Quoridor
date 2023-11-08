package ddym_corp.quoridor.gameRoom.service;

import ddym_corp.quoridor.gameRoom.web.MoveMessage;
import org.springframework.web.socket.WebSocketSession;

public interface GameRoomManager {
    GameRoom createRoom();
    GameRoom findRoom(Long gameId);
    void sendMessageToRoom(Long gameId, WebSocketSession session, MoveMessage message);
}
