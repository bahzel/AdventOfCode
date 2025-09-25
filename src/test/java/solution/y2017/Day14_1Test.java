package solution.y2017;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14_1Test {
    @Test
    public void test() {
        assertThat(new Day14_1().disableLog().solve()).isEqualTo("8304");
    }
}
