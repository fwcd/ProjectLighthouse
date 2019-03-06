package lighthouse.puzzle.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lighthouse.puzzle.model.Board;
import lighthouse.puzzle.model.Level;

public class RecursiveSolver implements Solver {
	private final int maxDepth = 4;
	
	@Override
	public List<Board> solve(Level level) {
		List<Board> boards = new ArrayList<>();
		Set<Board> visited = new HashSet<>();
		Board board = level.getStart();
		Board goal = level.getGoal();
		
		boards.add(board);
		
		while (!board.equals(goal)) {
			Board next = pickNext(board, level, visited).orElse(null);
			
			if (next == null) {
				// TODO: Got stuck in a dead end, find a solution somehow
				return Collections.emptyList();
			}
			
			board = next;
			visited.add(board);
			boards.add(board);
			System.out.println(board);
		}
		
		return boards;
	}
	
	private Optional<Board> pickNext(Board board, Level level, Set<Board> visited) {
		return board.streamPossibleMoves()
			.map(board::childBoard)
			.filter(it -> !visited.contains(it))
			.min(Comparator.comparingDouble(next -> rate(next, level, maxDepth, visited)));
	}
	
	private double rate(Board board, Level level, int depth, Set<Board> visited) {
		if (board.equals(level.getGoal())) {
			return 100000 - depth;
		} else if (depth == 0) {
			return board.streamPossibleMoves()
				.map(board::childBoard)
				.filter(it -> !visited.contains(it))
				.mapToDouble(level::avgDistanceToGoal)
				.min()
				.orElse(Double.MIN_VALUE);
		} else {
			return board.streamPossibleMoves()
				.map(board::childBoard)
				.filter(it -> !visited.contains(it))
				.mapToDouble(next -> rate(next, level, depth - 1, visited))
				.min()
				.orElse(Double.MIN_VALUE);
		}
	}
	
	private boolean solveRecursively(Deque<Board> boards, Level level, Set<Board> visited, Board current, Board goal) {
		if (current.equals(goal)) {
			boards.offerFirst(current);
			return true;
		} else {
			for (Board next : current.streamPossibleMoves()
					.map(current::childBoard)
					.sorted(Comparator.comparingDouble(level::avgDistanceToGoal))
					.collect(Collectors.toList())) {
				if (Thread.interrupted()) {
					return false;
				} else if (!visited.contains(next)) {
					if (solveRecursively(boards, level, visited, next, goal)) {
						boards.offerFirst(current);
						return true;
					}
				}
			}
			return false;
		}
	}
}
