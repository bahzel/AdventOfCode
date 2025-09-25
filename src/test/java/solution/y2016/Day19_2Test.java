package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day19_2Test {
    @Test
    public void test() {
        assertThat(new Day19_2().disableLog().solve()).isEqualTo("1410967");
    }
}
