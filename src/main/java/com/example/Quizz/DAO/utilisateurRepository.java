package com.example.Quizz.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Quizz.models.utilisateur;

public interface utilisateurRepository extends JpaRepository<utilisateur,String>{
	
}
