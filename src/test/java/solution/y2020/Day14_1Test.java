package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day14_1Test {
	@Test
	public void test() {
		assertThat(new Day14_1().disableLog().solve()).isEqualTo("7611244640053");
	}
}
