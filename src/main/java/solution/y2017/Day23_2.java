package solution.y2017;

import utils.soution.Solution;

public class Day23_2 extends Solution {
	public static void main(String[] args) {
		new Day23_2().solve();
	}

	@Override
	protected String doSolve() {
		long a = 1;
		long b = 0;
		long c = 0;
		long d = 0;
		long e = 0;
		long f = 0;
		long g = 0;
		long h = 0;

		b = 79;
		c = b;
		if (a != 0) {
			b *= 100;
			b -= -100000;
			c = b;
			c -= -17000;
		}
		do {
			f = 1;
			d = 2;
			do {
				e = 2;
				do {
					g = d;
					g *= e;
					g -= b;
					if (g == 0) {
						f = 0;
					}
					e -= -1;
					g = e;
					g -= b;
				} while (g != 0);
				d -= -1;
				g = d;
				g -= b;
			} while (g != 0);
			if (f == 0) {
				h -= -1;
			}
			g = b;
			g -= c;
			if (g != 0) {
				b -= -17;
			}
			System.out.println(h + " - " + g);
		} while (g != 0);

		return h + "";
	}
}
