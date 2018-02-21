package ie.gmit.sw.chess.game;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLAYERS")
public class Player {

    private final static String DEFAULT_NAME = "Player";

    @Id
    @GeneratedValue
    private int id;
    private String name;

    public Player() {
        games = new ArrayList<>();
        name = DEFAULT_NAME;
    }

    @ManyToMany(cascade = {
            CascadeType.ALL,
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "PLAYER_GAMES",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    private List<Game> games;

    public String getName() {
        if (name.equals(DEFAULT_NAME)) {
            name += "_" + id;
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public boolean inGame(Game game) {
        return games.contains(game);
    }
}
