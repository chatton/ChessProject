class AI:
    def __init__(self, board, my_colour):
        self.board = board
        self.colour = my_colour

    @property
    def my_pieces(self):
        return [piece for piece in self.board.pieces if piece.colour == self.colour]

    @property
    def my_moves(self):
        return self.board.all_possible_moves(self.colour)

    @property
    def opp_moves(self):
        return self.board.all_possible_moves(
            "WHITE" if self.colour == "BLACK" else "BLACK"
        )

    @property
    def opp_pieces(self):
        return [piece for piece in self.board.pieces if piece.colour != self.colour]

    @property
    def opponent_colour(self):
        return {"BLACK": "WHITE", "WHITE": "BLACK"}[self.colour]

    def get_next_move(self):
        raise NotImplementedError


class HardAI(AI):
    def __init__(self, board, my_colour):
        super().__init__(board, my_colour)
        self.options = []

    def get_next_move(self):
        pass

    def _optimal_move(self):
        pass


class MediumAI(AI):
    def __init__(self, board, my_colour):
        super().__init__(board, my_colour)

    def get_next_move(self):
        best_option = None
        highest = -200000
        all_possible_board_states = self.board.all_board_states(self.colour)
        for state in all_possible_board_states:
            from_there = state.all_board_states(self.opponent_colour)
            best_state = self._best_state(from_there)
            state.value -= (
                best_state.value
            )  # the value of this move depends on what the opponent will do.
            if state.value + 1 > highest and not state.is_check(self.opponent_colour):
                best_option = state
                highest = state.value

        return best_option.move

    def _best_state(self, possible_states):
        best_option = None
        highest = -200000
        for state in possible_states:
            if state.value > highest:
                best_option = state
                highest = state.value

        return best_option


class EasyAI(AI):
    def __init__(self, board, my_colour):
        super().__init__(board, my_colour)

    def get_next_move(self):
        """looks at just the current board state. Will make terrible trades
        if it sees that a piece is capturable."""
        moves_and_values = []

        for move in self.my_moves:
            moves_and_values.append((move, self._get_move_value(move)))

        # sort moves based on their values, so the highest value move is as the start of the list.
        moves_and_values.sort(key=lambda move_val: move_val[1], reverse=True)

        for move_val in moves_and_values:
            move, _ = move_val
            self.board.make_move(move)
            if not self.board.is_check(self.colour):
                self.board.undo_move()
                return move

            self.board.undo_move()

    def _get_move_value(self, move):
        piece = self.board.get_piece(move.dest)
        return piece.value if piece else 0
