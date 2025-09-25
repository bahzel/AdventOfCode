package solution.y2021;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day22_2Test {
	@Test
	public void test() {
		assertThat(new Day22_2().disableLog().solve()).isEqualTo("1263946820845866");
	}
}
