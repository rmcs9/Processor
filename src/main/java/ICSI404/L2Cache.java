package ICSI404;


public class L2Cache {

	private static Word[][] groups = new Word[4][8];
	private static Word[] addrs = new Word[4];

	private static int[] lastClock = { -1, -1, -1, -1 };


	public static Word[] L2Sweep(Word addr){
		Processor.clockCycles += 20;
		for(int i = 0; i < 4; i++){
			if(addrs[i] != null){
				Word addrRange = addrs[i];
				for(int j = 0; j < 8; j++){
					if(addr.isEquals(addrRange).getValue()){
						Word[] groupCopy = new Word[8];
						for(int k = 0; k < 8; k++){
							groupCopy[k] = new Word();
							groupCopy[k].copy(groups[i][k]);
						}
						Processor.clockCycles += 50;
						// System.out.println("L2 HIT!!!");
						return groupCopy;
					}
					addrRange = addrRange.increment();
				}
			}
		}
		//L2 MISS!!!!!
		// System.out.println("L2 MISS!!!");

		int index = 0;
		for(int i = 0; i < 4; i++){
			if(lastClock[i] < lastClock[index]){
				index = i;
			}
		}
		lastClock[index] = Processor.clockCycles;
		
		// addrs[index] = new Word();
		// addrs[index].set(addr.getSigned() - (addr.getSigned() % 8));
		// Word range = addrs[index];
		// Word[] groupCopy = new Word[8];
		// for(int i = 0; i < 8; i++){
		// 	groups[index][i] = new Word();
		// 	groups[index][i] = MainMemory.read(range);
		// 	groupCopy[i] = new Word();
		// 	groupCopy[i].copy(groups[index][i]);
		// 	range = range.increment();
		// }
		// Processor.clockCycles += 400;
		Processor.clockCycles += 50;
		return L2Miss(index, new Word(addr.getSigned() - (addr.getSigned() % 8)));
	}

	public static Word read(Word addr){
		Word blockStart = new Word();
		blockStart.set(addr.getSigned() - (addr.getSigned() % 8));
		for(int i = 0; i < 4; i++){
			if(addrs[i] != null && blockStart.isEquals(addrs[i]).getValue()){
				Word range = addrs[i];
				for(int j = 0; j < 8; j++){
					if(addr.isEquals(range).getValue()){
						Processor.clockCycles += 50;
						Word copy = new Word();
						copy.copy(groups[i][j]);
						return copy;
					}
					range = range.increment();
				}
			}
		}
		//L2 MISS!!!!
		
		int index = 0;
		for(int i = 0; i < 4; i++){
			if(lastClock[i] < lastClock[index]){
				index = i;
			}
		}
		lastClock[index] = Processor.clockCycles;
		L2Miss(index, blockStart);
		Word range = addrs[index];
		for(int i = 0; i < 8; i++){
			if(addr.isEquals(range).getValue()){
				Word copy = new Word();
				copy.copy(groups[index][i]);
				return copy;
			}
			range = range.increment();
		}
		throw new RuntimeException("addr is not found in cached block");
	}

	public static void write(Word addr, Word val){
		Word blockStart = new Word();
		blockStart.set(addr.getSigned() - (addr.getSigned() % 8));
		for(int i = 0; i < 8; i++){
			if(addrs[i] != null && blockStart.isEquals(addrs[i]).getValue()){
				Word range = addrs[i];
				for(int j = 0; j < 8; j++){
					if(addr.isEquals(range).getValue()){
						groups[i][j].copy(val);
						MainMemory.write(addr, val);
						Processor.clockCycles += 50;
					}
					range = range.increment();
				}
			}
		}

		//L2 MISS!!!!
		int index = 0;
		for(int i = 0; i < 4; i++){
			if(lastClock[i] < lastClock[index]){
				index = i;
			}
		}
		lastClock[index] = Processor.clockCycles;
		L2Miss(index, blockStart);
		Word range = addrs[index];
		for(int i = 0; i < 8; i++){
			if(addr.isEquals(range).getValue()){
				groups[index][i].copy(val);
				MainMemory.write(addr, val);
			}
			range = range.increment();
		}
		throw new RuntimeException("addr is not found in cached block");
	}

	private static Word[] L2Miss(int index, Word start){
		addrs[index] = new Word();
		addrs[index].copy(start);
		Word range = addrs[index];
		Word[] groupCopy = new Word[8];
		for(int i = 0; i < 8; i++){
			groups[index][i] = new Word();
			groups[index][i] = MainMemory.read(range);
			groupCopy[i] = new Word();
			groupCopy[i].copy(groups[index][i]);
			range = range.increment();
		}
		Processor.clockCycles += 350;
		return groupCopy;
	}

	public static void cacheClear(){
		groups = new Word[4][8];
		addrs = new Word[4];
		lastClock = new int[4];
		lastClock[0] = -1;
		lastClock[1] = -1;
		lastClock[2] = -1;
		lastClock[3] = -1;
	}
}
