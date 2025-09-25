package solution.y2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2_1Test {
    @Test
    public void test() {
        assertThat(new Day2_1().disableLog().solve()).isEqualTo("32020");
    }
}
