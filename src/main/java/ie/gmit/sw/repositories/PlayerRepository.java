package ie.gmit.sw.repositories;

import ie.gmit.sw.chess.game.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {}
