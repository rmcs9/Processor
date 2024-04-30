package ICSI404;

import java.util.LinkedList;
import java.util.HashMap;

public class Lexer{

	public class StringHandler {

    	private final String filestring;

    	public StringHandler(String input){
        	filestring = input;
    	}

    	private int index = 0;

    	public char peek(int i){
        	return filestring.charAt(index + i);
    	}

    	public String peekString(int i){
        	return filestring.substring(index, i - 1);
    	}

    	public char getChar(){
        	index++;
        	return filestring.charAt(index - 1);
    	}

    	public void swallow(int i){
       		index = index + i;
    	}

    	public boolean isDone(){
        	return index >= filestring.length();
    	}

    	public String remainder(){
        	return filestring.substring(index);
    	}
	}

	private StringHandler file;
	private HashMap<String, Token.TokenType> keywords;
	public LinkedList<Token> tokens;
	private int linenum = 1;
	private int charPOS = 0;

	public Lexer(String fstring){
		file = new StringHandler(fstring);
		tokens = new LinkedList<>();
		keywords = new HashMap<>();
		keywordFill();
	}

	private void keywordFill(){
		keywords.put("MATH",       Token.TokenType.MATH);	
		keywords.put("ADD",        Token.TokenType.ADD);
		keywords.put("SUBTRACT",   Token.TokenType.SUBTRACT);
		keywords.put("MULTIPLY",   Token.TokenType.MULTIPLY);
		keywords.put("AND",        Token.TokenType.AND);
		keywords.put("OR",         Token.TokenType.OR);
		keywords.put("NOT",        Token.TokenType.NOT);
		keywords.put("XOR",        Token.TokenType.XOR);
		keywords.put("COPY",       Token.TokenType.COPY);
		keywords.put("HALT",       Token.TokenType.HALT);
		keywords.put("BRANCH",     Token.TokenType.BRANCH);
		keywords.put("JUMP",       Token.TokenType.JUMP);
		keywords.put("CALL",       Token.TokenType.CALL);
		keywords.put("PUSH",       Token.TokenType.PUSH);
		keywords.put("LOAD",       Token.TokenType.LOAD);
		keywords.put("RETURN",     Token.TokenType.RETURN);
		keywords.put("STORE",      Token.TokenType.STORE);
		keywords.put("PEEK",       Token.TokenType.PEEK);
		keywords.put("POP",        Token.TokenType.POP);
		keywords.put("EQ",         Token.TokenType.EQ);
		keywords.put("NE",         Token.TokenType.NE);
		keywords.put("GT",         Token.TokenType.GT);
		keywords.put("LT",         Token.TokenType.LT);
		keywords.put("GTE",        Token.TokenType.GTE);
		keywords.put("LTE",        Token.TokenType.LTE);
		keywords.put("JUMP",       Token.TokenType.JUMP);
		keywords.put("SHIFTLEFT",  Token.TokenType.SHIFTLEFT);
		keywords.put("SHIFTRIGHT", Token.TokenType.SHIFTRIGHT);
	}

	public void Lex(){
		while(!file.isDone()){
			char c = file.peek(0);	
			switch(c){
				case ' ':
					file.swallow(1);
					charPOS++;
				break;
				case '\n':
					tokens.add(new Token(Token.TokenType.SEPERATOR, linenum, charPOS));
					file.swallow(1);
					charPOS = 0;
					linenum++;
				break;
				case '\r':
					file.swallow(1);
				break;
				case '#':
					file.swallow(1);
					while(!file.isDone() && file.peek(0) != '\n'){
						file.swallow(1);
					}
				break;
				default:
					if(Character.isLetter(c))
						tokens.add(processWord());
					else if(Character.isDigit(c) || c == '-')
						tokens.add(processNumber());
					else
						throw new RuntimeException("invalid character: " + c);
				break;
			}
		}
	}


	private Token processWord(){
		char current = file.getChar();
        String accum = "" + current;
        charPOS++;
        while (!file.isDone() &&
                (Character.isLetter(file.peek(0)) || Character.isDigit(file.peek(0)) || file.peek(0) == '_')) {
            current = file.getChar();
            accum += current;
            charPOS++;
        }
		accum = accum.toUpperCase();
        if(keywords.containsKey(accum)) {
            return new Token(keywords.get(accum), linenum, charPOS);
        } 
		else if(accum.charAt(0) == 'R'){
			return new Token(Token.TokenType.REGISTER, linenum, charPOS, accum.substring(1));
		}
		else {
			throw new RuntimeException("Unrecognized word: " + accum + " found.");
        }
	}


	private Token processNumber(){
		char current = file.getChar();
        String accum = "" + current;
        charPOS++;
        while (!file.isDone() && (Character.isDigit(file.peek(0)))) {
            current = file.getChar();
            accum += current;
            charPOS++;
        }
        return new Token(Token.TokenType.NUMBER, linenum, charPOS, accum);	
	}
}
