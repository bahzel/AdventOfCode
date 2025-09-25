package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day2_1Test {
	@Test
	public void test() {
		assertThat(new Day2_1().disableLog().solve()).isEqualTo("524");
	}
}
