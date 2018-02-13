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
        query = "http://{0}:{1}/chess/v1/newgame/".format(self.config["host"], self.config["port"])
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
        query = "http://{0}:{1}/chess/v1/gamestate".format(self.config["host"], self.config["port"])
        resp = requests.get(query, params={"gameId" : self.game_id})
        
        # TODO handle errors in response.

        self.game_state = resp.json()
        
        self.board = Board(self.game_state["positions"])


    def make_move(self):
        move = self._get_next_move()
        if not move:
            # checkmate
            print("Check mate!!!")
            return

        query = "http://{0}:{1}/chess/v1/makemove".format(self.config["host"], self.config["port"])
        # sends the move as a post request to the server.

        requests.post(query, json={
            "from" : move.origin,
            "to" : move.dest,
            "gameId" : self.game_id,
            "playerId" : self.player_id
        })
    
    def is_turn(self):
        """determines if it's currently the bot's turn or not based on the current server status."""
        return False if "currentTurn" not in self.game_state else self.game_state["currentTurn"] == self.colour

    def _best_state(self, possible_states):
        best_option = None
        highest = -100000
        for state in possible_states:
            if state.value > highest:
                best_option = state
                highest = state.value

        return best_option

    def _other_colour(self):
        return "WHITE" if self.colour == "WHITE" else "BLACK"

    def _get_next_move(self):
        """looks at the current possible moves and also what the opponent
        will do next turn. Won't make bad trades (eg won't sacrifice queen
        for a pawn)"""
        all_possible_states = self.board.all_board_states(self.colour) # give back all possible board states from the current board.
        best_option = None
        highest = -10000
        for state in all_possible_states:
            opponent_states = state.all_board_states(self._other_colour()) # get all possible states from each possible state.
   
            best_state = self._best_state(opponent_states) # this assumes that the opponent will make the "best" move they can.
            state.value -= best_state.value # the value of this move depends on what the opponent will do.
            if state.value + 1 > highest and not state.is_check(self._other_colour()):
                best_option = state
                highest = state.value

        print("BEST OPTION: " + str(best_option.move))
        return best_option.move
        

    def _get_next_move_easy(self):
        """looks at just the current board state. Will make terrible trades
        if it sees that a piece is capturable."""
        moves_and_values = []
        for move in self.moves:
            moves_and_values.append((move, self._get_move_value(move)))

        # sort moves based on their values, so the highest value move is as the start of the list.
        moves_and_values.sort(key=lambda move_val : move_val[1], reverse=True)
   
        for move_val in moves_and_values:
            move, _ = move_val
            self.board.make_move(move)
            if not self.board.is_check(self.colour):
                self.board.undo_move()
                return move

            self.board.undo_move()

        # no valid moves - checkmate.
            

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