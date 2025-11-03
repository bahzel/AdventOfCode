package solution.y2023;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day12_2Test {
	@Test
	public void test() {
		assertThat(new Day12_2().disableLog().solve()).isEqualTo("4443895258186");
	}
}
