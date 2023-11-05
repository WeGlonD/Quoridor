package ddym_corp.quoridor.gameRoom.service;

import ddym_corp.quoridor.gameRoom.repository.GameRoomRepository;
import ddym_corp.quoridor.gameRoom.web.MoveMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * 싱글톤으로 room 생성과 하나의 room 찾은 후 메세지전달을 매개하는 역할
 */
@Component
@RequiredArgsConstructor
public class GameRoomManagerV1 implements GameRoomManager {

    private final GameRoomRepository gameRepository;

    @Override
    public GameRoom createRoom(){
        return gameRepository.save();
    }

    @Override
    public void sendMessageToRoom(Long gameId, WebSocketSession session, MoveMessage message) {
        GameRoom room = gameRepository.findByGameId(gameId);

        if(room.handleTempActions(session, message)){
            // true면 게임 종료

        }
    }
}
