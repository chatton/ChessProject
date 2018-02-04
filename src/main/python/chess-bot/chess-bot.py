from pieces import *
from game import *

# import urllib.request

import requests

def main():
    board = Board()
    
    board.set_piece( Bishop(board,Colour.WHITE), (3,3))
    k = board.get_piece((3,3))
    print(k.moves)
    url = "http://localhost:8080/test"
    # f = urllib.request.urlopen(url)
    # print(f.read().decode("utf-8"))
    r = requests.get(url)
    print(r.json())

if __name__ == "__main__":
    main()
