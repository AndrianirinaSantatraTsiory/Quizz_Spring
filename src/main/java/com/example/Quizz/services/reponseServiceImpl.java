package com.example.Quizz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Quizz.DAO.reponseRepository;
import com.example.Quizz.DAO.utilisateurRepository;
import com.example.Quizz.models.noteReponse;
import com.example.Quizz.models.question;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.models.utilisateur;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class reponseServiceImpl implements reponseService {
	private final utilisateurRepository utilisateurRep;
	private final reponseRepository reponseRep;
	private EntityManager entityManager;
	@Override
	public retourAct ajouter(reponse repo) {
		repo.setId_reponse(suggestion_Id());
		retourAct retour = new retourAct();
		@SuppressWarnings("unchecked")
		List<reponse> rep=entityManager.createNativeQuery("select * from reponse where id_reponse='"+repo.getId_reponse()+"'", reponse.class)
				.getResultList();
		@SuppressWarnings("unchecked")
		List<reponse> repVrai=entityManager.createNativeQuery("select * from reponse where id_question='"+repo.getId_question()+"' and verif_reponse=1", reponse.class)
				.getResultList();
		@SuppressWarnings("unchecked")
		List<question> rep1=entityManager.createNativeQuery("select * from question where id_question='"+repo.getId_question()+"'", question.class)
					.getResultList();
		if(rep.size()>0) {
			retour.setSucces(false);
			retour.setMessage("Identifiant reponse dejà saisie");
		}
		else if(repVrai.size()>0&&repo.getVerif_reponse()==1) {
			retour.setSucces(false);
			retour.setMessage("Il y a dejà une reponse vrai pour cette question merci de ne plus en saisir plus");
		}
		else if(rep1.size()>0) {
				reponseRep.save(repo);
				retour.setSucces(true);
				retour.setMessage("Ajout avec succès");
		}
		else {
			retour.setSucces(false);
			retour.setMessage("la question n'existe pas");
		}
			
		return retour;
	}
	@Override
	public retourAct supprimer(String id) {
		retourAct retour=new retourAct();
		reponseRep.deleteById(id);
		retour.setSucces(true);
		retour.setMessage("Supprimé avec succès");
		return retour;
	}
	@Override
	public List<reponse> afficheAll() {
		@SuppressWarnings("unchecked")
		List<reponse> rep=entityManager.createNativeQuery("select * from reponse order by id_reponse", reponse.class)
				.getResultList();
		return rep;
	}
	@Override
	public retourAct modifier(String id, reponse rep) {
		retourAct retour= new retourAct();
		if(!rep.getId_reponse().equals("")) {
			reponseRep.deleteById(id);
			reponseRep.save(rep);
			retour.setSucces(true);
			retour.setMessage("modifié avec succès");
		}
		
		return retour;
	}
	@Override
	public String suggestion_Id() {
		int i=1;
		StringBuilder id=new StringBuilder();
		id.append("R");
		id.append(i);
		List<reponse> rep_list=reponseRep.findAll();
		while(already_exist(rep_list,id.toString())) {
			id.delete(0, id.length());
			i++;
			id.append("R");
			id.append(i);
		}
		return id.toString();
	}
	private boolean already_exist(List<reponse> var,String id) {
		boolean ret=false;
		for(reponse rep : var) {
			if(rep.getId_reponse().equals(id)) {
				ret=true;
				break;
			}
		}
		return ret;
	}
	@Override
	public List<reponse> rechereche(String to_search) {
			@SuppressWarnings("unchecked")
			List<reponse> rep=entityManager.createNativeQuery("select * from reponse where reponse like '%"+to_search+"%' or id_reponse like '%"+to_search+"%'", reponse.class)
					.getResultList();
			return rep;
	}
	@Override
	public reponse selectionner(String id) {
	
		return reponseRep.findById(id).get();
		
	}
	@Override
	public noteReponse correction(String id_utilisateur, List<String> reponse) {
		reponse rep = new reponse();
		utilisateur util=utilisateurRep.findById(id_utilisateur).get();
		noteReponse noteRep=new noteReponse();
		noteRep.setId_utilisateur(id_utilisateur);
		int note=0;
		for(String id_rep:reponse) {
			rep=reponseRep.findById(id_rep).get();
			if(rep.getVerif_reponse()==1) {
				note++;
			}
		}
		noteRep.setNote(note);
		if(note>util.getRecord()) {
			noteRep.setRecord(true);
		}
		
		return noteRep;
	}

}
