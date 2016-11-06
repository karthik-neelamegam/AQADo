package game;

public class Counter {
	
	int pos;
	boolean safe;
	boolean finish;
	boolean start;
	
	public Counter() {
		pos = 1;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public boolean isSafe() {
		if(pos == 5 || pos == 11 || pos == 1) return true;
		return false;
	}

	public boolean isFinish() {
		if(pos == 11) return true;
		return false;	
	}

	public boolean isStart() {
		if(pos == 1) return true;
		return false;
	}

}
