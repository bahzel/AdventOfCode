package solution.y2021;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import utils.soution.InstructionSolution;

public class Day22_2 extends InstructionSolution<Cube, List<Cube>> {
	public static void main(String[] args) {
		new Day22_2().solve();
	}

	@Override
	protected List<Cube> initializeValue() {
		return new ArrayList<>();
	}

	@Override
	protected Cube transformInstruction(String instruction) {
		var instructions = instruction.replace(",", "=").replace("..", "=").split("=");
		return new Cube(Integer.parseInt(instructions[1]), Integer.parseInt(instructions[2]),
				Integer.parseInt(instructions[4]), Integer.parseInt(instructions[5]), Integer.parseInt(instructions[7]),
				Integer.parseInt(instructions[8]), instruction.startsWith("on"));
	}

	@Override
	protected boolean performInstruction(Cube cube, List<Cube> cubes) {
		Queue<Cube> queue = new LinkedList<>();
		queue.add(cube);
		while (!queue.isEmpty()) {
			var cubeToAdd = queue.poll();
			if (cubeToAdd.isOn()) {
				var valid = true;
				for (var existingCube : cubes) {
					if (hasUpperCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(cubeToAdd.getMinX(), existingCube.getMaxX(), cubeToAdd.getMinY(),
								cubeToAdd.getMaxY(), cubeToAdd.getMinZ(), cubeToAdd.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMinX(existingCube.getMaxX() + 1);
					}
					if (hasLowerCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(existingCube.getMinX(), cubeToAdd.getMaxX(), cubeToAdd.getMinY(),
								cubeToAdd.getMaxY(), cubeToAdd.getMinZ(), cubeToAdd.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMaxX(existingCube.getMinX() - 1);
					}
					if (hasRightCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(cubeToAdd.getMinX(), cubeToAdd.getMaxX(), cubeToAdd.getMinY(),
								existingCube.getMaxY(), cubeToAdd.getMinZ(), cubeToAdd.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMinY(existingCube.getMaxY() + 1);
					}
					if (hasLeftCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(cubeToAdd.getMinX(), cubeToAdd.getMaxX(), existingCube.getMinY(),
								cubeToAdd.getMaxY(), cubeToAdd.getMinZ(), cubeToAdd.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMaxY(existingCube.getMinY() - 1);
					}
					if (hasBackCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(cubeToAdd.getMinX(), cubeToAdd.getMaxX(), cubeToAdd.getMinY(),
								cubeToAdd.getMaxY(), cubeToAdd.getMinZ(), existingCube.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMinZ(existingCube.getMaxZ() + 1);
					}
					if (hasFrontCollision(cubeToAdd, existingCube)) {
						queue.add(new Cube(cubeToAdd.getMinX(), cubeToAdd.getMaxX(), cubeToAdd.getMinY(),
								cubeToAdd.getMaxY(), existingCube.getMinZ(), cubeToAdd.getMaxZ(), cubeToAdd.isOn()));
						cubeToAdd.setMaxZ(existingCube.getMinZ() - 1);
					}
					if (cubeToAdd.getMinX() >= existingCube.getMinX() && cubeToAdd.getMaxX() <= existingCube.getMaxX()
							&& cubeToAdd.getMinY() >= existingCube.getMinY()
							&& cubeToAdd.getMaxY() <= existingCube.getMaxY()
							&& cubeToAdd.getMinZ() >= existingCube.getMinZ()
							&& cubeToAdd.getMaxZ() <= existingCube.getMaxZ()) {
						valid = false;
						break;
					}
				}
				if (valid) {
					cubes.add(cubeToAdd);
				}
			} else {
				for (var i = 0; i < cubes.size(); i++) {
					if (hasUpperCollision(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubes.get(i).getMinX(), cubeToAdd.getMaxX(), cubes.get(i).getMinY(),
								cubes.get(i).getMaxY(), cubes.get(i).getMinZ(), cubes.get(i).getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMinX(cubeToAdd.getMaxX() + 1);
						continue;
					}
					if (hasLowerCollision(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubeToAdd.getMinX(), cubes.get(i).getMaxX(), cubes.get(i).getMinY(),
								cubes.get(i).getMaxY(), cubes.get(i).getMinZ(), cubes.get(i).getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMaxX(cubeToAdd.getMinX() - 1);
						continue;
					}
					if (hasRightCollision(cubes.get(i), cubeToAdd) && isInsideX(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubes.get(i).getMinX(), cubes.get(i).getMaxX(), cubes.get(i).getMinY(),
								cubeToAdd.getMaxY(), cubes.get(i).getMinZ(), cubes.get(i).getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMinY(cubeToAdd.getMaxY() + 1);
						continue;
					}
					if (hasLeftCollision(cubes.get(i), cubeToAdd) && isInsideX(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubes.get(i).getMinX(), cubes.get(i).getMaxX(), cubeToAdd.getMinY(),
								cubes.get(i).getMaxY(), cubes.get(i).getMinZ(), cubes.get(i).getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMaxY(cubeToAdd.getMinY() - 1);
						continue;
					}
					if (hasBackCollision(cubes.get(i), cubeToAdd) && isInsideX(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubes.get(i).getMinX(), cubes.get(i).getMaxX(), cubes.get(i).getMinY(),
								cubes.get(i).getMaxY(), cubes.get(i).getMinZ(), cubeToAdd.getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMinZ(cubeToAdd.getMaxZ() + 1);
						continue;
					}
					if (hasFrontCollision(cubes.get(i), cubeToAdd) && isInsideX(cubes.get(i), cubeToAdd)) {
						cubes.add(new Cube(cubes.get(i).getMinX(), cubes.get(i).getMaxX(), cubes.get(i).getMinY(),
								cubes.get(i).getMaxY(), cubeToAdd.getMinZ(), cubes.get(i).getMaxZ(),
								cubes.get(i).isOn()));
						cubes.get(i).setMaxZ(cubeToAdd.getMinZ() - 1);
						continue;
					}
					if (cubes.get(i).getMinX() >= cubeToAdd.getMinX() && cubes.get(i).getMinX() <= cubeToAdd.getMaxX()
							&& cubes.get(i).getMinY() >= cubeToAdd.getMinY()
							&& cubes.get(i).getMinY() <= cubeToAdd.getMaxY()
							&& cubes.get(i).getMinZ() >= cubeToAdd.getMinZ()
							&& cubes.get(i).getMinZ() <= cubeToAdd.getMaxZ()) {
						cubes.remove(i);
						i--;
					}
				}
			}
		}

		return false;
	}

	private boolean hasUpperCollision(Cube upperCube, Cube lowerCube) {
		return upperCube.getMaxX() > lowerCube.getMaxX() && upperCube.getMinX() <= lowerCube.getMaxX()
				&& isInsideY(upperCube, lowerCube) && isInsideZ(upperCube, lowerCube);
	}

	private boolean hasLowerCollision(Cube lowerCube, Cube upperCube) {
		return lowerCube.getMinX() < upperCube.getMinX() && lowerCube.getMaxX() >= upperCube.getMinX()
				&& isInsideY(lowerCube, upperCube);
	}

	private boolean hasRightCollision(Cube rightCube, Cube leftCube) {
		return rightCube.getMaxY() > leftCube.getMaxY() && rightCube.getMinY() <= leftCube.getMaxY()
				&& isInsideX(rightCube, leftCube);
	}

	private boolean hasLeftCollision(Cube leftCube, Cube rightCube) {
		return leftCube.getMinY() < rightCube.getMinY() && leftCube.getMaxY() >= rightCube.getMinY()
				&& isInsideX(leftCube, rightCube) && isInsideZ(leftCube, rightCube);
	}

	private boolean hasBackCollision(Cube backCube, Cube frontCube) {
		return backCube.getMaxZ() > frontCube.getMaxZ() && backCube.getMinZ() <= frontCube.getMaxZ()
				&& isInsideX(backCube, frontCube) && isInsideY(backCube, frontCube);
	}

	private boolean hasFrontCollision(Cube frontCube, Cube backCube) {
		return frontCube.getMinZ() < backCube.getMinZ() && frontCube.getMaxZ() >= backCube.getMinZ()
				&& isInsideX(frontCube, backCube) && isInsideY(frontCube, backCube);
	}

	private boolean isInsideX(Cube innerCube, Cube outerCube) {
		return innerCube.getMinX() >= outerCube.getMinX() && innerCube.getMinX() <= outerCube.getMaxX()
				|| innerCube.getMaxX() >= outerCube.getMinX() && innerCube.getMaxX() <= outerCube.getMaxX();
	}

	private boolean isInsideY(Cube innerCube, Cube outerCube) {
		return innerCube.getMinY() >= outerCube.getMinY() && innerCube.getMinY() <= outerCube.getMaxY()
				|| innerCube.getMaxY() >= outerCube.getMinY() && innerCube.getMaxY() <= outerCube.getMaxY();
	}

	private boolean isInsideZ(Cube innerCube, Cube outerCube) {
		return innerCube.getMinZ() >= outerCube.getMinZ() && innerCube.getMinZ() <= outerCube.getMaxZ()
				|| innerCube.getMaxZ() >= outerCube.getMinZ() && innerCube.getMaxZ() <= outerCube.getMaxZ();
	}

	@Override
	protected String getSolution(List<Cube> cubes) {
		var solution = 0L;
		for (var cube : cubes) {
			solution += (cube.getMaxX() - cube.getMinX() + 1) * (cube.getMaxY() - cube.getMinY() + 1)
					* (cube.getMaxZ() - cube.getMinZ() + 1);
		}
		return solution + "";
	}
}
