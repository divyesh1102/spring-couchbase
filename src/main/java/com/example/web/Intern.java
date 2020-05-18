package com.example.web;


public class Intern {
	private Integer id;
	private String name;
	private String tech;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}
	
	
	
	public Intern() {
		super();
	}
	
	public Intern(Integer id, String name, String tech) {
		super();
		this.id = id;
		this.name = name;
		this.tech = tech;
	}
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", tech=" + tech;
	}
	
	
}
