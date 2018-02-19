package ie.gmit.sw.repositories;

import ie.gmit.sw.chess.game.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Integer> {}
