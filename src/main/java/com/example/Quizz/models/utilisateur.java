package com.example.Quizz.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
public class utilisateur {
	@Id()
	@Column(length=20)
	private String id_utilisateur;
	@Column(length=30)
	private String nom;
	
	@Column(length=30)
	private String photo;
	
	@Column(length=20)
	private String mail;
	
	@Column(length=255)
	private String mdp_ut;
	
	@Column(length=255)
	private String validation;
	
	private Integer record;
}
