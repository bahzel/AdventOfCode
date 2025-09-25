package solution.y2021;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import utils.soution.Solution;

public class Day23_2 extends Solution {
	private final Set<String> CACHE = new HashSet<>();

	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected String doSolve() {
		Queue<AmphipodRoom> queue = new PriorityQueue<>();
		queue.add(new AmphipodRoom(new Amphipod[11], getInitialRooms()));
		while (!queue.isEmpty()) {
			var room = queue.poll();
			if (!CACHE.add(room.getCheckSum())) {
				continue;
			}
			if (room.isFinished()) {
				return room.getScore() + "";
			}
			queue.addAll(room.getNextSteps());
		}
		throw new IllegalStateException();
	}

	private Amphipod[][] getInitialRooms() {
		var rooms = new Amphipod[4][4];
		rooms[0][0] = Amphipod.C;
		rooms[0][1] = Amphipod.D;
		rooms[0][2] = Amphipod.D;
		rooms[0][3] = Amphipod.B;
		rooms[1][0] = Amphipod.C;
		rooms[1][1] = Amphipod.B;
		rooms[1][2] = Amphipod.C;
		rooms[1][3] = Amphipod.B;
		rooms[2][0] = Amphipod.A;
		rooms[2][1] = Amphipod.A;
		rooms[2][2] = Amphipod.B;
		rooms[2][3] = Amphipod.D;
		rooms[3][0] = Amphipod.A;
		rooms[3][1] = Amphipod.C;
		rooms[3][2] = Amphipod.A;
		rooms[3][3] = Amphipod.D;
		return rooms;
	}
}
