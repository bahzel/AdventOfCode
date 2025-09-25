package solution.y2019;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day7_2Test {
	@Test
	public void test() {
		assertThat(new Day7_2().disableLog().solve()).isEqualTo("9246095");
	}
}
