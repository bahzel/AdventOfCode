package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day1_2Test {
	@Test
	public void test() {
		assertThat(new Day1_2().disableLog().solve()).isEqualTo("128397680");
	}
}
