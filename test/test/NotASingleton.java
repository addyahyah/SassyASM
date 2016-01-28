package test;

public class NotASingleton {
	
	private int id;
	
	NotASingleton(){
		id = 0;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}

}
