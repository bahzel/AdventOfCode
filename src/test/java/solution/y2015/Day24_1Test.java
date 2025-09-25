package solution.y2015;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day24_1Test {
	@Test
	public void test() {
		assertThat(new Day24_1().disableLog().solve()).isEqualTo("10723906903");
	}
}
