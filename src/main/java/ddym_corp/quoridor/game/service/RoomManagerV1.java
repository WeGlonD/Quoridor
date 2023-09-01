package ddym_corp.quoridor.game.service;

import ddym_corp.quoridor.game.repository.GameRepository;
import ddym_corp.quoridor.game.web.MoveMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 싱글톤으로 room 생성과 하나의 room 찾은 후 메세지전달을 매개하는 역할
 */
@Component
@RequiredArgsConstructor
public class RoomManagerV1 implements RoomManager {

    private final GameRepository gameRepository;

    @Override
    public Room createRoom(){
        return gameRepository.save();
    }

    @Override
    public void sendMessageToRoom(Long gameId, WebSocketSession session, MoveMessage message) {
        Room room = gameRepository.findByGameId(gameId);

        room.handleTempActions(session, message);
    }
}
