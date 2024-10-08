package org.example.usermanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Response {

	private int id;
	private String name;
	private String passwd;
	private String mail;

	public User(){}

	public User(int id, String name, String passwd, String mail){
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.mail = mail;
	}

	public User(String name, String passwd, String mail) {
		this.name = name;
		this.passwd = passwd;
		this.mail = mail;
	}
	
	public User(int id, String name, String mail){
		this.id = id;
		this.name = name;
		this.mail = mail;
	}

	public User(String name, String mail){
		this.name = name;
		this.mail = mail;
	}

	@Override
	public String toString() {
		return String.format("{\"id\"=%s, \"name\"=%s, \"mail\"=%s}", this.id, this.name, this.mail);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@JsonIgnore
	public boolean isComplete() {
		if (this.name == null || this.mail == null || this.passwd == null)
			return false;
		return true;
	}

	@JsonIgnore
	public boolean isEmpty() {
		if (this.name == null && this.mail == null && this.passwd == null)
			return false;
		return true;
	}

	@JsonIgnore
	public boolean isValid() {
		if (this.id != 0 && this.name != null && this.mail != null)
			return true;
		return false;
	}
	
}
