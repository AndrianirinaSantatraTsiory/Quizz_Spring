package com.example.Quizz.models;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Getter
@Setter

public class retourAct {
	
	private boolean succes;
	
	private String message;
	
	public retourAct() {
		this.message="";
		this.succes=false;
	}
	
}
