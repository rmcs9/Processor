package ICSI404;


import org.junit.Test;
import static org.junit.Assert.*;


public class Assignment1Tests{
	
//-----------------------------------------[BIT TESTS]-------------------------------------------------------
	
	@Test
	public void BitGetValue() throws Exception{
		//tests set(), clear(), toggle()
		Bit test = new Bit(false);
		//set bit true and ensure true value
		test.set();
		assertTrue(test.getValue());
		//clear bit and ensure false value
		test.clear();
		assertFalse(test.getValue());
		//toggle bit from false to true
		test.toggle();
		assertTrue(test.getValue());
	}


	@Test
	public void BitAnd() throws Exception{
		//tests Bit.and()
		//and together a false and a true bit:
		Bit test = new Bit(false);
		assertFalse(test.and(new Bit(true)).getValue());
		//and together a false and a false bit
		assertFalse(test.and(new Bit(false)).getValue());
		//and together a true and a true bit
		test.set();
		assertTrue(test.and(new Bit(true)).getValue());
	}

	@Test
	public void BitOr() throws Exception{
		//tests Bit.or()
		//or together a false and a true bit
		Bit test = new Bit(false);
		assertTrue(test.or(new Bit(true)).getValue());
		//or together a true and a true bit
		test.set();
		assertTrue(test.or(new Bit(true)).getValue());
		//or together a false and a false bit
		test.clear();
		assertFalse(test.or(new Bit(false)).getValue());
	}

	@Test
	public void BitXor() throws Exception{
		//tests Bit.xor()
		//xor a true and a false
		Bit test = new Bit(true);
		assertTrue(test.xor(new Bit(false)).getValue());
		//xor a true and true bit
		assertFalse(test.xor(new Bit(true)).getValue());
		//xor a false and false bit
		test.clear();
		assertFalse(test.xor(new Bit(false)).getValue());
	}

	@Test
	public void BitNot() throws Exception{
		//tests Bit.not()
		//test not on true
		Bit test = new Bit(true);
		assertFalse(test.not().getValue());
		//test not on false
		test.clear();
		assertTrue(test.not().getValue());
	}


//-----------------------------------------[WORD TESTS]------------------------------------------------------


