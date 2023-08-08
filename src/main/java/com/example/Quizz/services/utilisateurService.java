package com.example.Quizz.services;

import java.util.List;

import com.example.Quizz.models.infoAthentification;
import com.example.Quizz.models.repAthentification;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.models.utilisateur;

public interface utilisateurService {
	retourAct ajouter(utilisateur util);
	retourAct modifier(String id,utilisateur util);
	retourAct supprimer(String id);
	List<utilisateur> afficherAll();
	List<utilisateur> rechercher(String to_search);
	utilisateur selectionner(String id);
	repAthentification Athentifier(infoAthentification info);
	retourAct envoyer_code(String id);
}
