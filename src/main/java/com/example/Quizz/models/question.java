package com.example.Quizz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
public class question {
	@Id()
	@Column(length=20)
	private String id_question;
	@Column(length=200)
	private String enonce;
	
	private Integer niveau;
	

}
