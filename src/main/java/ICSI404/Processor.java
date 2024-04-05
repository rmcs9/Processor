package ICSI404;


public class Processor{
	private Word PC;
	//USED ONLY FOR TESTING PURPOSES
	public Word getPC(){
		return PC;
	}
	private Word SP;
	private Word currentInstruction;
	private Bit isHalted;
	//public for testing
	public Word[] registers;

	private ALU alu;

	private Word opCode;
	private Word rd;
	private Word func;
	//NOTE: this is used primarily for debug printing
	private Word rs2;
	private Word rs2val;
	//NOTE: this is used primarily for debug printing
	private Word rs1;
	private Word rs1val;
	private Word immediate;


	//MASKS
	private Word fiveBitMask;
	private Word functionMask;
	private Word immediateMask;


	public Processor(){
		PC = new Word();
		SP = new Word();
		SP.set(1024);
		isHalted = new Bit(false);
		currentInstruction = new Word();

		//initialize registers
		registers = new Word[32];
		for(int i = 0; i < 32; i++){
			registers[i] = new Word();
		}

		//ALU DECLARATION
		alu = new ALU();

		//MASK DECLARATIONS
		fiveBitMask = new Word();
		for(int i = 27; i < 32; i++){
			fiveBitMask.setBit(i, new Bit(true));
		}
		functionMask = new Word();
		for(int i = 18; i < 22; i++){
			functionMask.setBit(i, new Bit(true));
		}
		immediateMask = new Word();
		for(int i = 0; i < 27; i++){
			immediateMask.setBit(i, new Bit(true));
		}
	}

	public void run(){
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
		//reset all of the decoding storage
		opCode = new Word();
		rd = new Word();
		func = new Word();
		rs2 = new Word();
		rs2val = new Word();
		rs1 = new Word();
		rs1val = new Word();
		immediate = new Word();

		//determine what type of instruction is present
		Bit threeR = currentInstruction.getBit(30).and(currentInstruction.getBit(31).not());
		Bit twoR = currentInstruction.getBit(30).and(currentInstruction.getBit(31));
		Bit destOnly = currentInstruction.getBit(30).not().and(currentInstruction.getBit(31));
		Bit noR = currentInstruction.getBit(30).not().and(currentInstruction.getBit(31).not()); 
		//3r
		if(threeR.getValue()){
			//set the opcode
			opCode = currentInstruction.and(fiveBitMask);					
			//set the index for the destination register
			rd = currentInstruction.rightShift(5).and(fiveBitMask);
			//set the function code
			func = currentInstruction.and(functionMask).rightShift(10);
			//find the index for rs2
			rs2 = currentInstruction.rightShift(14).and(fiveBitMask);
			//calculate the index as an int
			//grab the value of the register from the register array
			rs2val.copy(registers[findRegIndex(rs2)]);
			//find the index of rs1
			rs1 = currentInstruction.rightShift(19).and(fiveBitMask);
			//calculate the index of rs1 as an int
			//grab the value of the register from the register array
			rs1val.copy(registers[findRegIndex(rs1)]);
			//set the immediate value
			immediate = currentInstruction.and(immediateMask).rightShift(24);
		}
		//2r
		else if(twoR.getValue()){
			//set the opcode
			opCode = currentInstruction.and(fiveBitMask);
			//set the destination register
			rd = currentInstruction.rightShift(5).and(fiveBitMask);
			//set the function code
			func = currentInstruction.and(functionMask).rightShift(10);
			// NOTE: when using only one source register, default to r2...
			rs2 = currentInstruction.rightShift(14).and(fiveBitMask);
			//find the index of rs2 as an int
			//grab the value of the register from the register array
			rs2val.copy(registers[findRegIndex(rs2)]);
			//set the immediate value
			immediate = currentInstruction.and(immediateMask).rightShift(19);
		}
		//Dest only (1r)
		else if(destOnly.getValue()){
			//set the opcode
			opCode = currentInstruction.and(fiveBitMask);
			//set the destination register
			rd = currentInstruction.rightShift(5).and(fiveBitMask);
			//set the function code
			func = currentInstruction.and(functionMask).rightShift(10);
			//set the immediate value
			immediate = currentInstruction.and(immediateMask).rightShift(14);
		}
		//no r (0r)
		else if(noR.getValue()){
			//set the opcode
			opCode = currentInstruction.and(fiveBitMask);
			//set the immediate value
			immediate = currentInstruction.and(immediateMask).rightShift(5);
		}
	}

