package ICSI404;



public class InstructionCache {
	
	private static Word[] cache = new Word[8];

	private static Word address;

	public static Word read(Word addr){
		if(address != null){
			Word addrRange = address;
			for(int i = 0; i < 8; i++){
				if(addr.isEquals(addrRange).getValue()){
					Processor.clockCycles += 10;
					// System.out.println("L1 HIT!!!");
					return cache[i];
				}
				addrRange = addrRange.increment();
			}
		}
		
		// System.out.println("L1 MISS!!!!");
		Word[] pull = L2Cache.L2Sweep(addr);
		address = new Word();
		address.set(addr.getSigned() - (addr.getSigned() % 8));
		cache = pull;
		
		Word addrRange = address;
		for(int i = 0; i < 8; i++){
			if(addr.isEquals(addrRange).getValue()){
				return cache[i];
			}
		}
		throw new RuntimeException("SOMETHING REALLY BAD HAPPENED");

	}

	public static void cacheClear() {
		cache = new Word[8];
		address = null;
	}
}
