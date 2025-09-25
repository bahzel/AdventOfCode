package utils;

import com.google.common.collect.Lists;

import java.util.LinkedList;

public class InstructionQueue extends LinkedList<Character> {
	public InstructionQueue(String instructions) {
		addAll(Lists.charactersOf(instructions));
	}
}