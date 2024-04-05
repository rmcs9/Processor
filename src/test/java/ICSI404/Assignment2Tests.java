
package ICSI404;

import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment2Tests{
	//testing the full bit adder
	@Test
	public void FullAdderTest(){
		ALU alu = new ALU();
		Bit[] res;

		res = alu.fAdder(new Bit(false), new Bit(false), new Bit(false));
		assertFalse(res[0].getValue());
		assertFalse(res[1].getValue());

		res = alu.fAdder(new Bit(false), new Bit(false), new Bit(true));
		assertTrue(res[0].getValue());
		assertFalse(res[1].getValue());

		res = alu.fAdder(new Bit(false), new Bit(true), new Bit(false));
		assertTrue(res[0].getValue());
		assertFalse(res[1].getValue());

		res = alu.fAdder(new Bit(false), new Bit(true), new Bit(true));
		assertFalse(res[0].getValue());
		assertTrue(res[1].getValue());

		res = alu.fAdder(new Bit(true), new Bit(false), new Bit(false));
		assertTrue(res[0].getValue());
		assertFalse(res[1].getValue());
	
		res = alu.fAdder(new Bit(true), new Bit(false), new Bit(true));
		assertFalse(res[0].getValue());
		assertTrue(res[1].getValue());
	
		res = alu.fAdder(new Bit(true), new Bit(true), new Bit(false));
		assertFalse(res[0].getValue());
		assertTrue(res[1].getValue());

		res = alu.fAdder(new Bit(true), new Bit(true), new Bit(true));
		assertTrue(res[0].getValue());
		assertTrue(res[1].getValue());
	}
	
	@Test
	public void addTest(){
		Bit[] addOp = {new Bit(true), new Bit(true), new Bit(true), new Bit(false)};
		ALU alu = new ALU();

		alu.op1.set(100);
		alu.op2.set(100);
		alu.doOperation(addOp);
		assertEquals(200, alu.res.getSigned());

		alu.op1.set(6);
		alu.op2.set(4);
		alu.doOperation(addOp);
		assertEquals(10, alu.res.getSigned());

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(addOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(-1);
		alu.op2.set(-1);
		alu.doOperation(addOp);
		assertEquals(-2, alu.res.getSigned());

		alu.op1.set(-25);
		alu.op2.set(26);
		alu.doOperation(addOp);
		assertEquals(1, alu.res.getSigned());
	
		alu.op1.set(-25);
		alu.op2.set(-26);
		alu.doOperation(addOp);
		assertEquals(-51, alu.res.getSigned());

		alu.op1.set(2147483646);
		alu.op2.set(1);
		alu.doOperation(addOp);
		assertEquals(2147483647, alu.res.getSigned());

		alu.op1.set(-2147483646);
		alu.op2.set(-1);
		alu.doOperation(addOp);
		assertEquals(-2147483647, alu.res.getSigned());

		alu.op1.set(1147483646);
		alu.op2.set(999999);
		alu.doOperation(addOp);
		assertEquals(1148483645, alu.res.getSigned());

		alu.op1.set(25);
		alu.op2.set(-26);
		alu.doOperation(addOp);
		assertEquals(-1, alu.res.getSigned());

		alu.op1.set(100);
		alu.op2.set(-100);
		alu.doOperation(addOp);
		assertEquals(0, alu.res.getSigned());
	}

	@Test
	public void add4Test(){
		ALU alu = new ALU();

		Word result = new Word();
		Word w1 = new Word();
		Word w2 = new Word();
		Word w3 = new Word();
		Word w4 = new Word();

		w1.set(5);
		w2.set(10);
		w3.set(20);
		w4.set(40);
		result.copy(alu.add4(w1, w2, w3, w4));
		assertEquals(75, result.getSigned());

		w1.set(100000);
		w2.set(-50000);
		w3.set(-30000);
		w4.set(-20000);
		result.copy(alu.add4(w1, w2, w3, w4));
		assertEquals(0, result.getSigned());

		w1.set(2000000000);
		w2.set(100000000);
		w3.set(40000000);
		w4.set(7483647);
		result.copy(alu.add4(w1, w2, w3, w4));
		assertEquals(2147483647, result.getSigned());

		w1.set(-2000000000);
		w2.set(-100000000);
		w3.set(-40000000);
		w4.set(-7483647);
		result.copy(alu.add4(w1, w2, w3, w4));
		assertEquals(-2147483647, result.getSigned());
	}
	
	@Test
	public void subtractTest(){
		Bit[] subOp = {new Bit(true), new Bit(true), new Bit(true), new Bit(true)};
		ALU alu = new ALU();

		alu.op1.set(100);
		alu.op2.set(100);
		alu.doOperation(subOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(101);
		alu.op2.set(90);
		alu.doOperation(subOp);
		assertEquals(11, alu.res.getSigned());

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(subOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(-12);
		alu.op2.set(-13);
		alu.doOperation(subOp);
		assertEquals(1, alu.res.getSigned());

		alu.op1.set(8902435);
		alu.op2.set(-999999);
		alu.doOperation(subOp);
		assertEquals(9902434, alu.res.getSigned());

		alu.op1.set(2147483647);
		alu.op2.set(2147483647);
		alu.doOperation(subOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(2147483647);
		alu.op2.set(147483647);
		alu.doOperation(subOp);
		assertEquals(2000000000, alu.res.getSigned());

		alu.op1.set(2147483646);
		alu.op2.set(-1);
		alu.doOperation(subOp);
		assertEquals(2147483647, alu.res.getSigned());

		alu.op1.set(2000000);
		alu.op2.set(0);
		alu.doOperation(subOp);
		assertEquals(2000000, alu.res.getSigned());
	}

	@Test
	public void multiplyTests(){
		
		ALU alu = new ALU();
		Bit[] multOp = {new Bit(false), new Bit(true), new Bit(true), new Bit(true)};


		alu.op1.set(10);
		alu.op2.set(10);
		alu.doOperation(multOp);
		assertEquals(100, alu.res.getSigned());

		alu.op1.set(2147483647);
		alu.op2.set(1);
		alu.doOperation(multOp);
		assertEquals(2147483647, alu.res.getSigned());

		alu.op1.set(0);
		alu.op2.set(0);
		alu.doOperation(multOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(2147483647);
		alu.op2.set(0);
		alu.doOperation(multOp);
		assertEquals(0, alu.res.getSigned());

		alu.op1.set(214890);
		alu.op2.set(10);
		alu.doOperation(multOp);
		assertEquals(2148900, alu.res.getSigned());

		alu.op1.set(333333);
		alu.op2.set(33);
		alu.doOperation(multOp);
		assertEquals(10999989, alu.res.getSigned());

		alu.op1.set(333333);
		alu.op2.set(-33);
		alu.doOperation(multOp);
		assertEquals(-10999989, alu.res.getSigned());

		alu.op1.set(-23456);
		alu.op2.set(-789);
		alu.doOperation(multOp);
		assertEquals(18506784, alu.res.getSigned());

		alu.op1.set(64);
		alu.op2.set(2);
		alu.doOperation(multOp);
		assertEquals(128, alu.res.getSigned());
	}
}
