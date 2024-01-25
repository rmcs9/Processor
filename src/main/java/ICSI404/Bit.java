package ICSI404;


public class Bit{
	
	private Boolean bit;
	
	//sets the value of this bit
	public void set(Boolean value){
		bit = value;
	}
	
	//toggles the value of this bit, if on --> off, if off --> on
	public void toggle(){
		if(bit){
			bit = false;
		}
		else{
			bit = true;
		}
	}
	
	//sets this bits value to true
	public void set(){
		bit = true;
	}
	
	//clears the value of this bit to false
	public void clear(){
		bit = false;
	}

	//returns the current value held by this bit
	public Boolean getValue(){
		return bit;
	}
	
	//ands the value of this bit with Bit other
	public Bit and(Bit other){
		if(bit){
			if(other.getValue()){
				return new Bit(true);
			}
		}
		return new Bit(false);
	}

	//ors the value of this bit with Bit other
	public Bit or(Bit other){
		if(bit){
			return new Bit(true);
		}
		if(other.getValue()){
			return new Bit(true);
		}
		return new Bit(false);
	}

	//performs xor on this bit with Bit other
	public Bit xor(Bit other){
		if(bit){
			if(other.bit){
				return new Bit(false);
			}
			else{
				return new Bit(true);
			}
		}
		else{
			if(other.bit){
				return new Bit(true);
			}
			else{
				return new Bit(false);
			}
		}
	}

	//returns a new bit with the negated value of this bit
	public Bit not(){
		if(bit){ 
			return new Bit(false);
		}
		else{
			return new Bit(true);
		}
	}
	
	//returns "t" for true, "f" for false
	public String toString(){
		return bit ? "t" : "f";
	}
	
	//my constructor for Bit, functions exactly the same as the set method
	public Bit(boolean val){
		bit = val;
	}
}
