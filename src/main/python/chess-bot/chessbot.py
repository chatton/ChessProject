from bot import ChessBot
import argparse
import time

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("-n", "--name", help="The user name of the user you want to connect as.", action="store", default="PyBot")
    parser.add_argument("-hn", "--host", help="The hostname you want to connect to", action="store", default="localhost")
    parser.add_argument("-pt", "--port", help="The port", action="store", type=int, default="8080")
    parser.add_argument("-p", "--password", help="the password", action="store", default="PyBotPass")
    parser.add_argument("-nng", "--num_new_games", help="the number of new games pybot should start", action="store", type=int, default=0)

    # you must either register or login
    log_or_reg = parser.add_mutually_exclusive_group(required=True)
    log_or_reg.add_argument("-r", "--register", help="If the bot needs to register a new account.", action="store_true", default=False)
    log_or_reg.add_argument("-l", "--login", help="If the bot needs to login to the system with an existing account", action="store_true", default=False)
    
    args = parser.parse_args()

    bot = ChessBot(
        name=args.name, 
        host=args.host, 
        port=args.port,
        password=args.password
    )

    if args.register:
        bot.register()

    if args.login:
        bot.login()

    for _ in range(args.num_new_games):
        bot.request_game()

    all_game_ids = bot.game_ids

    print("[{0}] is currently in [{1}] games.".format(args.name, len(all_game_ids)))

    while True:
        time.sleep(5) # wait 5 seconds between GET requests.
        for game_id in all_game_ids:
            bot.game_id = game_id
            bot.update_game_state() # GET updated board
            if bot.is_turn():
                # it's the bot's turn so it needs to make a move.
                bot.make_move() # sends it's move to the server via a POST request.

if __name__ == "__main__":
    main()
