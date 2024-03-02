package ICSI404;


import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment3Tests{
	


	@Test
	public void incrementTests(){
		Word test = new Word();

		test.set(5);
		test = test.increment();
		assertEquals(6, test.getSigned());

		test.set(-3);
		test = test.increment();
		assertEquals(-2, test.getSigned());

		test.set(2147483646);
		test = test.increment();
		assertEquals(2147483647, test.getSigned());

		test.set(3008965);
		test = test.increment();
		assertEquals(3008966, test.getSigned());

		test.set(-2147483647);
		test = test.increment();
		assertEquals(-2147483646, test.getSigned());

		test.set(0);
		test = test.increment();
		assertEquals(1, test.getSigned());

		test.set(499999);
		test = test.increment();
		assertEquals(500000, test.getSigned());

		test.set(-1);
		test = test.increment();
		assertEquals(0, test.getSigned());
		
		test.set(-500001);
		test = test.increment();
		assertEquals(-500000, test.getSigned());
	}


	@Test
	public void memloadTests(){
		String[] memload = {
			"00000000000000000000000000000000",
    		"00000000000000000000000000000001",
    		"00000000000000000000000000000010",
    		"00000000000000000000000000000011",
    		"00000000000000000000000000000100",
    		"00000000000000000000000000000101",
    		"00000000000000000000000000000110",
    		"00000000000000000000000000000111",
    		"00000000000000000000000000001000",
    		"00000000000000000000000000001001",
    		"00000000000000000000000000001010",
		};

		MainMemory.load(memload);
		Word addr = new Word();
		for(int i = 0; i < memload.length; i++){
			addr.set(i);
			assertEquals(i, MainMemory.read(addr).getSigned());
		}
	}

	public void readWriteTests(){
		String[] memload = {
			"00000000000000000000000000000000",
    		"00000000000000000000000000000001",
    		"00000000000000000000000000000010",
    		"00000000000000000000000000000011",
    		"00000000000000000000000000000100",
    		"00000000000000000000000000000101",
    		"00000000000000000000000000000110",
    		"00000000000000000000000000000111",
    		"00000000000000000000000000001000",
    		"00000000000000000000000000001001",
    		"00000000000000000000000000001010",
		};

		MainMemory.load(memload);
		Word addr = new Word();
		addr.set(9);
		assertEquals(9, MainMemory.read(addr));
		Word value = new Word();
		
		value.set(2147483647);
		addr.set(1023);
		MainMemory.write(addr, value); 
		assertEquals(2147483647, MainMemory.read(addr));

		value.set(998);
		addr.set(999);
		MainMemory.write(addr, value);
		assertEquals(998, MainMemory.read(addr));
		
		//rewrite memory at address 1023
		addr.set(1023);
		MainMemory.write(addr, value);
		assertEquals(998, MainMemory.read(addr));
	}
}
