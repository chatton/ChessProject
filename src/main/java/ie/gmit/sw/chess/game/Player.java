package ie.gmit.sw.chess.game;

import ie.gmit.sw.chess.board.pieces.Colour;
/**
 * Represents a player of the game*/
public class Player {
    private int id;
    private Colour colour;


    public Player(int id) {
        this.id = id;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour){
        this.colour = colour;
    }

    public int getId() {
        return id;
    }
}
