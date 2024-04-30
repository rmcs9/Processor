package ICSI404;


import org.junit.Test;
import static org.junit.Assert.*;


public class Assignment5Tests{

// ----------------------------------- ALU BOP TESTS ------------------------------

	@Test
	public void equalsTest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(false);
		opcode[2] = new Bit(false);
		opcode[3] = new Bit(false);

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(12);
		alu.op2.set(21);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-536);
		alu.op2.set(-536);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(654);
		alu.op2.set(-654);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483647);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

	}

	@Test
	public void notEqualsTest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(false);
		opcode[2] = new Bit(false);
		opcode[3] = new Bit(true);
		
		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(125);
		alu.op2.set(210);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-539);
		alu.op2.set(-539);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(65467);
		alu.op2.set(-65467);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483647);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
		
	}

	@Test
	public void lessThanTest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(false);
		opcode[2] = new Bit(true);
		opcode[3] = new Bit(false);
		
		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(125);
		alu.op2.set(210);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-539);
		alu.op2.set(-539);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(65467);
		alu.op2.set(-65467);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483646);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());
	}

	@Test
	public void GTEtest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(false);
		opcode[2] = new Bit(true);
		opcode[3] = new Bit(true);

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(125);
		alu.op2.set(210);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-539);
		alu.op2.set(-539);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(65467);
		alu.op2.set(-65467);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483646);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());	

		alu.op1.set(1);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	}

	@Test
	public void greaterThanTest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(true);
		opcode[2] = new Bit(false);
		opcode[3] = new Bit(false);
		
		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(125);
		alu.op2.set(210);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-539);
		alu.op2.set(-539);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(65467);
		alu.op2.set(-65467);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483646);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());	

		alu.op1.set(1);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	}

	@Test
	public void LTEtest(){
		ALU alu = new ALU();
		Bit[] opcode = new Bit[4];
		opcode[0] = new Bit(false);
		opcode[1] = new Bit(true);
		opcode[2] = new Bit(false);
		opcode[3] = new Bit(true);

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(125);
		alu.op2.set(210);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(-539);
		alu.op2.set(-539);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());

		alu.op1.set(65467);
		alu.op2.set(-65467);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(-2147483647);
		alu.op2.set(-2147483646);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	
		alu.op1.set(2147483647);
		alu.op2.set(2147483646);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());	

		alu.op1.set(1);
		alu.op2.set(0);
		alu.doOperation(opcode);
		assertFalse(alu.res.getBit(0).getValue());

		alu.op1.set(50065);
		alu.op2.set(80068);
		alu.doOperation(opcode);
		assertTrue(alu.res.getBit(0).getValue());
	}

	@Test
	public void decrementTest(){
		Word testWord = new Word();

		testWord.set(2);
		testWord = testWord.decrement();
		assertEquals(1, testWord.getSigned());

		testWord.set(2147483647);
		testWord = testWord.decrement();
		assertEquals(2147483646, testWord.getSigned());

		testWord.set(-2147483647);
		testWord = testWord.decrement();
		assertEquals(-2147483648, testWord.getSigned());

		testWord.set(0);
		testWord = testWord.decrement();
		assertEquals(-1, testWord.getSigned());

		testWord.set(1024);
		testWord = testWord.decrement();
		assertEquals(1023, testWord.getSigned());
	}

	
