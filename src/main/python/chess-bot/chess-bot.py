from pieces import *
from game import *

def main():
    board = Board()
    
    board.set_piece( Bishop(board,Colour.WHITE), (3,3))
    k = board.get_piece((3,3))
    print(k.moves)

if __name__ == "__main__":
    main()
