package simpoppit.greedy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import simpoppit.board.GameGrid;
import simpoppit.board.PoppitBoard;
import simpoppit.board.Space;
import simpoppit.board.SpaceSet;

public class GreedyPlayer {

	public static Logger _logger = Logger.getLogger(GreedyPlayer.class);

	public static void main(String[] args) {
		GameGrid grid = new GameGrid(8, 8);
		PoppitBoard board = new PoppitBoard(grid);
		GreedyPlayer player = new GreedyPlayer();
		player.play(board);
	}

	public void play(PoppitBoard board) {
		while (true) {
			_logger.debug("Picking move for board\n" + board);
			SpaceSet move = pickMove(board);
			if (move != null) {
				_logger.debug("Picked move " + move);
				board.pop(move);
			} else {
				_logger.debug("No moves available");
				break;
			}
		}
		_logger.debug("DONE");
		LogManager.shutdown();
	}

	private SpaceSet pickMove(PoppitBoard board) {
		SpaceSet considered = new SpaceSet();
		SpaceSet bestMove = null;
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Space sp = board.getSpace(x, y);
				if (considered.contains(sp)) {
					continue;
				}
				SpaceSet move = board.getPopSet(sp);
				considered.add(move);
				if (move.size() < 2) {
					continue;
				}
				if (bestMove == null || bestMove.size() < move.size()) {
					bestMove = move;
				}
			}
		}
		return bestMove;
	}

}
