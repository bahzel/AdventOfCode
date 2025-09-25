package solution.y2021;

import java.util.ArrayList;
import java.util.List;

import utils.Point3D;
import utils.soution.Solution;

public class Day19_1 extends Solution {
	public static void main(String[] args) {
		new Day19_1().solve();
	}

	@Override
	protected String doSolve() {
		var beacons = getBeacons();
		var mainBeacon = beacons.getFirst();
		var rotatedBeacons = new ArrayList<>(
				beacons.subList(1, beacons.size()).stream().map(this::getRotatedBeacons).toList());
		for (int i = 0; i < rotatedBeacons.size(); i++) {
			println(i);
			for (var rotatedBeacon : rotatedBeacons.get(i)) {
				var repositionedBeacon = getRepositionedBeacon(mainBeacon, rotatedBeacon);
				if (repositionedBeacon != null) {
					println("found " + rotatedBeacons.size());
					rotatedBeacons.remove(i);
					i = -1;
					for (var point : repositionedBeacon) {
						if (!mainBeacon.contains(point)) {
							mainBeacon.add(point);
						}
					}
				}
			}
		}
		return mainBeacon.size() + "";
	}

	private List<List<Point3D>> getBeacons() {
		var beacons = new ArrayList<List<Point3D>>();
		ArrayList<Point3D> beacon = null;
		for (var instruction : input) {
			if (instruction.startsWith("---")) {
				beacon = new ArrayList<>();
				beacons.add(beacon);
			} else if (!instruction.isEmpty()) {
				var coordinates = instruction.split(",");
				beacon.add(new Point3D(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]),
						Integer.parseInt(coordinates[2])));
			}
		}
		return beacons;
	}

	private List<Point3D> getRepositionedBeacon(List<Point3D> mainBeacon, List<Point3D> beaconToCheck) {
		for (var i = 0; i < beaconToCheck.size() - 11; i++) {
			for (var j = 0; j < mainBeacon.size(); j++) {
				var deltaX = mainBeacon.get(j).getX() - beaconToCheck.get(i).getX();
				var deltaY = mainBeacon.get(j).getY() - beaconToCheck.get(i).getY();
				var deltaZ = mainBeacon.get(j).getZ() - beaconToCheck.get(i).getZ();
				var count = 1;
				for (var k = i + 1; k < beaconToCheck.size(); k++) {
					if (mainBeacon.contains(new Point3D(beaconToCheck.get(k).getX() + deltaX,
							beaconToCheck.get(k).getY() + deltaY, beaconToCheck.get(k).getZ() + deltaZ))) {
						count++;
					}
				}
				if (count >= 12) {
					return beaconToCheck.stream()
										.map(point -> new Point3D(point.getX() + deltaX, point.getY() + deltaY,
												point.getZ() + deltaZ))
										.toList();
				}
			}
		}
		return null;
	}

	private List<List<Point3D>> getRotatedBeacons(List<Point3D> beacon) {
		var rotatedBeacons = new ArrayList<List<Point3D>>();
		for (int i = 0; i < 24; i++) {
			rotatedBeacons.add(new ArrayList<>());
		}
		for (var point : beacon) {
			rotatedBeacons.getFirst().add(new Point3D(point.getX(), point.getY(), point.getZ()));
			rotatedBeacons.get(1).add(new Point3D(point.getX(), point.getZ(), -point.getY()));
			rotatedBeacons.get(2).add(new Point3D(point.getX(), -point.getY(), -point.getZ()));
			rotatedBeacons.get(3).add(new Point3D(point.getX(), -point.getZ(), point.getY()));

			rotatedBeacons.get(4).add(new Point3D(-point.getX(), -point.getY(), point.getZ()));
			rotatedBeacons.get(5).add(new Point3D(-point.getX(), point.getZ(), point.getY()));
			rotatedBeacons.get(6).add(new Point3D(-point.getX(), point.getY(), -point.getZ()));
			rotatedBeacons.get(7).add(new Point3D(-point.getX(), -point.getZ(), -point.getY()));

			rotatedBeacons.get(8).add(new Point3D(point.getY(), -point.getX(), point.getZ()));
			rotatedBeacons.get(9).add(new Point3D(point.getY(), point.getZ(), point.getX()));
			rotatedBeacons.get(10).add(new Point3D(point.getY(), point.getX(), -point.getZ()));
			rotatedBeacons.get(11).add(new Point3D(point.getY(), -point.getZ(), -point.getX()));

			rotatedBeacons.get(12).add(new Point3D(-point.getY(), point.getX(), point.getZ()));
			rotatedBeacons.get(13).add(new Point3D(-point.getY(), point.getZ(), -point.getX()));
			rotatedBeacons.get(14).add(new Point3D(-point.getY(), -point.getX(), -point.getZ()));
			rotatedBeacons.get(15).add(new Point3D(-point.getY(), -point.getZ(), point.getX()));

			rotatedBeacons.get(16).add(new Point3D(point.getZ(), point.getY(), -point.getX()));
			rotatedBeacons.get(17).add(new Point3D(point.getZ(), -point.getX(), -point.getY()));
			rotatedBeacons.get(18).add(new Point3D(point.getZ(), -point.getY(), point.getX()));
			rotatedBeacons.get(19).add(new Point3D(point.getZ(), point.getX(), point.getY()));

			rotatedBeacons.get(20).add(new Point3D(-point.getZ(), -point.getY(), -point.getX()));
			rotatedBeacons.get(21).add(new Point3D(-point.getZ(), -point.getX(), point.getY()));
			rotatedBeacons.get(22).add(new Point3D(-point.getZ(), point.getY(), point.getX()));
			rotatedBeacons.get(23).add(new Point3D(-point.getZ(), point.getX(), -point.getY()));
		}
		return rotatedBeacons;
	}
}
