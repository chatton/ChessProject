from bot import ChessBot
import time

def main():
    
    bot = ChessBot()
    bot.request_game()

    while True:
        # keep updating the game state until it's the bot's turn.
        while not bot.is_turn:
            time.sleep(3) # wait 3 seconds between GET requests.
            bot.update_game_state() # GET updated board

        # it's the bot's turn so it needs to make a move.
        print(bot.make_move()) # sends it's move to the server via a POST request.

if __name__ == "__main__":
    main()
