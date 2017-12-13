package com.example.demo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {
	private String username;
	private String password;
	
	private Set<Character> characters = new HashSet<Character>(0);
	
	@Id
	@Column(name = "username")
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
	@OneToMany(cascade = CascadeType.ALL, mappedBy="owner")
	public Set<Character> getUserCharacters() {
		return this.characters;
	}
	public void setUserCharacters(Set<Character> characters) {
		this.characters = characters;
	}
}
