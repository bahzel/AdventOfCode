package solution.y2023;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day17_2Test {
	@Test
	public void test() {
		assertThat(new Day17_2().disableLog().solve()).isEqualTo("1249");
	}
}
