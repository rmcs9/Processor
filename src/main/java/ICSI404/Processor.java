package ICSI404;


public class Processor{
	private Word PC;
	private Word SP;
	private Word currentInstruction;
	private Bit isHalted;

	public Processor(){
		PC = new Word();
		SP = new Word();
		SP.set(1024);
		isHalted = new Bit(false);
		currentInstruction = new Word();
		run();
	}

	private void run(){
		while(isHalted.not().getValue()){
			fetch();
			decode();
			execute();
			store();
		}
	}

	private void fetch(){
		//set the current intruction
		currentInstruction = MainMemory.read(PC);		
		//advance the program counter
		PC = PC.increment();
	}

	private void decode(){

	}

	private void execute(){

	}

	private void store(){

	}
}
