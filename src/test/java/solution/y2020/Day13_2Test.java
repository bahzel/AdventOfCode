package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day13_2Test {
	@Test
	public void test() {
		assertThat(new Day13_2().disableLog().solve()).isEqualTo("526090562196173");
	}
}
