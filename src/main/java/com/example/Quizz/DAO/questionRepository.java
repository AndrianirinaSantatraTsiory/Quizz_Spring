package com.example.Quizz.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Quizz.models.question;

public interface questionRepository extends JpaRepository<question,String>{

}
