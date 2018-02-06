package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.pieces.Colour;
/**
 * Represente a player of the game*/
public class Player {
    private int id;
    private Colour colour;


    public Player(int id, Colour colour) {
        this.id = id;
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public int getId() {
        return id;
    }
}
