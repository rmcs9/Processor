package ICSI404;



public class Word{
	//array of bits representing this word	
	private Bit[] word = new Bit[32];

	//retrives the value of the bit at position i
	public Bit getBit(int i){
		return new Bit(word[i].getValue());
	}
	
	//sets the value of the bit at position i, to the value of Bit value
	public void setBit(int i, Bit value){
		word[i] = new Bit(value.getValue());
	}

	//performs an and operation on this word and Word other
	//returns a new word containing the results
	public Word and(Word other){
		Word returnWord = new Word();
		
		for(int i = 0; i < 32; i++){
			returnWord.word[i] = word[i].and(other.word[i]);
		}

		return returnWord;
	}
	
	//performs an or operation on this word and Word other
	//returns a new word containing the results
	public Word or(Word other){
		Word returnWord = new Word();

		for(int i = 0; i < 32; i++){
			returnWord.word[i] = word[i].or(other.word[i]);
		}

		return returnWord;
	}

	//performs an xor operation on this word and Word other
	//returns a new word containing the results
	public Word xor(Word other){
		Word returnWord = new Word();

		for(int i = 0; i < 32; i++){
			returnWord.word[i] = word[i].xor(other.word[i]);
		}

		return returnWord;
	}
	
	//inverts the bits of this word and copies those values into a new Word to be returned
	public Word not(){
		Word returnWord = new Word();

		for(int i = 0; i < 32; i++){
			returnWord.word[i] = word[i].not();
		}

		return returnWord;
	}

	//right shifts a word by amount bits
	public Word rightShift(int amount){
		Word returnWord = new Word();
		for(int i = amount, j = 0; i < 32; i++, j++){
			returnWord.word[i] = new Bit(word[j].getValue());
		}

		return returnWord;
	}
	
	//left shifts a word by amount bits
	public Word leftShift(int amount){
		Word returnWord = new Word();
		for(int i = 0, j = amount; j < 32; i++, j++){
			returnWord.word[i] = new Bit(word[j].getValue());
		}

		return returnWord;
	}

	public Word increment(){
		Word result = new Word();
		result.copy(this);
		//set the initial carry to be true
		Bit carry = new Bit(true);
		//for every bit
		for(int i = 31; i >= 0; i--){
			Bit bit = result.getBit(i);
			//set the resulting bit to bit XOR carry
			result.setBit(i, bit.xor(carry));
			//set the carry out to bit AND carry
			carry = bit.and(carry);
		}
		//return the resulting word
		return result;
	}

	public Word decrement(){
		Word result = new Word();
		result.copy(this);
		for(int i = 31; i >= 0; i--){
			Bit bit = result.getBit(i);
			if(bit.not().getValue()){
				result.setBit(i, new Bit(true));
			}
			else{
				result.setBit(i, new Bit(false));
				return result;
			}
		}
		return result;
	}

	//toString method for word, prints words in the format: "f,t,f,t..."
	public String toString(){
		String wordstr = word[0].toString();
		for(int i = 1; i < 32; i++){
			wordstr += ",";
			wordstr += word[i].toString();
		}
		return wordstr;
	}
	
	//returns the unsigned representation of the current word
	public long getUnsigned(){
		long res = 0;
		
		for(int i = 31; i >= 0; i--){
			//if the current bits value is one,
			if(word[i].getValue()){
				//add 2^(bit position) onto the total
				res +=	Math.pow(2, 31 - i); 
			}
		}

		return res;
	}
	
	//returns the signed representation of the current word
	public int getSigned(){
		int res = 0;
		//if the first bit of the word is true (negative case)
		if(word[0].getValue()){
			for(int i = 31; i >= 1; i--){
				//since negative numbers have inverted bits,
				//not the current bit to obtain its real value
				if(word[i].not().getValue()){
					res -= Math.pow(2, 31 - i);
				}
			}
			//subtract one from the result to obtain the original value
			res--;
		}
		//positive case
		else{
			for(int i = 31; i >= 1; i--){
				if(word[i].getValue()){
					res += Math.pow(2, 31 - i);
				}
			}
		}
		return res;
	}
	
	//copy the value from another word into this one
	public void copy(Word other){
		for(int i = 0; i < 32; i++){
			word[i] = new Bit(other.word[i].getValue());
		}
	}
	
	//take a integer input and set the bits in this word to represent value
	public void set(int value){
		//determine if value is negative
		boolean negative = value < 0 ? true : false;
		int[] convert = new int[32]; 
		//convert value into its binary representation
		for(int i = 31; value != 0; i--, value /= 2){
			convert[i] = Math.abs(value % 2);
		}
		//read the binary representation into this bit
		for(int j = 31; j >= 0; j--){
			word[j] = convert[j] == 1 ? new Bit(true) : new Bit(false);
		}
	
		//if value is negative...
		if(negative){
			//invert the word
			for(int i = 0; i < 32; i++){
				word[i] = word[i].not();
			}
			//add one to the result
			for(int i = 31; i >= 0; i--){
				if(word[i].getValue()){
					word[i].clear();
				}
				else{
					word[i].set();
					break;
				}
			}
			//set the leftmost bit to true
			word[0] = new Bit(true);
		}
	}
	//my constructor for words, initializes a new Word to all false bits
	public Word(){
		for(int i = 0; i < 32; i++){
			word[i] = new Bit(false);
		}
	}

}
