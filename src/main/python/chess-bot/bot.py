import requests

from ai import MediumAI
from game import Board


class ChessBot:
    def __init__(self, name="PyBot", host="localhost", port=8080):
        self.config = {
            "name": name,
            "host": host,
            "port": port
        }

        self.board = None
        self.ai = None
        self.colour = None
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
        resp = requests.get(query, params={"gameId": self.game_id})

        # TODO handle errors in response.

        self.game_state = resp.json()

        self.board = Board(self.game_state["positions"])
        self.ai = MediumAI(self.board, self.colour)

    def make_move(self):
        move = self.ai.get_next_move()
        if not move:
            # checkmate
            print("Check mate!!!")
            return

        query = "http://{0}:{1}/chess/v1/makemove".format(self.config["host"], self.config["port"])
        # sends the move as a post request to the server.

        requests.post(query, json={
            "from": move.origin,
            "to": move.dest,
            "gameId": self.game_id,
            "playerId": self.player_id
        })

    def is_turn(self):
        """determines if it's currently the bot's turn or not based on the current server status."""
        return False if "currentTurn" not in self.game_state else self.game_state["currentTurn"] == self.colour
