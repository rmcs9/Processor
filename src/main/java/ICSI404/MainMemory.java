package ICSI404;



public class MainMemory{
	
	private static Word[] mem = new Word[1024];

	public static Word read(Word addr){
		Word res = new Word();
		//get the address of the memory location to read from
		int ind = (int)addr.getUnsigned();
		//if the word at this address is not null...
		if(mem[ind] != null){
			//copy the word at the address into result
			res.copy(mem[ind]);
		}
		//return the result
		return res;
	}

	public static void write(Word addr, Word val){
		//get the address of where the write will occur
		int ind = (int)addr.getUnsigned();
		//initialize the word at the address
		mem[ind] = new Word();
		//copy val into the memory address
		mem[ind].copy(val);
	}

	public static void load(String[] data){
		//iterate through the provided data array
		for(int i = 0; i < data.length; i++){
			//initialize each memory address starting at 0
			mem[i] = new Word();
			//set the value of the word in memory
			for(int j = 0; j < 32; j++){
				//if data[i].charAt(j) == '1' create a new true bit
				//else create a new false bit
				mem[i].setBit(j, new Bit(data[i].charAt(j) == '1' ? true : false));
			}
		}
	} 
}

