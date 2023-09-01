package ddym_corp.quoridor.game.repository;

import ddym_corp.quoridor.game.service.Room;

import java.util.List;

public interface GameRepository {
    List<Room> findAll();
    Room save();

    Room findByGameId(Long gameId);
}
