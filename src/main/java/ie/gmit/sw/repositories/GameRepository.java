package ie.gmit.sw.repositories;

import ie.gmit.sw.chess.game.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    default List<Game> findPrivateGames() {
        Iterable<Game> games = findAll();
        List<Game> filtered = new ArrayList<>();
        for (Game g : games) {
            if (g.isPrivate()) {
                filtered.add(g);
            }
        }
        return filtered;
    }

}