	@Test
	public void WordToString() throws Exception{
		Word test = new Word();
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", 
			test.toString());	
		test.setBit(5, new Bit(true));
		assertEquals("f,f,f,f,f,t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", 
			test.toString());
	}
	
	@Test
	public void WordAnd() throws Exception{
		//tests Word.and()
		//and(MAX INT and -123)
		Word maxint = new Word();
		maxint.set(2147483647);
		Word onetwothree = new Word();
		onetwothree.set(-123);
		assertEquals("f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f,f,f,t,f,t", maxint.and(onetwothree).toString());
		//and(MAX INT and 0)
		Word zero = new Word();
		zero.set(0);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", maxint.and(zero).toString());
		//and(MAX INT and -1)
		Word minusone = new Word();
		minusone.set(-1);
		assertEquals("f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.and(minusone).toString());
	}

	@Test
	public void WordOr() throws Exception{
		//tests Word.or()
		//or(MAX INT or -123)
		Word maxint = new Word();
		maxint.set(2147483647);
		Word onetwothree = new Word();
		onetwothree.set(-123);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.or(onetwothree).toString());
		//or(MAX INT or 0)
		Word zero = new Word();
		zero.set(0);
		assertEquals("f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.or(zero).toString());
		//or(MAX INT or -1)
		Word minusone = new Word();
		minusone.set(-1);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.or(minusone).toString());
			
	}

	@Test
	public void WordXor() throws Exception{
		//tests Word.xor()
		//xor(MAX INT xor -123)
		Word maxint = new Word();
		maxint.set(2147483647);
		Word onetwothree = new Word();
		onetwothree.set(-123);
		assertEquals("t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t,f,t,f", maxint.xor(onetwothree).toString());
		//xor(MAX INT xor 0)
		Word zero = new Word();
		zero.set(0);
		assertEquals("f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.xor(zero).toString());
		//xor(MAX INT xor -1)
		Word minusone = new Word();
		minusone.set(-1);
		assertEquals("t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", maxint.xor(minusone).toString());

	}

	@Test
	public void WordNot() throws Exception{
		//tests Word.not()
		//not(MAX INT)
		Word maxint = new Word();
		maxint.set(2147483647);
		assertEquals("t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", maxint.not().toString());
		//not(-123)
		Word onetwothree = new Word();
		onetwothree.set(-123);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t,f,t,f", onetwothree.not().toString());
		//not(1)
		Word one = new Word();
		one.set(1);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f", one.not().toString());
	}

	@Test 
	public void WordRShift() throws Exception{
		//tests Word.rightShift()
		//rightShift MAX INT 1
		Word maxint = new Word();
		maxint.set(2147483647);
		assertEquals("f,f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.rightShift(1).toString());
		//rightShift MAX INT 10
		assertEquals("f,f,f,f,f,f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", maxint.rightShift(5).toString());

	}

	@Test 
	public void WordLeftShift() throws Exception{
		//tests Word.leftShift()
		//leftShift MAX INT 1
		Word maxint = new Word();
		maxint.set(2147483647);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f", maxint.leftShift(1).toString());
		//leftShift MAX INT 10
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f,f,f,f,f,f,f,f,f", maxint.leftShift(10).toString());
	}

	@Test
	public void WordUnsigned() throws Exception{
		//tests Word.getUnsigned()
		Word test = new Word();
		test.set(123);
		assertEquals(123, test.getUnsigned());

		test.set(500000000);
		assertEquals(500000000, test.getUnsigned());

		test.set(2147483647);
		assertEquals(2147483647, test.getUnsigned());

		test.set(0);
		assertEquals(0, test.getUnsigned());

		test.set(1);
		assertEquals(1, test.getUnsigned());
		//getUnsigned on maximum 32 bit unsigned value
		for(int i = 0; i < 32; i++){
			test.setBit(i, new Bit(true));
		}
		assertEquals(4294967295L, test.getUnsigned());
	}
	
	@Test
	public void WordSigned() throws Exception{
		//tests Word.getSigned()
		Word test = new Word();
		test.set(123);
		assertEquals(123, test.getSigned());

		test.set(-123);
		assertEquals(-123, test.getSigned());

		test.set(500000000);
		assertEquals(500000000, test.getSigned());

		test.set(-500000000);
		assertEquals(-500000000, test.getSigned());

		test.set(2147483647);
		assertEquals(2147483647, test.getSigned());

		test.set(-2147483647);
		assertEquals(-2147483647, test.getSigned());

		test.set(0);
		assertEquals(0, test.getSigned());

		test.set(1);
		assertEquals(1, test.getSigned());

		test.set(-1);
		assertEquals(-1, test.getSigned());
	}

	@Test
	public void WordSet() throws Exception{
		//tests Word.set()
		Word test = new Word();
		//set to 123 and -123
		test.set(123);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t,t,t,t,f,t,t", test.toString());
		test.set(-123);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,f,f,f,f,t,f,t", test.toString());	
		//set to 500000000 and -500000000
		test.set(500000000);
		assertEquals("f,f,f,t,t,t,f,t,t,t,f,f,t,t,f,t,f,t,t,f,f,t,f,t,f,f,f,f,f,f,f,f", test.toString());
		test.set(-500000000);
		assertEquals("t,t,t,f,f,f,t,f,f,f,t,t,f,f,t,f,t,f,f,t,t,f,t,t,f,f,f,f,f,f,f,f", test.toString());
		//set to MAX INT and MIN INT
		test.set(2147483647);
		assertEquals("f,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", test.toString());
		test.set(-2147483647);
		assertEquals("t,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t", test.toString());
		//set to 0, 1, -1
		test.set(0);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f", test.toString());
		test.set(1);
		assertEquals("f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,f,t", test.toString());
		test.set(-1);
		assertEquals("t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t,t", test.toString());
	}

}
