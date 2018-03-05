import requests
import sys

from ai import MediumAI
from game import Board


class ChessBot:
    def __init__(self, name="PyBot", host="localhost", port=8080, password=""):
        self.config = {
            "name": name,
            "host": host,
            "port": port,
            "password" : password
        }

        self.board = None
        self.ai = None
        self.colour = None
        self.game_state = {}
        self.player_id = None
        self.game_id = None



    def login(self):
        query = "http://{0}:{1}/chess/v1/login/".format(self.host, self.port)
        resp = requests.post(query, json={
            "userName" : self.name,
            "password" : self.password
        })
        
        data = resp.json()
        if data["status"] == "BAD":
            print("Error logging in.")
            sys.exit()
        
        print("Successfully logged in as [{}]".format(self.name))

        self.player_id = data["id"]
        print(f"Received id of [{self.player_id}]")
        

    def register(self):
        query = "http://{0}:{1}/chess/v1/register/".format(self.host, self.port)
        resp = requests.post(query, json={
            "userName" : self.config["name"],
            "password" : self.config["password"]
        })
        data = resp.json()
        if data["status"] == "BAD":
            print(f"Error registering as {self.config['name']}")
            sys.exit()

        print(f"Successfully registered as [{self.config['name']}]")

        self.player_id = data["id"]
        print(f"Received id of [{self.player_id}]")

        

    def request_game(self):
        """connect to a game and set the player and game ids as well as what colour the bot
        will be."""
        query = "http://{0}:{1}/chess/v1/newgame/".format(self.host, self.port)
        resp = requests.get(query, params={"playerId" : self.player_id})

        # TODO handle errors.

        result = resp.json()
        print(result)
        self.player_id = result["playerId"]
        self.game_id = result["gameId"]
        self.colour = result["colour"]

    def update_game_state(self):
        """this method makes a get request to the server and updates
        the current board dict with the most up-to-date positions of 
        the pieces."""
        query = "http://{0}:{1}/chess/v1/gamestate".format(self.host, self.port)
        resp = requests.get(query, params={"gameId": self.game_id, "playerId" : self.player_id})

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

        query = "http://{0}:{1}/chess/v1/makemove".format(self.host, self.port)
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


    @property
    def host(self):
        return self.config["host"]

    @property
    def port(self):
        return self.config["port"]

    @property
    def name(self):
        return self.config["name"]

    @property
    def password(self):
        return self.config["password"]

    @property
    def game_ids(self):
        """returns the game ids of all games the bot is currently in.
        Sample response.
        [{
            'whitePlayerName': 'PyBot',
            'blackPlayerName': None,
            'gameId': 725273979,
            'currentTurn': 'WHITE'
        }]
        """
        
       
        
        query = "http://{0}:{1}/chess/v1/allgames?playerId={2}".format(self.host, self.port, self.player_id)
        resp = requests.get(query)
        results = resp.json()
        return [game["gameId"] for game in results]


