package com.example.Quizz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reponse")
@Getter
@Setter
@NoArgsConstructor
public class reponse {
	@Id()
	@Column(length=20)
	private String id_reponse;
	@Column(length=200)
	private String reponse;
	
	private Integer verif_reponse;
	
	@Column(length=20)
	private String id_question;
	
	
	
}
