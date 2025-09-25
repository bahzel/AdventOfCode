package solution.y2019;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day23_1Test {
    @Test
    public void test() {
        assertThat(new Day23_1().disableLog().solve()).isEqualTo("15416");
    }
}
