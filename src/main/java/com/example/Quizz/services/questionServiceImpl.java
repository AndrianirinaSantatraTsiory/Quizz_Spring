package com.example.Quizz.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.example.Quizz.DAO.questionRepository;
import com.example.Quizz.models.question;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class questionServiceImpl implements questionService{
	private final questionRepository  questionRep;
	EntityManager entityManager;
	@Override
	public retourAct ajouter(question quest) {
		quest.setId_question(suggestion_Id());;
		retourAct retour = new retourAct();
		@SuppressWarnings("unchecked")
		List<question> rep=entityManager.createNativeQuery("select * from question where id_question='"+quest.getId_question()+"'", question.class)
				.getResultList();
		if(rep.size()>0) {
			retour.setSucces(false);
			retour.setMessage("Identifiant question dejà saisie");
		}
		else {
			questionRep.save(quest);
			retour.setSucces(true);
			retour.setMessage("Ajout avec succès");
		}
		return retour;
	}
	@Override
	public retourAct supprimer(String id) {
		retourAct retour=new retourAct();
		questionRep.deleteById(id);
		retour.setSucces(true);
		retour.setMessage("Supprimé avec succès");
		return retour;
	}
	@Override
	public retourAct modifier(String id,question quest) {
		retourAct retour= new retourAct();
		if(!quest.getId_question().equals("")) {
			questionRep.deleteById(id);
			questionRep.save(quest);
			retour.setSucces(true);
			retour.setMessage("Modifié avec succès");
		}
		return retour;
			
	}
	@Override
	public List<question> afficherAll() {
		@SuppressWarnings("unchecked")
		List<question> rep=entityManager.createNativeQuery("select * from question order by id_question", question.class)
				.getResultList();
		return rep;
		//return questionRep.findAll();
	}
	@Override
	public String suggestion_Id() {
		int i=1;
		StringBuilder id=new StringBuilder();
		id.append("Q");
		id.append(i);
		//String id="Q"+i;
		List<question> quest_list=questionRep.findAll();
		while(already_exist(quest_list,id.toString())) {
			id.delete(0, id.length());
			i++;
			id.append("Q");
			id.append(i);
		}
		return id.toString();
	}
	
	private boolean already_exist(List<question> var,String id) {
		boolean ret=false;
		for(question rep : var) {
			if(rep.getId_question().equals(id)) {
				ret=true;
				break;
			}
		}
		return ret;
	}
	@Override
	public List<question> rechereche(String to_search) {
			@SuppressWarnings("unchecked")
			List<question> rep=entityManager.createNativeQuery("select * from question where enonce like '%"+to_search+"%' or id_question like '%"+to_search+"%'", question.class)
					.getResultList();
			return rep;
	}
	@Override
	public List<reponse> reponse_a_question(String id_question) {
		@SuppressWarnings("unchecked")
		List<reponse> rep=entityManager.createNativeQuery("select * from reponse where id_question='"+id_question+"'", reponse.class)
				.getResultList();
		return rep;
	}
	@Override
	public retourAct verif_existance_reponse(String id_question) {
		retourAct ret=new retourAct();
		@SuppressWarnings("unchecked")
		List<reponse> rep=entityManager.createNativeQuery("select * from reponse where id_question='"+id_question+"' and verif_reponse=1", reponse.class)
				.getResultList();
		if(rep.size()>0) {
			ret.setSucces(true);
			ret.setMessage("Il y a dejà une reponse vrai pour cette question");
		}
		else {
			ret.setSucces(false);
			ret.setMessage("Il n'y a pas encore de reponse vrai pour cette question");
		}
		return ret;
	}
	@Override
	public List<question> question_hasard() {
		String[] niveau1=id_hasard(1,5);
		String[] niveau2=id_hasard(2,3);
		String[] niveau3=id_hasard(3,3);
		String req="select * from question where id_question in('"+niveau1[0]+"'";
		for(int i=1;i<niveau1.length;i++) {
			req+=",'"+niveau1[i]+"'";
		}
		
		for(int i=0;i<niveau2.length;i++) {
			req+=",'"+niveau2[i]+"'";
		}
		for(int i=0;i<niveau3.length;i++) {
			req+=",'"+niveau3[i]+"'";
		}
		req+=")";
		@SuppressWarnings("unchecked")
		List<question> rep=entityManager.createNativeQuery(req, question.class)
				.getResultList();
		return rep;
	}
	public String[] id_hasard(int niveau,int nbr) {
		@SuppressWarnings("unchecked")
		List<question> allquestion=entityManager.createNativeQuery("select * from question where niveau="+niveau, question.class)
				.getResultList();;
		int n=allquestion.size();
		System.out.println("size "+n);
		int rand;
		List<String> allquest=new ArrayList<String>();
		String[] quest_tire=new String[nbr];
		for(int i=0;i<nbr;i++)
			quest_tire[i]="an";
		for(int i=0;i<n;i++) {
			allquest.add(allquestion.get(i).getId_question());
		}
		
		for(int i=0;i<nbr;i++) {
			rand=(int)(Math.random()*allquest.size());
			quest_tire[i]=allquest.get(rand);
			allquest.remove(rand);
		}
		return quest_tire;
		
	}
	@Override
	public question selectionner(String id) {
		
		return questionRep.findById(id).get();
	}

}
