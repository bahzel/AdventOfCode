package solution.y2019;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day24_1Test {
	@Test
	public void test() {
		assertThat(new Day24_1().disableLog().solve()).isEqualTo("18400821");
	}
}