// ----------------------------------- BRANCH TESTS ------------------------------

	@Test 
	public void branch0R(){
		String[] instructions = {
			//BRANCH 0R JUMP 64
			"000000000000000000001000000"+"00100"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(65, processor.getPC().getSigned());
		MainMemory.clear();
		processor = new Processor();

		//BRANCH 0R JUMP 127
		instructions[0] = "000000000000000000001111111"+"00100";
		MainMemory.load(instructions);
		processor.run();
		assertEquals(128, processor.getPC().getSigned());
		MainMemory.clear();
	}

	@Test
	public void branch1R(){
		String[] instructions = {
			//MATH 1R R1 1
			"000000000000000001"+"0000"+"00001"+"00001",	
			//MATH 1R R2 2
			"000000000000000010"+"0000"+"00010"+"00001",	
			//BRANCH 1R JUMP PC + 31
			"000000000000011111"+"0000"+"00000"+"00101"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(35, processor.getPC().getSigned());
		MainMemory.clear();
	}
	
	@Test 
	public void branch2R(){
		String[] instructions = {
			//MATH 1R R1 1
			"000000000000000001"+"0000"+"00001"+"00001",	
			//MATH 1R R2 2
			"000000000000000010"+"0000"+"00010"+"00001",	
			//BRANCH 2R R2 GT R1 ? PC + 3
			"0000000000011"+"00010"+"0100"+"00001"+"00111",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(7, processor.getPC().getSigned());
		MainMemory.clear();

		instructions[2] = "0000000000011"+"00001"+"0100"+"00010"+"00111"; 
		MainMemory.load(instructions);
		processor = new Processor();

		processor.run();
		assertEquals(4, processor.getPC().getSigned());
		MainMemory.clear();
	}

	@Test
	public void branch3R(){
		String[] instructions = {
			//MATH 1R R1 1
			"000000000000000001"+"0000"+"00001"+"00001",	
			//MATH 1R R2 2
			"000000000000000010"+"0000"+"00010"+"00001",	
			//BRANCH 3R R1 NE R2 ? PC + 3
			"00000011"+"00001"+"00010"+"0001"+"00000"+"00110"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(7, processor.getPC().getSigned());
		MainMemory.clear();

		instructions[2] = "00000011"+"00001"+"00010"+"0000"+"00000"+"00110";
		MainMemory.load(instructions);
		processor = new Processor();

		processor.run();
		assertEquals(4, processor.getPC().getSigned());
		MainMemory.clear();
	}


// ----------------------------------- CALL TESTS ------------------------------

	@Test
	public void call0R(){
		String[] instructions = {
			//CALL 0R 15
			"000000000000000000000001111"+"01000"		
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(16, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(1, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}
	
	@Test
	public void call1R(){
		String[] instructions = {
			//MATH 1R R2 31
			"000000000000011111"+"0000"+"00010"+"00001",
			//CALL 1R 128 
			"000000000010000000"+"0000"+"00010"+"01001",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(160, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(2, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

	@Test
	public void call2R(){
		String[] instructions = {
			//MATH 1R R2 31
			"000000000000011111"+"0000"+"00010"+"00001",
			//MATH 1R R3 31
			"000000000000011111"+"0000"+"00011"+"00001",
			//CALL 2R R2 EQ R3 ? PC + 32
			"0000000100000"+"00010"+"0000"+"00011"+"01011"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(36, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(3, MainMemory.read(addr).getSigned());
		MainMemory.clear();
		
		//CALL 2R R2 NE R3 ? PC + 32
		instructions[2] = "0000000100000"+"00010"+"0001"+"00011"+"00011";
		MainMemory.load(instructions);
		processor = new Processor();

		processor.run();
		assertEquals(4, processor.getPC().getSigned());
		assertEquals(0, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

	@Test
	public void call3R(){
		String[] instructions = {
			//MATH 1R R2 31
			"000000000000011111"+"0000"+"00010"+"00001",
			//MATH 1R R3 15
			"000000000000001111"+"0000"+"00011"+"00001",
			//MATH 1R R1 64
			"000000000001000000"+"0000"+"00001"+"00001",
			//CALL 3R R2 GT R3 ? RD + 2	
			"00000010"+"00010"+"00011"+"0100"+"00001"+"01010"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(67, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(4, MainMemory.read(addr).getSigned());
		MainMemory.clear();

		//CALL 3R R2 LT R3 ? RD + 2
		instructions[3] = "00000010"+"00010"+"00011"+"0010"+"00001"+"01010";
		MainMemory.load(instructions);
		processor = new Processor();

		processor.run();
		assertEquals(5, processor.getPC().getSigned());
		assertEquals(0, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

// ----------------------------------- PUSH TESTS ------------------------------

	@Test
	public void pushTest(){
		String[] instructions = {
			//MATH 1R R2 31
			"000000000000011111"+"0000"+"00010"+"00001",
			//MATH 1R R3 15
			"000000000000001111"+"0000"+"00011"+"00001",
			//MATH 1R R1 64
			"000000000001000000"+"0000"+"00001"+"00001",
			//PUSH 1R R1 * 2
			"000000000000000010"+"0111"+"00001"+"01101",
			//PUSH 2R RD + RS 
			"0000000000000"+"00011"+"1110"+"00010"+"01111",
			//PUSH 3R RS1 + RS2
			"00000000"+"00001"+"00010"+"1110"+"00000"+"01110"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		Word addr = new Word();
		addr.set(1023);

		assertEquals(64 * 2, MainMemory.read(addr).getSigned());
		addr = addr.decrement();
		assertEquals(31 + 15, MainMemory.read(addr).getSigned());
		addr = addr.decrement();
		assertEquals(64 + 31, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}


// ----------------------------------- LOAD TESTS ------------------------------


	@Test
	public void loadReturnTest(){
		String[] instructions = {
			//PUSH 1R 16 -> STACK
			"000000000000010000"+"1110"+"00000"+"01101",
			//LOAD 0R RETURN PC <- 16
			"000000000000000000000000000"+"10000"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(17, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(0, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

	@Test
	public void loadTests(){
		Word addr = new Word();
		addr.set(500);
		Word value = new Word();
		value.set(555);
		MainMemory.write(addr, value);
		
		addr.set(700);
		value.set(777);
		MainMemory.write(addr, value);

		addr.set(800);
		value.set(888);
		MainMemory.write(addr, value);

		String[] instructions = {
			//LOAD 1R mem[500] -> R2
			"000000000111110100"+"0000"+"00010"+"10001",
			//LOAD 2R mem[RS + 145] -> R1
			"0000010010001"+"00010"+"0000"+"00001"+"10011",	
			//MATH 1R COPY 23 -> R3
			"000000000000010111"+"0000"+"00011"+"00001",
			//LOAD 3R mem[RS1 + RS2] -> R4
			"00000000"+"00001"+"00011"+"0000"+"00100"+"10010",
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();
		
		processor.run();
		assertEquals(555, processor.registers[2].getSigned());
		assertEquals(777, processor.registers[1].getSigned());
		assertEquals(888, processor.registers[4].getSigned());
		MainMemory.clear();
	}


// ----------------------------------- STORE TESTS ------------------------------

	@Test
	public void storeTests(){
		String[] instructions = {
			//MATH 1R R2 31
			"000000000000011111"+"0000"+"00010"+"00001",
			//MATH 1R R3 47
			"000000000000101111"+"0000"+"00011"+"00001",
			//MATH 1R R1 128
			"000000000010000000"+"0000"+"00001"+"00001",
			//STORE 1R mem[R1] = 512
			"000000001000000000"+"0000"+"00001"+"10101",
			//STORE 2R mem[R3 + imm] = R2
			"0000000000011"+"00010"+"0000"+"00011"+"10111",	
			//STORE 3R mem[R1 + R2] = R3 
			"00000000"+"00010"+"00011"+"0000"+"00001"+"10110"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		Word addr = new Word();
		addr.set(128);
		assertEquals(512, MainMemory.read(addr).getSigned());
		addr.set(50);
		assertEquals(31, MainMemory.read(addr).getSigned());
		addr.set(159);
		assertEquals(47, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}



// ----------------------------------- PEEK TESTS -------------------------------
	

	@Test
	public void peekTests(){
		String[] instructions = {
			//PUSH 256
			"000000000100000000"+"1110"+"00000"+"01101",
			//PUSH 128
			"000000000010000000"+"1110"+"00000"+"01101",
			//PUSH 64
			"000000000001000000"+"1110"+"00000"+"01101",
			//MATH 1 -> R3
			"000000000000000001"+"0000"+"00011"+"00001",
			//PEEK 2R PEEK 2 -> R2
			"0000000000010"+"00000"+"0000"+"00010"+"11011",
			//PEEK 3R PEEK 1 -> R1
			"00000000"+"00000"+"00011"+"0000"+"00001"+"11010",
			//POP -> R5
			"000000000000000000"+"0000"+"00101"+"11001"
		};

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(256, processor.registers[2].getSigned());
		assertEquals(128, processor.registers[1].getSigned());
		assertEquals(64, processor.registers[5].getSigned());
		MainMemory.clear();
	}
}
