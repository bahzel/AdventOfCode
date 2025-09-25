package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day10_2Test {
	@Test
	public void test() {
		assertThat(new Day10_2().disableLog().solve()).isEqualTo("148098383347712");
	}
}
