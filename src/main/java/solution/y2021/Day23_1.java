package solution.y2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.soution.Solution;

public class Day23_1 extends Solution {
	private final Set<String> CACHE = new HashSet<>();

	public static void main(String[] args) {
		new Day23_1().solve();
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
		var rooms = new Amphipod[4][2];
		rooms[0][0] = Amphipod.C;
		rooms[0][1] = Amphipod.B;
		rooms[1][0] = Amphipod.C;
		rooms[1][1] = Amphipod.B;
		rooms[2][0] = Amphipod.A;
		rooms[2][1] = Amphipod.D;
		rooms[3][0] = Amphipod.A;
		rooms[3][1] = Amphipod.D;
		return rooms;
	}
}

@Getter
class AmphipodRoom implements Comparable<AmphipodRoom> {
	private final Amphipod[] buffer;
	private final Amphipod[][] rooms;
	private final long score;
	private String checkSum = null;

	public AmphipodRoom(Amphipod[] buffer, Amphipod[][] rooms) {
		this.buffer = buffer;
		this.rooms = rooms;
		score = 0L;
	}

	public AmphipodRoom(AmphipodRoom other, int addScore) {
		buffer = Arrays.copyOf(other.buffer, other.buffer.length);
		rooms = new Amphipod[][] { Arrays.copyOf(other.rooms[0], other.rooms[0].length),
				Arrays.copyOf(other.rooms[1], other.rooms[1].length),
				Arrays.copyOf(other.rooms[2], other.rooms[2].length),
				Arrays.copyOf(other.rooms[3], other.rooms[3].length) };
		score = other.score + addScore;
	}

	@Override
	public int compareTo(AmphipodRoom o) {
		return (int) (score - o.score);
	}

	public boolean isFinished() {
		for (var i = 0; i < rooms.length; i++) {
			for (var j = 0; j < rooms[i].length; j++) {
				if (rooms[i][j] == null || rooms[i][j].getRoomIndex() != i) {
					return false;
				}
			}
		}
		return true;
	}

	public List<AmphipodRoom> getNextSteps() {
		var nextSteps = new ArrayList<AmphipodRoom>();
		for (var i = 0; i < buffer.length; i++) {
			if (canMoveToHome(i)) {
				var freeIndex = getFreeIndex(buffer[i]);
				var nextStep = new AmphipodRoom(this,
						(Math.abs(buffer[i].getBufferIndex() - i) - freeIndex + rooms[0].length) * buffer[i].getCost());
				nextStep.getRooms()[buffer[i].getRoomIndex()][freeIndex] = buffer[i];
				nextStep.getBuffer()[i] = null;
				nextSteps.add(nextStep);
			}
		}

		if (!nextSteps.isEmpty()) {
			return nextSteps;
		}

		for (var i = 0; i < rooms.length; i++) {
			var indexToMove = getIndexToMove(i);
			if (indexToMove == -1) {
				continue;
			}

			var roomIndex = 2 + 2 * i;
			for (var j = roomIndex - 1; j >= 0; j--) {
				if (j == 2 || j == 4 || j == 6) {
					continue;
				}
				if (buffer[j] != null) {
					break;
				}
				var nextStep = new AmphipodRoom(this,
						(roomIndex - j - indexToMove + rooms[i].length) * rooms[i][indexToMove].getCost());
				nextStep.getBuffer()[j] = rooms[i][indexToMove];
				nextStep.getRooms()[i][indexToMove] = null;
				nextSteps.add(nextStep);
			}
			for (var j = roomIndex + 1; j < buffer.length; j++) {
				if (j == 4 || j == 6 || j == 8) {
					continue;
				}
				if (buffer[j] != null) {
					break;
				}
				var nextStep = new AmphipodRoom(this,
						(j - roomIndex - indexToMove + rooms[i].length) * rooms[i][indexToMove].getCost());
				nextStep.getBuffer()[j] = rooms[i][indexToMove];
				nextStep.getRooms()[i][indexToMove] = null;
				nextSteps.add(nextStep);
			}
		}

		return nextSteps;
	}

	private boolean canMoveToHome(int index) {
		if (buffer[index] == null) {
			return false;
		}
		if (getFreeIndex(buffer[index]) == -1) {
			return false;
		}

		var direction = index < buffer[index].getBufferIndex() ? 1 : -1;
		for (var bufferIndex = index
				+ direction; bufferIndex != buffer[index].getBufferIndex(); bufferIndex += direction) {
			if (buffer[bufferIndex] != null) {
				return false;
			}
		}
		return true;
	}

	private int getIndexToMove(int roomIndex) {
		var isInvalid = false;
		for (var i = 0; i < rooms[roomIndex].length; i++) {
			if (rooms[roomIndex][i] == null) {
				return isInvalid ? i - 1 : -1;
			} else if (rooms[roomIndex][i].getRoomIndex() != roomIndex) {
				isInvalid = true;
			}
		}
		return isInvalid ? rooms[roomIndex].length - 1 : -1;
	}

	private int getFreeIndex(Amphipod amphipod) {
		for (var i = 0; i < rooms[0].length; i++) {
			if (rooms[amphipod.getRoomIndex()][i] == null) {
				return i;
			} else if (rooms[amphipod.getRoomIndex()][i] != amphipod) {
				return -1;
			}
		}
		return -1;
	}

	public String getCheckSum() {
		if (checkSum == null) {
			checkSum = Arrays.toString(buffer) + Arrays.deepToString(rooms);
		}
		return checkSum;
	}
}

@AllArgsConstructor
@Getter
enum Amphipod {
	A(1, 0, 2), B(10, 1, 4), C(100, 2, 6), D(1000, 3, 8);

	private final int cost;
	private final int roomIndex;
	private final int bufferIndex;
}
