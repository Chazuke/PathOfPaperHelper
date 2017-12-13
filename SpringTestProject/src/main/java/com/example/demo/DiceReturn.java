package com.example.demo;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiceReturn {

	private long id;
	private int roll;
	private int[] rolls;
	private int sum;
	
	public DiceReturn() {}
	
	
	@Override
	public String toString() {
		if (getRoll() == -1) {
			return "You rolled " + Arrays.toString(getRolls()) + " Sum: " + getSum();
		}
		else {
			return "You rolled [" + getRoll() + "]";
		}
	}
	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public int getRoll() {
		return roll;
	}



	public void setRoll(int roll) {
		this.roll = roll;
	}



	public int[] getRolls() {
		return rolls;
	}



	public void setRolls(int[] rolls) {
		this.rolls = rolls;
	}



	public int getSum() {
		return sum;
	}



	public void setSum(int sum) {
		this.sum = sum;
	}
}
