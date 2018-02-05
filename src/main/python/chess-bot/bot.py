from pieces import Colour
from game import Board
from util import pos_to_str
import random
import requests

class ChessBot:
    def __init__(self, name="PyBot", host="localhost", port=8080):
        self.board = {"positions": []}
        self.config = {
            "name" : name,
            "host" : host,
            "port" : port
        }
        self.game_state = {}
        self.player_id = None
        self.game_id = None
        self.colour = Colour.WHITE

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
        self._colour_enum = Colour.WHITE if self.colour == "WHITE" else Colour.BLACK

    def update_game_state(self):
        """this method makes a get request to the server and updates
        the current board dict with the most up-to-date positions of 
        the pieces."""
        query = "http://" + self.config["host"] + ":" + str(self.config["port"]) + "/chess/v1/gamestate/"
        resp = requests.get(query)
        
        # TODO handle errors in response.

        self.game_state = resp.json()
        
        self.board = Board(self.game_state["positions"])


    def make_move(self):
        move = self._get_next_move()
        return move
        # TODO send move to server.

    @property
    def is_turn(self):
        """determines if it's currently the bot's turn or not based on the current server status."""
        return False if "currentTurn" not in self.game_state else self.game_state["currentTurn"] == self.colour

    def _get_next_move(self):
        return random.choice(self.moves)

    @property
    def pieces(self):
        return [
            piece for piece in self.board.pieces 
            if piece and piece.colour == self._colour_enum
        ]

    @property
    def opponent_pieces(self):
        return [
            piece for piece in self.board.pieces 
            if piece and piece.colour != self._colour_enum
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