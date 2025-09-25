package solution.y2017;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import utils.Point3D;
import utils.soution.MapSolution;

public class Day20_2 extends MapSolution<List<Particle>> {
	public static void main(String[] args) {
		new Day20_2().solve();
	}

	public Day20_2() {
		super();
	}

	public Day20_2(List<String> input) {
		super(input);
	}

	@Override
	protected List<Particle> initializeMapping() {
		return new ArrayList<>();
	}

	@Override
	protected void transformInstruction(String instruction, List<Particle> particles) {
		var instructions = instruction.replace("p=<", "")
									  .replace(">, v=<", "%")
									  .replace(">, a=<", "%")
									  .replace(">", "")
									  .split("%");
		var position = instructions[0].split(",");
		var velocity = instructions[1].split(",");
		var acceleration = instructions[2].split(",");

		particles.add(new Particle(new Point3D(Integer.parseInt(position[0]), Integer.parseInt(position[1]),
				Integer.parseInt(position[2])),
				new Point3D(Integer.parseInt(velocity[0]), Integer.parseInt(velocity[1]),
						Integer.parseInt(velocity[2])),
				new Point3D(Integer.parseInt(acceleration[0]), Integer.parseInt(acceleration[1]),
						Integer.parseInt(acceleration[2]))));
	}

	@Override
	protected String computeSolution(List<Particle> particles) {
		var toRemove = new ArrayList<Particle>();

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < particles.size(); j++) {
				for (int k = j + 1; k < particles.size(); k++) {
					if (particles.get(j).collides(particles.get(k))) {
						toRemove.add(particles.get(j));
						toRemove.add(particles.get(k));
					}
				}
			}
			particles.removeAll(toRemove);
			toRemove.clear();
			particles.forEach(Particle::computeNextPosition);
		}

		return particles.size() + "";
	}
}

@AllArgsConstructor
class Particle {
	private final Point3D position;
	private final Point3D velocity;
	private final Point3D acceleration;

	public void computeNextPosition() {
		velocity.setX(velocity.getX() + acceleration.getX());
		velocity.setY(velocity.getY() + acceleration.getY());
		velocity.setZ(velocity.getZ() + acceleration.getZ());
		position.setX(position.getX() + velocity.getX());
		position.setY(position.getY() + velocity.getY());
		position.setZ(position.getZ() + velocity.getZ());
	}

	public boolean collides(Particle other) {
		return position.equals(other.position);
	}
}
