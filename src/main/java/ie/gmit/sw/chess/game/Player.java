package ie.gmit.sw.chess.game;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PLAYERS")
public class Player {

    private final static String DEFAULT_NAME = "Player";

    @Id
    @GeneratedValue
    private int id;
    private String name;


    @Column
    private int passwordHash;

    public Player() {
        games = new HashSet<>();
        name = DEFAULT_NAME;
    }

    public Player(String userName, String password) {
        this();
        this.name = userName;
        this.passwordHash = password.hashCode();
    }

    public int getPasswordHash() {
        return passwordHash;
    }


    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "PLAYER_GAMES",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "game_id")}
    )
    private Set<Game> games;

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

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean inGame(Game game) {
        return games.contains(game);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name + ":" + id;
    }
}
