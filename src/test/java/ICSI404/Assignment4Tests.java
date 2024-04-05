package ICSI404;


import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment4Tests{
	
	@Test
	public void computerTest1(){
		System.out.println("COMPUTER TEST 1");
		//TEST FROM THE DOC:
		//MATH DESTONLY 5, R1
		//MATH ADD R1 R1 R2
		//MATH ADD R2 R2
		//MATH ADD R2 R1 R3
		//HALT
		String[] instructions = {
			"00000000000000010100000000100001",
			"00000000000010000111100001000010",
			"00000000000000001011100001000011",
			"00000000000100000111100001100010",
			"00000000000000000000000000000000",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();
			
		processor.run();
		assertEquals(25, processor.registers[3].getSigned());	
		MainMemory.clear();

		System.out.println();
	}


	@Test
	public void computerTest2(){
		System.out.println("COMPUTER TEST 2");
		//ATTEMPTING TO WRITE THE VALUE 7 TO REGISTER 0
		//MATH DESTONLY 7, R0
		String[] instructions = {
			"00000000000000011100000000000001",
			"00000000000000000000000000000000",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(0, processor.registers[0].getSigned());
		MainMemory.clear();

		System.out.println();
	}

	@Test
	public void computerTest3(){
		System.out.println("COMPUTER TEST 3");
		//MATH DESTONLY 33 -> R3
		//MATH DESTONLY 3 -> R9
		//MATH DESTONLY 10 -> R4
		//MATH 2R R4 * R3 -> R3
		//MATH 3R R3 * R9 -> R31
		String[] instructions = {
			"00000000000010000100000001100001",
			"00000000000000001100000100100001",
			"00000000000000101000000010000001",
			"00000000000000010001110001100011",
			"00000000000110100101111111100010",
			"00000000000000000000000000000000",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(990, processor.registers[31].getSigned());
		MainMemory.clear();

		System.out.println();
	}

	@Test
	public void computerTest4(){
		System.out.println("COMPUTER TEST 4");
		//MATH DESTONLY 33 -> R3
		//MATH DESTONLY 3 -> R9
		//MATH DESTONLY 10 -> R4
		//MATH 2R R4 - R3 -> R3
		//MATH 3R R3 - R9 -> R31
		String[] instructions = {
			"00000000000010000100000001100001",
			"00000000000000001100000100100001",
			"00000000000000101000000010000001",
			"00000000000000010011110001100011",
			"00000000000110100111111111100010",
			"00000000000000000000000000000000",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(20, processor.registers[31].getSigned());
		MainMemory.clear();

		System.out.println();
	}
}

