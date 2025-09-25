package solution.y2021;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day18_1Test {
	@Test
	public void test() {
		assertThat(new Day18_1().disableLog().solve()).isEqualTo("3725");
	}

	@Test
	public void explode1() {
		assertThat(new Day18_1().reduce("[[[[[9,8],1],2],3],4]")).isEqualTo("[[[[0,9],2],3],4]");
	}

	@Test
	public void explode2() {
		assertThat(new Day18_1().reduce("[7,[6,[5,[4,[3,2]]]]]")).isEqualTo("[7,[6,[5,[7,0]]]]");
	}

	@Test
	public void explode3() {
		assertThat(new Day18_1().reduce("[[6,[5,[4,[3,2]]]],1]")).isEqualTo("[[6,[5,[7,0]]],3]");
	}

	@Test
	public void explode4() {
		assertThat(new Day18_1().reduce("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")).isEqualTo(
				"[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
	}

	@Test
	public void explode5() {
		assertThat(new Day18_1().reduce("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]")).isEqualTo(
				"[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
	}

	@Test
	public void mixed1() {
		assertThat(new Day18_1().reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")).isEqualTo(
				"[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
	}

	@Test
	public void mixed2() {
		assertThat(new Day18_1().reduce(
				"[[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]],[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]]")).isEqualTo(
						"[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
	}

	@Test
	public void getMagnitude() {
		assertThat(new Day18_1().getMagnitude("[[1,2],[[3,4],5]]")).isEqualTo("143");
	}
}
