package ICSI404;

import java.util.LinkedList;
import java.util.Optional;

public class Parser{

	public class TokenManager {

		private LinkedList<Token> tokens;

		public TokenManager(LinkedList<Token> list){
			tokens = list;
		}

		public Optional<Token> peek(int i) {
			if(this.moreTokens()){
				return Optional.of(tokens.get(i));
			}
			return Optional.empty();
		}

		public boolean moreTokens(){
			return !tokens.isEmpty();
		}

		public Optional<Token> matchAndRemove(Token.TokenType type){
			if(!this.moreTokens()){
				return Optional.empty();
			}
			if(type == tokens.get(0).type){
				return Optional.of(tokens.remove());
			}
			else{
				return Optional.empty();
			}
    	}
	}

	private TokenManager tokens;

	public Parser(LinkedList<Token> toks){
		tokens = new TokenManager(toks);
	}

	private boolean acceptSeparators() {
		if (tokens.moreTokens() && tokens.peek(0).get().type == Token.TokenType.SEPERATOR) {
			while (tokens.moreTokens() && tokens.peek(0).get().type == Token.TokenType.SEPERATOR) {
				tokens.matchAndRemove(Token.TokenType.SEPERATOR);
			}
			return true;
		} else {
			return false;
		}
    }


	public LinkedList<String> parse(){
		acceptSeparators();
		//list to hold output instructions
		LinkedList<String> instructions = new LinkedList<>();
		//while there are more tokens...
		while(tokens.moreTokens()){
			//parse the next instruction
			instructions.add(parseInstruction());
			//absorb new lines
			if(!acceptSeparators()){
				throw new RuntimeException("no line separation between statements");	
			}
		}
		return instructions;
	}

	private String immediate;
	private String rs1;
	private String rs2;
	private String function;
	private String rd;
	private String instructType;

	private boolean noR;
	private boolean oneR;
	private boolean twoR;
	private boolean threeR;

	private String parseInstruction(){
		//reset all fields
		immediate = "";
		rs1 = "";
		rs2 = "";
		function = "";
		rd = "";
		instructType = "";

		noR = false;
		oneR = false;
		twoR = false;
		threeR = false;
		
		//check for halt or return as they accept no parameters
		if(tokens.matchAndRemove(Token.TokenType.HALT).isPresent()){
			return "00000000000000000000000000000000";
		}
		if(tokens.matchAndRemove(Token.TokenType.RETURN).isPresent()){
			return "000000000000000000000000000" + "10000";
		}

		parseType();

		//based on the inferred instruction format, assemble an instruction string
		if(noR){
			return immediate + instructType + "00";
		}
		else if(oneR){
			return immediate + function + rd + instructType + "01";
		}
		else if(twoR){
			return immediate + rs2 + function + rd + instructType + "11";
		}
		else if(threeR){
			return immediate + rs1 + rs2 + function + rd + instructType + "10";	
		}
		else{
			throw new RuntimeException("invalid instruction format...");
		}

	}

	private void parseType(){
		Optional<Token> keyword = tokens.matchAndRemove(tokens.peek(0).get().type);
		//set the instruction type for the opcode
		switch(keyword.get().type){
			case MATH:
			case COPY:
				instructType = "000";
				break;
			case BRANCH:
			case JUMP:
				instructType = "001";
				break;
			case CALL:
				instructType = "010";
				break;
			case PUSH:
				instructType = "011";
				break;
			case PEEK:
			case POP:
				instructType = "110";
				break;
			case LOAD:
				instructType = "100";
				break;
			case STORE:
				instructType = "101";
				break;
			default:
				throw new RuntimeException("Invalid start to a statement: " + keyword.get().type);
		}
		//if the statement requires a math operation, attempt to parse it
		if(keyword.get().type == Token.TokenType.MATH || keyword.get().type == Token.TokenType.PUSH){
			parseMOP();	
		}
		//if the statement requires a boolean operation, attempt to aprse it
		else if(keyword.get().type == Token.TokenType.BRANCH){
			parseBOP();
		}
		//call has cases where BOP is required and forbidden (3R and 2R require BOP, 1R and 0R forbid)
		else if(keyword.get().type == Token.TokenType.CALL){
			if(tokens.peek(0).get().type != Token.TokenType.REGISTER && tokens.peek(0).get().type != Token.TokenType.NUMBER){
				parseBOP();
			}
			else{
				function = "0000";
			}
		}
		else{
			function = "0000";
		}
		//parse the parameters
		parseRegisters();
	}
	
	private void parseMOP(){
		switch(tokens.peek(0).get().type){
			case ADD:
				tokens.matchAndRemove(Token.TokenType.ADD);
				function = "1110";
				break;
			case SUBTRACT:
				tokens.matchAndRemove(Token.TokenType.SUBTRACT);
				function = "1111";
				break;
			case MULTIPLY:
				tokens.matchAndRemove(Token.TokenType.MULTIPLY);
				function = "0111";
				break;
			case AND:
				tokens.matchAndRemove(Token.TokenType.AND);
				function = "1000";
				break;
			case OR:
				tokens.matchAndRemove(Token.TokenType.OR);
				function = "1001";
				break;
			case NOT:
				tokens.matchAndRemove(Token.TokenType.NOT);
				function = "1011";
				break;
			case XOR:
				tokens.matchAndRemove(Token.TokenType.XOR);
				function = "1010";
				break;
			case SHIFTLEFT:
				tokens.matchAndRemove(Token.TokenType.SHIFTLEFT);
				function = "1100";
				break;
			case SHIFTRIGHT:
				tokens.matchAndRemove(Token.TokenType.SHIFTRIGHT);
				function = "1101";
				break;
			default:
				throw new RuntimeException("invalid keyword: " + tokens.peek(0).get().type + " found after MATH or PUSH." +
											" must provide a valid MOP.");
		}
	}

