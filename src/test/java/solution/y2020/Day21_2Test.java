package solution.y2020;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class Day21_2Test {
	@Test
	public void test() {
		assertThat(new Day21_2().disableLog().solve()).isEqualTo("gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr");
	}
}
