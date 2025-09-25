package solution.y2017;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day20_2Test {
	@Test
	public void test() {
		assertThat(new Day20_2().solve()).isEqualTo("504");
	}

	@Test
	public void tripleCollissionsAreDetected() {
		assertThat(new Day20_2(List.of("p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>", "p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>",
				"p=<-2,0,0>, v=<1,0,0>, a=<0,0,0>", "p=<3,0,0>, v=<-1,0,0>, a=<0,0,0>")).solve()).isEqualTo("1");
	}

	@Test
	public void earliestCollissionsAreEliminatedFirst() {
		assertThat(new Day20_2(List.of("p=<-6,0,0>, v=<3,0,0>, a=<0,0,0>", "p=<-4,0,0>, v=<2,0,0>, a=<0,0,0>",
				"p=<-5,0,0>, v=<1,0,0>, a=<0,0,0>", "p=<-6,0,0>, v=<-1,0,0>, a=<0,0,0>")).solve()).isEqualTo("2");
	}
}