	private void parseBOP(){
		switch(tokens.peek(0).get().type) {
			case EQ:
				tokens.matchAndRemove(Token.TokenType.EQ);
				function = "0000";
				break;
			case NE:
				tokens.matchAndRemove(Token.TokenType.NE);
				function = "0001";
				break;
			case GT:
				tokens.matchAndRemove(Token.TokenType.GT);
				function = "0100";
				break;
			case LT: 
				tokens.matchAndRemove(Token.TokenType.LT);
				function = "0010";
				break;
			case GTE:
				tokens.matchAndRemove(Token.TokenType.GTE);
				function = "0011";
				break;
			case LTE:
				tokens.matchAndRemove(Token.TokenType.LTE);
				function = "0101";
				break;
			default:
				throw new RuntimeException("invalid keyword: " + tokens.peek(0).get().type + " found after BRANCH or CALL." 
											+ " please provide a valid BOP.");
		}
	}

	private void parseRegisters(){
		parse0R();	
	}

	private void parse0R(){
		//0R only expects an immediate, so look for it and set its value
		Optional<Token> imm = tokens.matchAndRemove(Token.TokenType.NUMBER);	
		if(imm.isPresent()){
			Word val = new Word();
			val.set(Integer.parseInt(imm.get().value));
			String binaryString = "";
			for(int i = 5; i < 32; i++){
				binaryString = 
					val.getBit(i).getValue() ? binaryString + "1" : binaryString + "0";
			}
			immediate = binaryString;
			noR = true;	
		}
		//if it cant be found, attempt to parse 1 register
		else{
			parse1R();	
		}	
	}

	private void parse1R(){
		//attempt to remove a register
		Optional<Token> reg1 = tokens.matchAndRemove(Token.TokenType.REGISTER);	
		//if it is present
		if(reg1.isPresent()){
			//parse its register number
			rd = getRegisterNumber(Integer.parseInt(reg1.get().value));		
			//look for an immediate value
			Optional<Token> imm = tokens.matchAndRemove(Token.TokenType.NUMBER);

			//if the immediate is present, set its value
			if(imm.isPresent()){
				Word val = new Word();
				val.set(Integer.parseInt(imm.get().value));
				String binaryString = "";
				for(int i = 14; i < 32; i++){
					binaryString = 
						val.getBit(i).getValue() ? binaryString + "1" : binaryString + "0";
				}
				immediate = binaryString;
				oneR = true;
			}
			//else look for more registers
			else{
				parse2R3R();
			}
		}
		else{
			throw new RuntimeException("failed to parse a register in an instruction that is not 0R");
		}
	}

	private void parse2R3R(){
		//attempt to parse another register
		Optional<Token> reg2 = tokens.matchAndRemove(Token.TokenType.REGISTER);
		//if it is present
		if(reg2.isPresent()){
			//attempt to match another register and look for immediate
			Optional<Token> reg3 = tokens.matchAndRemove(Token.TokenType.REGISTER);
			Optional<Token> imm = tokens.matchAndRemove(Token.TokenType.NUMBER);
			//if the third register is present, a 3R operation is required
			if(reg3.isPresent()){
				rs1 = getRegisterNumber(Integer.parseInt(reg2.get().value));
				rs2 = getRegisterNumber(Integer.parseInt(reg3.get().value));
				threeR = true;
				
				if(imm.isPresent()){
					Word val = new Word();
					val.set(Integer.parseInt(imm.get().value));
					String binaryString = "";
					for(int i = 24; i < 32; i++){
						binaryString = 
							val.getBit(i).getValue() ? binaryString + "1" : binaryString + "0";
					}
					immediate = binaryString;
				}
				else{
					immediate = "00000000";
				}
			}
			//else a 2R operation is present
			else{
				rs2 = getRegisterNumber(Integer.parseInt(reg2.get().value));		
				twoR = true;
				
				if(imm.isPresent()){
					Word val = new Word();
					val.set(Integer.parseInt(imm.get().value));
					String binaryString = "";
					for(int i = 19; i < 32; i++){
						binaryString = 
							val.getBit(i).getValue() ? binaryString + "1" : binaryString + "0";
					}
					immediate = binaryString;	
				}
				else{
					immediate = "0000000000000";
				}
			}
		}
		else{
			immediate = "000000000000000000";
			oneR = true;
		}
	}

	//helper method for getting the register number associated with a register token
	private String getRegisterNumber(int regnum){
		if(regnum < 0 || regnum > 32){
			throw new RuntimeException("provided register number: " + regnum + " is invalid." +
						" register must be in the range of 0-31.");
		}	

		Word reg = new Word();
		reg.set(regnum);
		String val = "";
		for(int i = 27; i < 32; i++){
			val = reg.getBit(i).getValue() ? val + "1" : val + "0";
		}
		return val;
	}
}
