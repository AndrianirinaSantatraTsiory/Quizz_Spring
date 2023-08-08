package com.example.Quizz.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class repAthentification {
	private boolean succes;
	private String id;
	private String nom;
	private String message;
	private String photo;
	public repAthentification() {
		succes=false;
		id="";
		nom="";
		message="";
		photo="";
	}

}
