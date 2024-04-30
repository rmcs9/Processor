package ICSI404;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;


public class Assignment6Tests{

	@Test
	public void quickMathTest(){
		String asm = """
			COPY R1 -200
			COPY R3 -300
			MATH ADD R2 R1 R3
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(-500, processor.registers[2].getSigned());
		MainMemory.clear();
	}

	@Test
	public void computerTest1ASM(){
		String asm = """
			COPY R1 5
			MATH ADD R2 R1 R1
			MATH ADD R2 R2
			MATH ADD R3 R2 R1
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(25, processor.registers[3].getSigned());
		MainMemory.clear();
	}

	@Test
	public void computerTest2ASM(){
		String asm = 
			"COPY R0 7\n";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(0, processor.registers[0].getSigned());
		MainMemory.clear();

	}

	@Test
	public void computerTest3ASM(){
		String asm = """
			COPY R3 33
			COPY R9 3
			COPY R4 10
			MATH MULTIPLY R3 R4
			MATH MULTIPLY R31 R3 R9
			""";


		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(990, processor.registers[31].getSigned());
		MainMemory.clear();
	}


	@Test
	public void JUMPTests(){
		String asm = 
			"JUMP 127\n";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(128, processor.getPC().getSigned());
		MainMemory.clear();
	}

	@Test
	public void JUMPTOTest(){
		String asm = """
			COPY R1 1
			COPY R2 2
			JUMP R0 31	
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(35, processor.getPC().getSigned());
		MainMemory.clear();
	}

	@Test
	public void branch2RTest(){
		String asm = """
			COPY R1 1
			COPY R2 2
			BRANCH NE R1 R2 3
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}
		System.out.println("THREAD: " + Thread.currentThread().getName());
		System.out.println("BRANCH 2R INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(7, processor.getPC().getSigned());
		MainMemory.clear();

	}

	@Test
	public void branch3RTest(){
		String asm = """
			COPY R1 1
			COPY R2 2
			BRANCH NE R1 R2 R0 3
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(7, processor.getPC().getSigned());
		MainMemory.clear();
	}

	@Test
	public void call0RTest(){
		String asm = """
			CALL 15
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

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
	public void call1RTest(){
		String asm = """
			COPY R2 31
			CALL R2 128
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("THREAD: " + Thread.currentThread().getName());
		System.out.println("CALL 1R INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

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
	public void call2RTest(){
		String asm = """
			COPY R2 31
			COPY R3 31
			CALL EQ R2 R3 32
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();
		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("THREAD: " + Thread.currentThread().getName());
		System.out.println("CALL 2R INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(36, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(3, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

	@Test
	public void call3RTest(){
		String asm = """
			COPY R2 31
			COPY R3 15
			COPY R1 64
			CALL GT R1 R2 R3 2
			""";
		
		Lexer lexer = new Lexer(asm);
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("CALL 3R INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(67, processor.getPC().getSigned());
		Word addr = new Word();
		addr.set(1023);
		assertEquals(4, MainMemory.read(addr).getSigned());
		MainMemory.clear();
	}

	@Test
	public void pushTest(){
		String asm = """
			COPY R2 31
			COPY R3 15 
			COPY R1 64
			PUSH MULTIPLY R1 2
			PUSH ADD R3 R2
			PUSH ADD R0 R1 R2
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("PUSH INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

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

	@Test
	public void returnTest(){
		String asm = """
			PUSH ADD R0 16
			RETURN
			""";
			
		Lexer lexer = new Lexer(asm);
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("RETURN INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

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
	public void storeTest(){
		String asm = """
			COPY R2 31
			COPY R3 47
			COPY R1 128
			STORE R1 512
			STORE R3 R2 3
			STORE R1 R2 R3
			""";
		
		Lexer lexer = new Lexer(asm);
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("STORE INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

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

	@Test
	public void peekTest(){
		String asm = """
			PUSH ADD R0 256
			PUSH ADD R0 128
			PUSH ADD R0 64
			COPY R3 1
			PEEK R2 R0 2
			PEEK R1 R0 R3
			POP R5
			""";

		Lexer lexer = new Lexer(asm);
		lexer.Lex();

		Parser parser = new Parser(lexer.tokens);
		LinkedList<String> instructs = parser.parse();
		String[] instructions = new String[instructs.size()];
		for(int i = 0; i < instructions.length; i++){
			instructions[i] = instructs.get(i);
		}

		System.out.println("PEEK INSTRUCTIONS:");
		for(String in : instructions){
			System.out.println(in);
		}

		MainMemory.load(instructions);
		Processor processor = new Processor();

		processor.run();
		assertEquals(256, processor.registers[2].getSigned());
		assertEquals(128, processor.registers[1].getSigned());
		assertEquals(64, processor.registers[5].getSigned());
		MainMemory.clear();
	}
}
