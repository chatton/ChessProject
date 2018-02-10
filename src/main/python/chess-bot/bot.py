from pieces import Colour
from game import Board
from util import pos_to_str, random_choice
import random
import requests

class ChessBot:
    def __init__(self, name="PyBot", host="localhost", port=8080):
        self.config = {
            "name" : name,
            "host" : host,
            "port" : port
        }
        self.game_state = {}
        self.player_id = None
        self.game_id = None
    

    def request_game(self):
        """connect to a game and set the player and game ids as well as what colour the bot
        will be."""
        query = "http://" + self.config["host"] + ":" + str(self.config["port"]) + "/chess/v1/newgame/"
        resp = requests.get(query)

        # TODO handle errors.
        
        result = resp.json()
        self.player_id = result["playerId"]
        self.game_id = result["gameId"]
        self.colour = result["colour"]

    def update_game_state(self):
        """this method makes a get request to the server and updates
        the current board dict with the most up-to-date positions of 
        the pieces."""
        query = "http://" + self.config["host"] + ":" + str(self.config["port"]) + "/chess/v1/gamestate?gameId=" + str(self.game_id)
        resp = requests.get(query)
        
        # TODO handle errors in response.

        self.game_state = resp.json()
        
        self.board = Board(self.game_state["positions"])


    def make_move(self):
        move = self._get_next_move()
        print("Making move: " + str(move))
        query = "http://" + self.config["host"] + ":" + str(self.config["port"]) + "/chess/v1/makemove"
        # sends the move as a post request to the server.

        rep = requests.post(query, json={
            "from" : move.origin,
            "to" : move.dest,
            "gameId" : self.game_id,
            "playerId" : self.player_id
        })
    
    @property
    def is_turn(self):
        """determines if it's currently the bot's turn or not based on the current server status."""
        return False if "currentTurn" not in self.game_state else self.game_state["currentTurn"] == self.colour

    def _get_next_move(self):
        moves_and_values = []
        for move in self.moves:
            moves_and_values.append((move, self._get_move_value(move)))

        moves_and_values.sort(key=lambda move_val : move_val[1], reverse=True)
   
        for move_val in moves_and_values:
            move = move_val[0]
            self.board.make_move(move)
            if not self.board.is_check(self.colour):
                self.board.undo_move()
                return move

            self.board.undo_move()
            

    def _get_move_value(self, move):
        piece = self.board.get_piece(move.dest)
        return piece.value if piece else 0
        

    @property
    def pieces(self):
        return [
            piece for piece in self.board.pieces 
            if piece and piece.colour == self.colour
        ]

    @property
    def opponent_pieces(self):
        return [
            piece for piece in self.board.pieces 
            if piece and piece.colour != self.colour
        ]

    @property
    def moves(self):
        moves = []
        for piece in self.pieces:            
            moves.extend(piece.moves)
        return moves

    @property
    def opponent_moves(self):
        moves = []
        for piece in self.opponent_pieces:
            moves.extend(piece.moves)
        return moves