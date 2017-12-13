package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String username;
	private String password;
	
	private Set<Character> characters = new HashSet<Character>(0);
	
	public User(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public User() {};
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Character> getUserCharacters() {
		return this.characters;
	}
	public void setUserCharacters(Set<Character> characters) {
		this.characters = characters;
	}
}
