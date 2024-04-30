package ICSI404;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class ArrayIntegersBackwardsTest {
    @Test
    public void arrayBackwardsTest(){
        Word index = new Word(200);

        MainMemory.write(index, new Word(10));
        index = index.increment();
        MainMemory.write(index, new Word(9));
        index = index.increment();
        MainMemory.write(index, new Word(8));
        index = index.increment();
        MainMemory.write(index, new Word(7));
        index = index.increment();
        MainMemory.write(index, new Word(6));
        index = index.increment();
        MainMemory.write(index, new Word(5));
        index = index.increment();
        MainMemory.write(index, new Word(4));
        index = index.increment();
        MainMemory.write(index, new Word(3));
        index = index.increment();
        MainMemory.write(index, new Word(2));
        index = index.increment();
        MainMemory.write(index, new Word(1));
        index = index.increment();
        MainMemory.write(index, new Word(2));
        index = index.increment();
        MainMemory.write(index, new Word(3));
        index = index.increment();
        MainMemory.write(index, new Word(4));
        index = index.increment();
        MainMemory.write(index, new Word(5));
        index = index.increment();
        MainMemory.write(index, new Word(6));
        index = index.increment();
        MainMemory.write(index, new Word(7));
        index = index.increment();
        MainMemory.write(index, new Word(8));
        index = index.increment();
        MainMemory.write(index, new Word(9));
        index = index.increment();
        MainMemory.write(index, new Word(10));
        index = index.increment();
        MainMemory.write(index, new Word(999));

        String asm = """
			COPY R2 1
			#total should be 1108
			COPY R3 200
			COPY R7 219
			#insutruction 3
			LOAD R4 R7
			MATH ADD R5 R4
			MATH SUBTRACT R7 R2
			BRANCH LTE R7 R3 -4
			HALT
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

        System.out.println("SUM OF ARRAY INTEGERS BACKWARDS");
        processor.run();
        assertEquals(1108, processor.registers[5].getSigned());
        MainMemory.clear();
        InstructionCache.cacheClear();
        L2Cache.cacheClear();
    }
}
