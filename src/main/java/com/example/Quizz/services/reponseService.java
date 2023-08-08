package com.example.Quizz.services;

import java.util.List;

import com.example.Quizz.models.noteReponse;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;

public interface reponseService{
	retourAct ajouter(reponse rep);
	retourAct supprimer(String id);
	List<reponse> afficheAll();
	retourAct modifier(String id,reponse rep);
	String suggestion_Id();
	List<reponse> rechereche(String to_search);
	reponse selectionner(String id);
	noteReponse correction(String id_utilisateur,List<String> reponse);
}
