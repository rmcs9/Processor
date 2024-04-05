package ICSI404;


public class ALU{
	
	public Word op1;

	public Word op2;

	public Word res;

	public ALU(){
		op1 = new Word();
		op2 = new Word();
		res = new Word();
	}

	public void doOperation(Bit[] op){
		
		//	1000 – and
		boolean isAnd = op[0].and(op[1].not().and(op[2].not().and(op[3].not()))).getValue();
		//	1001 – or
		boolean isOr = op[0].and(op[1].not().and(op[2].not().and(op[3]))).getValue();
		//	1010 – xor
		boolean isXor = op[0].and(op[1].not().and(op[2].and(op[3].not()))).getValue();
		//	1011 – not (not “op1”; ignore op2)
		boolean isNot = op[0].and(op[1].not().and(op[2].and(op[3]))).getValue();
		//	1100 – left shift 
		boolean isLeftShift = op[0].and(op[1].and(op[2].not().and(op[3].not()))).getValue();
		//	1101 – right shift 
		boolean isRightShift = op[0].and(op[1].and(op[2].not().and(op[3]))).getValue();
		//	1110 – add
		boolean isAdd = op[0].and(op[1].and(op[2].and(op[3].not()))).getValue();
		//	1111 – subtract
		boolean isSubtract = op[0].and(op[1].and(op[2].and(op[3]))).getValue();
		//	0111 - multiply
		boolean isMultiply = op[0].not().and(op[1].and(op[2].and(op[3]))).getValue();
		//  0000 - equals
		boolean isEquals = op[0].not().and(op[1].not()).and(op[2].not()).and(op[3].not()).getValue();
		//  0001 - not equals
		boolean isNotEquals = op[0].not().and(op[1].not()).and(op[2].not()).and(op[3]).getValue();
		//  0010 - less than
		boolean isLessThan = op[0].not().and(op[1].not()).and(op[2]).and(op[3].not()).getValue();
		//  0011 - greater than or equal to
		boolean isGTE = op[0].not().and(op[1].not()).and(op[2]).and(op[3]).getValue();
		//  0100 - greater than
		boolean isGreaterThan = op[0].not().and(op[1]).and(op[2].not()).and(op[3].not()).getValue();
		//  less than or equal to
		boolean isLTE = op[0].not().and(op[1]).and(op[2].not()).and(op[3]).getValue();
		
		Word copy1 = new Word(), copy2 = new Word();

		if(isAnd){
			res.copy(op1.and(op2));
		}
		else if(isOr){
			res.copy(op1.or(op2));
		}
		else if(isXor){
			res.copy(op1.xor(op2));
		}
		else if(isNot){
			res.copy(op1.not());
		}
		else if(isLeftShift){
			res.copy(op1);
			int shift = 0;
			for(int i = 31, j = 0; j < 5; i--, j++){
				if(op2.getBit(i).getValue()){
					shift += Math.pow(2, j);
				}
			}
			res.leftShift(shift);
		}
		else if(isRightShift){
			res.copy(op1);
			int shift = 0;
			for(int i = 31, j = 0; j < 5; i--, j++){
				if(op2.getBit(i).getValue()){
					shift += Math.pow(2, j);
				}
			}
			res.rightShift(shift);
		}
		else if(isAdd){
			copy1.copy(op1); copy2.copy(op2);
			res.copy(add(copy1, copy2));
		}
		else if(isSubtract){
			copy1.copy(op1); copy2.copy(op2);
			res.copy(subtract(copy1, copy2));
		}
		else if(isMultiply){
			copy1.copy(op1); copy2.copy(op2);
			res.copy(multiply(copy1, copy2));
		}
		else if(isEquals){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			Bit val = new Bit(true);
			for(int i = 0; i < 32; i++){
				if(subResult.getBit(i).getValue()){
					val.clear();
				}	
			}
			res.copy(new Word());
			res.setBit(0, val);
		}
		else if(isNotEquals){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			Bit val = new Bit(false);
			for(int i = 0; i < 32; i++){
				if(subResult.getBit(i).getValue()){
					val.set();
				}	
			}
			res.copy(new Word());
			res.setBit(0, val);

		}
		//< 
		else if(isLessThan){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			res.copy(new Word());
			res.setBit(0, subResult.getBit(0));
		}
		//>=
		else if(isGTE){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			res.copy(new Word());
			res.setBit(0, subResult.getBit(0).not());
		}
		//>
		else if(isGreaterThan){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			res.copy(new Word());
			if(subResult.getBit(0).not().getValue()){
				Bit val = new Bit(false);
				for(int i = 0; i < 32; i++){
					if(subResult.getBit(i).getValue()){
						val.set();
					}
				}
				res.setBit(0, val);
			}
			else{
				res.setBit(0, new Bit(false));
			}
		}
		//<=
		else if(isLTE){
			copy1.copy(op1); copy2.copy(op2);
			Word subResult = subtract(copy1, copy2);
			res.copy(new Word());
			if(subResult.getBit(0).not().getValue()){
				Bit val = new Bit(true);	
				for(int i = 0; i < 32; i++){
					if(subResult.getBit(i).getValue()){
						val.clear();
					}
				}
				res.setBit(0, val);
			}
			else{
				res.setBit(0, new Bit(true));
			}
		}
		else{
			throw new RuntimeException("invalid operation");
		}
	}

