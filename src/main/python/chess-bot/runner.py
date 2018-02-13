from bot import ChessBot
import time

def main():
    
    bot = ChessBot()
    bot.request_game()

    while True:
        time.sleep(5) # wait 5 seconds between GET requests.
        bot.update_game_state() # GET updated board
       
        # keep updating the game state until it's the bot's turn.
    
        if bot.is_turn():
            # it's the bot's turn so it needs to make a move.
            bot.make_move() # sends it's move to the server via a POST request.

if __name__ == "__main__":
    main()
