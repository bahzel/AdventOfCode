package solution.y2021;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day24_2Test {
	@Test
	public void test() {
		assertThat(new Day24_2().disableLog().solve()).isEqualTo("11211791111365");
	}
}
