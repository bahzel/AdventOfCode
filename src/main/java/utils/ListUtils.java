package utils;

import java.util.List;

public class ListUtils {
	private ListUtils() {
	}

	public static int countCycleSize(List<?> list) {
		for (var size = 1; size <= list.size() / 2; size++) {

			var isCycle = true;
			for (var i = 0; i < size; i++) {
				if (!list.get(list.size() - i - 1).equals(list.get(list.size() - i - 1 - size))) {
					isCycle = false;
					break;
				}
			}
			if (isCycle) {
				return size;
			}
		}

		return -1;
	}
}