	private void execute(){		
		//add code for the alu... used in many execution operations. placed here for ease of use
		Bit[] add = {
			new Bit(true),
			new Bit(true), 
			new Bit(true),
			new Bit(false),
		};
		//MATH 000
		if(opCode.getBit(27).not().and(opCode.getBit(28).not()).and(opCode.getBit(29).not()).getValue()){
			//MATH 3R OPERATION
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				//pass operands to the ALU
				alu.op1.copy(rs1val);
				alu.op2.copy(rs2val);
				//set the function code
				Bit functionCode[] = new Bit[4];
				functionCode[0] = func.getBit(28);
				functionCode[1] = func.getBit(29);
				functionCode[2] = func.getBit(30);
				functionCode[3] = func.getBit(31);
				//call the alu to perform the math op
				alu.doOperation(functionCode);
			}
			//MATH 2R OPERATION
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				//find the value for rd
				Word rdVal = new Word();
				rdVal.copy(registers[findRegIndex(rd)]);
				//pass the operands to the ALU
				alu.op1.copy(rdVal);
				alu.op2.copy(rs2val);
				//set the function code
				Bit functionCode[] = new Bit[4];
				functionCode[0] = func.getBit(28);
				functionCode[1] = func.getBit(29);
				functionCode[2] = func.getBit(30);
				functionCode[3] = func.getBit(31);
				//call the alu to perform the math op
				alu.doOperation(functionCode);
			}
			//MATH DESTINATION ONLY OPERATION (COPY)
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				//store the value to be copied in the result word of the alu
				alu.res.copy(immediate);
			}
			//MATH NO REGISTER OPERATION (HALT)
			else{
				//set the halt bit
				isHalted.set();	
			}
		}
		//BRANCH 001
		else if(opCode.getBit(27).not().and(opCode.getBit(28).not()).and(opCode.getBit(29)).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				//copy rs1 and rs2 into the alu
				alu.op1.copy(registers[findRegIndex(rs1)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				//set the alu's operation code according to the instruction
				Bit[] boolOP = new Bit[4];
				boolOP[0] = func.getBit(28);
				boolOP[1] = func.getBit(29);
				boolOP[2] = func.getBit(30);
				boolOP[3] = func.getBit(31);
				alu.doOperation(boolOP);
				//if the boolean operation succeedes...
				if(alu.res.getBit(0).getValue()){
					//copy the PC and immediate into the alu
					alu.op1.copy(PC);
					alu.op2.copy(immediate);
					//create an alu code for adding
					alu.doOperation(add);
				}
				//if the boolean op fails...
				else{
					//copy the PC into the alu's result word
					alu.res.copy(PC);
				}
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				//copy rs2 and rd into the alu
				alu.op1.copy(registers[findRegIndex(rs2)]);
				alu.op2.copy(registers[findRegIndex(rd)]);
				//create the boolOP according to the instruction
				Bit[] boolOP = new Bit[4];
				boolOP[0] = func.getBit(28);
				boolOP[1] = func.getBit(29);
				boolOP[2] = func.getBit(30);
				boolOP[3] = func.getBit(31);
				alu.doOperation(boolOP);
				//if the boolean op succeedes...
				if(alu.res.getBit(0).getValue()){
					//copy the PC and immediate into the alu
					alu.op1.copy(PC);
					alu.op2.copy(immediate);
					alu.doOperation(add);
				}
				//if the boolean op does not succeed...
				else{
					//copy the PC into the alu's result word
					alu.res.copy(PC);
				}

			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				//copy the PC and immediate into the alu
				alu.op1.copy(PC);
				alu.op2.copy(immediate);
				//add PC + immediate
				alu.doOperation(add);
			}
			//NO R
			//NO MATH EXECUTION REQUIRED	
		}
		//CALL 010
		else if(opCode.getBit(27).not().and(opCode.getBit(28)).and(opCode.getBit(29).not()).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				//copy rs1 and rs2 into the alu
				alu.op1.copy(registers[findRegIndex(rs1)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				Bit[] boolOP = new Bit[4];
				boolOP[0] = func.getBit(28);
				boolOP[1] = func.getBit(29);
				boolOP[2] = func.getBit(30);
				boolOP[3] = func.getBit(31);
				//perform the requested boolean operation according to the instruction
				alu.doOperation(boolOP);
				//if the bool op is true...
				if(alu.res.getBit(0).getValue()){
					//push the PC onto the stack
					push(PC);
					//add rd + imm
					alu.op1.copy(registers[findRegIndex(rd)]);
					alu.op2.copy(immediate);
					alu.doOperation(add);
				}
				//if the bool op is false
				else{
					//copy the PC into the alu's result word
					alu.res.copy(PC);
				}
				
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				//cpy rs and rd to the alu
				alu.op1.copy(registers[findRegIndex(rs2)]);
				alu.op2.copy(registers[findRegIndex(rd)]);
				Bit[] boolOP = new Bit[4];
				boolOP[0] = func.getBit(28);
				boolOP[1] = func.getBit(29);
				boolOP[2] = func.getBit(30);
				boolOP[3] = func.getBit(31);
				//perform the requested boolean operation according to the instruction
				alu.doOperation(boolOP);
				//if the bool op is true
				if(alu.res.getBit(0).getValue()){
					//push PC onto the stack
					push(PC);
					//add PC + imm in the alu
					alu.op1.copy(PC);
					alu.op2.copy(immediate);
					alu.doOperation(add);
				}
				//if the bool op is false
				else{
					//copy the PC into the alu's result word
					alu.res.copy(PC);
				}
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				//push PC onto the stack
				push(PC);
				//add rd + imm
				alu.op1.copy(registers[findRegIndex(rd)]);
				alu.op2.copy(immediate);
				alu.doOperation(add);
			}
			//NO R
			else{
				//push PC onto the stack
				push(PC);
			}
		}
		//PUSH 011
		else if(opCode.getBit(27).not().and(opCode.getBit(28)).and(opCode.getBit(29)).getValue()){
			//for all of these, add the necessary components of the calculation to the alu, 
			//and perform the necessary math operation according the instruction
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				alu.op1.copy(registers[findRegIndex(rs1)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				Bit[] mathOP = new Bit[4];
				mathOP[0] = func.getBit(28);
				mathOP[1] = func.getBit(29);
				mathOP[2] = func.getBit(30);
				mathOP[3] = func.getBit(31);
				alu.doOperation(mathOP);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				alu.op1.copy(registers[findRegIndex(rd)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				Bit[] mathOP = new Bit[4];
				mathOP[0] = func.getBit(28);
				mathOP[1] = func.getBit(29);
				mathOP[2] = func.getBit(30);
				mathOP[3] = func.getBit(31);
				alu.doOperation(mathOP);
	
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				alu.op1.copy(registers[findRegIndex(rd)]);	
				alu.op2.copy(immediate);
				Bit[] mathOP = new Bit[4];
				mathOP[0] = func.getBit(28);
				mathOP[1] = func.getBit(29);
				mathOP[2] = func.getBit(30);
				mathOP[3] = func.getBit(31);
				alu.doOperation(mathOP);
			}
			//NO R
			//UNUSED
		}
		//LOAD 100
		else if(opCode.getBit(27).and(opCode.getBit(28).not()).and(opCode.getBit(29).not()).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				alu.op1.copy(registers[findRegIndex(rs1)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				alu.doOperation(add);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				alu.op1.copy(registers[findRegIndex(rs2)]);
				alu.op2.copy(immediate);
				alu.doOperation(add);
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				alu.op1.copy(registers[findRegIndex(rd)]);
				alu.op2.copy(immediate);
				alu.doOperation(add);
			}
			//NO R
			//NO MATH EXECUTION REQUIRED
		}
		//STORE 101
		else if(opCode.getBit(27).and(opCode.getBit(28).not()).and(opCode.getBit(29)).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				alu.op1.copy(registers[findRegIndex(rd)]);
				alu.op2.copy(registers[findRegIndex(rs1)]);
				alu.doOperation(add);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				alu.op1.copy(registers[findRegIndex(rd)]);
				alu.op2.copy(immediate);
				alu.doOperation(add);
			}
			//DEST ONLY
			//NO MATH EXECUTION REQUIRED
			
			//NO R
			//UNUSED	
		}
		//POP/INTERRUPT 110
		else if(opCode.getBit(27).and(opCode.getBit(28)).and(opCode.getBit(29).not()).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				//add rs1 and rs2 to the alu and add them
				alu.op1.copy(registers[findRegIndex(rs1)]);
				alu.op2.copy(registers[findRegIndex(rs2)]);
				alu.doOperation(add);
				//then add the result of rs1 + rs2 onto the stack pointer
				alu.op1.copy(SP);
				alu.op2.copy(alu.res);
				alu.doOperation(add);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				//add rs and imm to the alu and add them
				alu.op1.copy(registers[findRegIndex(rs2)]);
				alu.op2.copy(immediate);
				alu.doOperation(add);
				//then add the result of rs + imm onto the stack pointer
				alu.op1.copy(SP);
				alu.op2.copy(alu.res);
				alu.doOperation(add);
			}
			//DEST ONLY
			//NO MATH EXECUTION REQUIRED

			//NO R
			//UNUSED
		}
	}

	private void store(){
		//MATH STORES 000
		if(opCode.getBit(27).not().and(opCode.getBit(28).not()).and(opCode.getBit(29).not()).getValue()){
			//determine if a store has to be performed
			//a store has to be performed if the rd word does not 
			// evaluate to 0
			// the rd word will be zero in the following cases:
			// 1. when a HALT or COPY is performed (rd is never decoded)
			// 2. when you are attempting to write a value to register 0 (NOT ALLOWED)
			Bit validStore = new Bit(false);
			for(int i = 31; i > 26; i--){
				if(rd.getBit(i).getValue()){
					validStore.set();
				}
			}
			if(validStore.getValue()){
				//find the index to store the value at
				//store the value
				registers[findRegIndex(rd)].copy(alu.res);
			}
			//DEBUG INFORMATION:
			String operation;
			String regvals;
			switch(opCode.getSigned()){
				case 2:
					operation = "MATH 3R ";
					regvals = "r" + rs1.getSigned() + " val: " + rs1val.getSigned() + ", " + 
						"r" + rs2.getSigned() + " val: " + rs2val.getSigned() + ", " + "rd: " + rd.getSigned() +
						" rd value: " + registers[rd.getSigned()].getSigned();
						System.out.println(operation + regvals);
				break;
				case 3:
					operation = "MATH 2R ";
					regvals = "r" + rs2.getSigned() + " val: " + rs2val.getSigned() + ", " +
						"rd: " + rd.getSigned() + " rdval: " + registers[rd.getSigned()].getSigned();
					System.out.println(operation + regvals);
				break;
				case 1:
					operation = "MATH DEST ONLY ";
					regvals = "imm: " + immediate.getSigned() + ", rd: " + rd.getSigned() + " val: " + 
							registers[rd.getSigned()].getSigned();
					System.out.println(operation + regvals);
				break;
				case 0:
					operation = "MATH HALT";
				break;
			}
		}
		//BRANCH STORES 001
		else if(opCode.getBit(27).not().and(opCode.getBit(28).not()).and(opCode.getBit(29)).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				PC.copy(alu.res);	
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				PC.copy(alu.res);
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				PC.copy(alu.res);
			}
			//NO R
			else{
				PC.copy(immediate);	
			}
		}
		//CALL STORES 010
		else if(opCode.getBit(27).not().and(opCode.getBit(28)).and(opCode.getBit(29).not()).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				PC.copy(alu.res);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				PC.copy(alu.res);
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				PC.copy(alu.res);
			}
			//NO R
			else{
				PC.copy(immediate);
			}
		}
		//PUSH STORES 011
		else if(opCode.getBit(27).not().and(opCode.getBit(28)).and(opCode.getBit(29)).getValue()){
			//3R, 2R, 1R
			push(alu.res);
			//NO R
			//UNUSED
		}
		//LOAD STORES 100
		else if(opCode.getBit(27).and(opCode.getBit(28).not()).and(opCode.getBit(29).not()).getValue()){
			//NO R
			if(opCode.getBit(30).not().and(opCode.getBit(31).not()).getValue()){
				PC.copy(pop());
			}
			//3R, 2R, 1R
			else{
				registers[findRegIndex(rd)].copy(MainMemory.read(alu.res));
			}
		}
		//STORE STORES 101
		else if(opCode.getBit(27).and(opCode.getBit(28).not()).and(opCode.getBit(29)).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				MainMemory.write(alu.res, registers[findRegIndex(rs2)]);
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				MainMemory.write(alu.res, registers[findRegIndex(rs2)]);
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				MainMemory.write(registers[findRegIndex(rd)], immediate);
			}
			//NO R
			//UNUSED
		}
		//POP/INTERRUPT STORES 110
		else if(opCode.getBit(27).and(opCode.getBit(28)).and(opCode.getBit(29).not()).getValue()){
			//3R
			if(opCode.getBit(30).and(opCode.getBit(31).not()).getValue()){
				registers[findRegIndex(rd)].copy(MainMemory.read(alu.res));
			}
			//2R
			else if(opCode.getBit(30).and(opCode.getBit(31)).getValue()){
				registers[findRegIndex(rd)].copy(MainMemory.read(alu.res));
			}
			//DEST ONLY
			else if(opCode.getBit(30).not().and(opCode.getBit(31)).getValue()){
				registers[findRegIndex(rd)].copy(pop());
			}
			//NO R
			//UNUSED
		}

	}

	private void push(Word val){
		//decrement the stack pointer
		SP = SP.decrement();
		//write the requested word at the new top of the stack
		MainMemory.write(SP, val);
	}

	private Word pop(){
		//read the word at the top of the stack
		Word pop = MainMemory.read(SP);
		//erase what was on top of the stack
		MainMemory.write(SP, new Word());
		//increment the stack pointer
		SP.increment();
		//return what was on top of the stack
		return pop;
	}

	//my solution to not being able to use getSigned()
	private int findRegIndex(Word w){
		// >= 16
		if(w.getBit(27).getValue()){
			// >= 24
			if(w.getBit(28).getValue()) {
				// >= 28
				if(w.getBit(29).getValue()){
					// >= 30
					if(w.getBit(30).getValue()){
						//31
						if(w.getBit(31).getValue()){
							return 31;
						}
						//30
						else{
							return 30;
						}
					}
					// < 30
					else{
						//29
						if(w.getBit(31).getValue()){
							return 29;
						}
						//28
						else{
							return 28;
						}
					}
				}
				// < 28
				else{
					// >= 26
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 27;
						}
						else{
							return 26;
						}
					}
					// < 26
					else{
						if(w.getBit(31).getValue()){
							return 25;
						}
						else{
							return 24;
						}
					}
				}
			}
			// < 24
			else{
				// >= 20
				if(w.getBit(29).getValue()){
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 23;
						}
						else{
							return 22;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 21;
						}
						else{
							return 20;
						}
					}
				}
				// < 20
				else{
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 19;
						}
						else{
							return 18;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 17;
						}
						else{
							return 16;
						}
					}	
				}
			}
		}
		// < 16
		else{
			// >= 8
			if(w.getBit(28).getValue()){
				// >= 12
				if(w.getBit(29).getValue()){
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 15;
						}
						else{
							return 14;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 13;
						}
						else{
							return 12;
						}
					}
				}
				// < 12
				else{
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 11;
						}
						else{
							return 10;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 9;
						}
						else{
							return 8;
						}
							
					}
				}
			}
			// < 8
			else{
				// >= 4
				if(w.getBit(29).getValue()){
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 7;
						}
						else{
							return 6;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 5;
						}
						else{
							return 4;
						}
					}
				}
				// < 4
				else{
					if(w.getBit(30).getValue()){
						if(w.getBit(31).getValue()){
							return 3;
						}
						else{
							return 2;
						}
					}
					else{
						if(w.getBit(31).getValue()){
							return 1;
						}
						else{
							return 0;
						}
					}
				}
			}
		}
	}
}
