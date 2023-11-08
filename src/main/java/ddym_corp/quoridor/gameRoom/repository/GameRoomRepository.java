package ddym_corp.quoridor.gameRoom.repository;

import ddym_corp.quoridor.gameRoom.service.GameRoom;

import java.util.List;

public interface GameRoomRepository {
    List<GameRoom> findAll();
    GameRoom save();
    GameRoom findByGameId(Long gameId);

    boolean remove(Long gameId);
}
