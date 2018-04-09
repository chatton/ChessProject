from bot import ChessBot
import argparse
import time
import sys

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("-n", "--name", help="The user name of the user you want to connect as.", action="store", default="PyBot")
    parser.add_argument("-p", "--password", help="the password", action="store", default="PyBotPass")
    parser.add_argument("-hn", "--host", help="The hostname you want to connect to", action="store", default="localhost")
    parser.add_argument("-pt", "--port", help="The port", action="store", type=int, default="8080")
    parser.add_argument("-g", "--game_id", help="Game to join", action="store", type=int)
    args = parser.parse_args()

    bot = ChessBot(
        name=args.name, 
        host=args.host, 
        port=args.port,
        password=args.password
    )

    registerd_sucessfully = bot.register() # attempt register
    if not registerd_sucessfully:
        logged_in = bot.login()
        if not logged_in:
            sys.exit()
    
    bot.join_game(args.game_id)

    while True:
        bot.update_game_state(args.game_id)
        if bot.is_turn():
            bot.make_move()
        time.sleep(5)

if __name__ == "__main__":
    main()
