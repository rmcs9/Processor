package ICSI404;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class LinkedListTest {
    @Test
    public void linkedListTest(){
        // 20, 30, 46, 72, 21, 32, 40, 59, 99, 129, 999, 23, 64, 52, 27, 999, 679, 15, 1, 20 = 3427
        MainMemory.write(new Word(352), new Word(20));
        MainMemory.write(new Word(353), new Word(252));

        MainMemory.write(new Word(252), new Word(30));
        MainMemory.write(new Word(253), new Word(391));

        MainMemory.write(new Word(391), new Word(46));
        MainMemory.write(new Word(392), new Word(284));

        MainMemory.write(new Word(284), new Word(72));
        MainMemory.write(new Word(285), new Word(554));

        MainMemory.write(new Word(554), new Word(21));
        MainMemory.write(new Word(555), new Word(202));

        MainMemory.write(new Word(202), new Word(32));
        MainMemory.write(new Word(203), new Word(495));

        MainMemory.write(new Word(495), new Word(40));
        MainMemory.write(new Word(496), new Word(540));

        MainMemory.write(new Word(540), new Word(59));
        MainMemory.write(new Word(541), new Word(319));

        MainMemory.write(new Word(319), new Word(99));
        MainMemory.write(new Word(320), new Word(316));

        MainMemory.write(new Word(316), new Word(129));
        MainMemory.write(new Word(317), new Word(274));

        MainMemory.write(new Word(274), new Word(20));
        MainMemory.write(new Word(275), new Word(310));

        MainMemory.write(new Word(310), new Word(999));
        MainMemory.write(new Word(311), new Word(458));

        MainMemory.write(new Word(458), new Word(23));
        MainMemory.write(new Word(459), new Word(325));

        MainMemory.write(new Word(325), new Word(64));
        MainMemory.write(new Word(326), new Word(333));

        MainMemory.write(new Word(333), new Word(52));
        MainMemory.write(new Word(334), new Word(372));

        MainMemory.write(new Word(372), new Word(27));
        MainMemory.write(new Word(373), new Word(552));

        MainMemory.write(new Word(552), new Word(999));
        MainMemory.write(new Word(553), new Word(531));

        MainMemory.write(new Word(531), new Word(679));
        MainMemory.write(new Word(532), new Word(498));

        MainMemory.write(new Word(498), new Word(15));
        MainMemory.write(new Word(499), new Word(207));

        MainMemory.write(new Word(207), new Word(1));
        MainMemory.write(new Word(208), new Word(800));

        String asm = """
				COPY R1 352
				LOAD R2 R1
				MATH ADD R3 R2
				LOAD R1 1
				LOAD R2 R1
				BRANCH NE R2 R0 -4
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


        System.out.println("SUM OF LINKED LIST MEMBERS:");
        processor.run();
        assertEquals(3427, processor.registers[3].getSigned());
        MainMemory.clear();
        InstructionCache.cacheClear();
        L2Cache.cacheClear();
    }
}
