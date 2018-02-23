package ie.gmit.sw.repositories;

import ie.gmit.sw.chess.game.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    /**
     * @param name the name of the player you're looking for
     * @return the player going by that name.
     */
    default Player findByName(String name) {
        Iterable<Player> allPlayers = findAll();
        for (Player player : allPlayers) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }
}