	public Word add(Word w1, Word w2){
		return add2(w1, w2);
	}

	private Word subtract(Word w1, Word w2){
		//negate w2
		w2 = w2.not();
		for(int i = 31; i >= 0; i--){
			if(w2.getBit(i).getValue()){
				w2.setBit(i, new Bit(false));
			}
			else{
				w2.setBit(i, new Bit(true));
				break;			
			}
		}
		//add w1 + -(w2)
		return add(w1, w2);
	}

	private Word multiply(Word w1, Word w2){
		Word[] multWords = new Word[32];
		//generate the 32 adds
		for(int i = 0, k = 31; i < 32; i++, k--){
			multWords[i] = new Word();
			for(int j = 31; j >= 0; j--){
				//set the bits in the add word by and-ing the bit in w2 with every bit in w1
				multWords[i].setBit(j, w2.getBit(k).and(w1.getBit(j)));
			}
			//shift to adjust for the 2's place
			multWords[i] = multWords[i].leftShift(i);
		}
		//round 1 of addition. uses 8 add4's to reduce adds down to 8
		Word[] round1 = new Word[8];
		for(int i = 0, j = 0; i < 32; i += 4, j++){
			round1[j] = add4(multWords[i], multWords[i+1], multWords[i+2], multWords[i+3]);
		}
		//round 2 of addition. uses 2 add4's to reduce adds down to 2
		Word[] round2 = new Word[2];
		round2[0] = add4(round1[0], round1[1], round1[2], round1[3]);
		round2[1] = add4(round1[4], round1[5], round1[6], round1[7]);
		//final round 3 of addition. uses 1 add 2 to add the remaining numbers from round 2
		return add2(round2[0], round2[1]);	
	}

	public Word add2(Word w1, Word w2){
		Word result = new Word();
		Bit carry = new Bit(false);
		for(int i = 31; i >= 0; i--){
			//add the bits from each word using the full adder
			Bit[] adds = fAdder(w1.getBit(31), w2.getBit(31), carry);
			//set the cooresponding bit in the result word
			result.setBit(i, new Bit(adds[0].getValue()));
			//obtain the new carry
			carry = new Bit(adds[1].getValue());
			//shift the operands to the right to obtain the next bits to be added
			w1 = w1.rightShift(1);
			w2 = w2.rightShift(1);
		}
		return result;
	}
	
	public Word add4(Word w1, Word w2, Word w3, Word w4){
		Word result = new Word();
		Word carries = new Word();
		for(int i = 31; i >= 0; i--){
			Bit b1 = w1.getBit(i), b2 = w2.getBit(i), b3 = w3.getBit(i), b4 = w4.getBit(i);
			//find the sum by xor all 4 bits plus all the carries
			Bit sum = b1.xor(b2).xor(b3).xor(b4);
			for(int j = 0; carries.getBit(j).getValue(); j++){
				sum = sum.xor(carries.getBit(j));
			}
			//set the resulting sum bit in the result word
			result.setBit(i, sum);	
			//count the amount of true bits that were present in this calculation
			int trueBits = b1.getValue() ? 1 : 0;
			trueBits = b2.getValue() ? trueBits + 1 : trueBits;
			trueBits = b3.getValue() ? trueBits + 1 : trueBits;
			trueBits = b4.getValue() ? trueBits + 1 : trueBits;
			for(int j = 0; carries.getBit(j).getValue(); j++){
				trueBits = carries.getBit(j).getValue() ? trueBits + 1 : trueBits;
			}
			carries = new Word();
			//the amount of carry outs can be determined by finding the number of true
			//bit pairs in the previous caclulation
			if(trueBits != 0){
				//set the new carry bits (which will be half of the true bits)
				for(int j = 0; j < trueBits / 2; j++){
					carries.setBit(j, new Bit(true));
				}
			}
		}
		return result;
	}

	//Full bit adder. Adds a bit from one word and a bit from a second word together
	public Bit[] fAdder(Bit x, Bit y, Bit cin){
		Bit[] ret = new Bit[2];
		//sum
		ret[0] = x.xor(y).xor(cin); 
		//cout
		ret[1] = x.and(y).or(x.xor(y).and(cin));
		return ret;
	}
}
