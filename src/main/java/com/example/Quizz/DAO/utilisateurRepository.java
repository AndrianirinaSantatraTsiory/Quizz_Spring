package com.example.Quizz.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Quizz.models.utilisateur;

public interface utilisateurRepository extends JpaRepository<utilisateur,String>{
	Optional<utilisateur> findByMail(String username);
}
