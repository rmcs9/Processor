package ICSI404;



public class Token {

    public enum TokenType {
		SEPERATOR, NUMBER, REGISTER, COMMENT,
		MATH, ADD, SUBTRACT, MULTIPLY, AND, OR, NOT, XOR, COPY, HALT, BRANCH, JUMP, CALL, PUSH, LOAD, 
		RETURN, STORE, PEEK, POP, EQ, NE, GT, LT, GTE, LTE, 
		SHIFTLEFT, SHIFTRIGHT,
    }

    public TokenType type;

    public String value;

    public int linenum;

    public int charPosition;

    public Token(TokenType toktype, int lNumber, int pos){
        type = toktype;
        linenum = lNumber;
        charPosition = pos;
    }

    public Token(TokenType toktype, int lNumber, int pos, String val){
        type = toktype;
        linenum = lNumber;
        charPosition = pos;
        value = val;
    }

    public String toString(){
        switch(this.type){
           	case NUMBER:
                return "NUMBER(" + this.value + ")";
			case REGISTER: 
				return "REGISTER(" + this.value + ")";
            default:
                return type.toString();
        }
    }
}
