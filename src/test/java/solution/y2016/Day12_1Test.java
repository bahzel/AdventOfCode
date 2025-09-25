package solution.y2016;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12_1Test {
    @Test
    public void test() {
        assertThat(new Day12_1().disableLog().solve()).isEqualTo("317993");
    }
}
