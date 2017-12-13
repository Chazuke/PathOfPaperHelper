package com.example.demo;

public class d10 {
	private final long id;
	private final int roll;
	private final int[] rolls;
	private final int sum;
	
	public d10(long id, int roll) {
		this.id=id;
		this.roll=roll;
		this.rolls=null;
		this.sum=roll;
	}
	
	public d10(long id, int[] rolls, int sum) {
		this.id=id;
		this.roll=-1;
		this.rolls=rolls;
		this.sum=sum;
	}
	
	public long getID() {
		return id;
	}
	
	public int getRoll() {
		return roll;
	}
	
	public int[] getRolls() {
		return rolls;
	}
	
	public int getSum() {
		return sum;
	}
}
