package com.example.Quizz.services;

import java.util.List;

import com.example.Quizz.models.question;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;

public interface questionService {
	retourAct ajouter(question quest);
	retourAct supprimer(String id);
	retourAct modifier(String id,question quest);
	List<question> afficherAll();
	String suggestion_Id();
	List<question> rechereche(String to_search);
	question selectionner(String id);
	List<reponse> reponse_a_question(String id_question);
	retourAct verif_existance_reponse(String id_question);
	List<question> question_hasard();
}
