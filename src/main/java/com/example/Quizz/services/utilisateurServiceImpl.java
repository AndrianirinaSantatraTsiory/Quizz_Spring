package com.example.Quizz.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto;
import com.example.Quizz.DAO.utilisateurRepository;
import com.example.Quizz.models.infoAthentification;
import com.example.Quizz.models.question;
import com.example.Quizz.models.repAthentification;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.models.utilisateur;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class utilisateurServiceImpl implements utilisateurService {
	@Autowired
	private final JavaMailSender mailSender;
	private final EntityManager entityManager;
	private final utilisateurRepository utilisateurRep;

	@Override
	public retourAct ajouter(utilisateur util) {
		
		retourAct retour = new retourAct();
		util.setId_utilisateur(suggestion_Id());
		@SuppressWarnings("unchecked")
		List<utilisateur> rep=entityManager.createNativeQuery("select * from utilisateur where mail='"+util.getMail()+"'", utilisateur.class)
				.getResultList();
		if(rep.size()>0) {
			retour.setSucces(false);
			retour.setMessage("Adresse mail déjà utilisé");
		}
		else {
			BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
			String mdp_cripte=bcrypt.encode(util.getMdp_ut());
			util.setMdp_ut(mdp_cripte);
			utilisateurRep.save(util);
			retour.setSucces(true);
			retour.setMessage("Compte crée avec succès");
		}
		return retour;
		
	}

	@Override
	public retourAct modifier(String id, utilisateur util) {
		retourAct retour=new retourAct();
		@SuppressWarnings("unchecked")
		List<utilisateur> same_change=entityManager.createNativeQuery("select * from utilisateur where mail='"+util.getMail()+"' and id_utilisateur<>'"+id+"'", utilisateur.class)
				.getResultList();
		if(same_change.size()>0) {
			retour.setSucces(false);
			retour.setMessage("Changement invalide. Ce mail est déjà utilisié par un autre compte");
		}
		else {
			if(!util.getId_utilisateur().equals("")) {
				utilisateurRep.deleteById(id);
				utilisateurRep.save(util);
				retour.setSucces(true);
				retour.setMessage("Mise à jour bien effectué");

			}
		}
		return retour;
	}

	@Override
	public retourAct supprimer(String id) {
		retourAct retour=new retourAct();
		utilisateurRep.deleteById(id);
		retour.setSucces(true);
		retour.setMessage("Compte supprimé avec succès");
		return retour;
	}

	@Override
	public List<utilisateur> afficherAll() {
		@SuppressWarnings("unchecked")
		List<utilisateur> rep=entityManager.createNativeQuery("select * from utilisateur order by id_utilisateur", utilisateur.class)
				.getResultList();
		return rep;
	}

	@Override
	public List<utilisateur> rechercher(String to_search) {
		@SuppressWarnings("unchecked")
		List<utilisateur> rep=entityManager.createNativeQuery("select * from utilisateur where nom like '%"+to_search+"%' or mail like '%"+to_search+"%'", utilisateur.class)
				.getResultList();
		return rep;
	}

	@Override
	public utilisateur selectionner(String id) {
		
		return utilisateurRep.findById(id).get();
	}

	@Override
	public repAthentification Athentifier(infoAthentification info) {
		repAthentification retour=new repAthentification();
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
		@SuppressWarnings("unchecked")
		List<utilisateur> ident=entityManager.createNativeQuery("select * from utilisateur where mail ='"+info.getIdentifiant()+"' or id_utilisateur='"+info.getIdentifiant()+"'", utilisateur.class)
				.getResultList();
		if(ident.size()==0) {
			retour.setMessage("identifiant incorrecte");
		}
		else
		{
				utilisateur ut=ident.get(0);
				
				if(bcrypt.matches(info.getMdp(),ut.getMdp_ut())||bcrypt.matches(info.getMdp(),ut.getValidation())){
					retour.setSucces(true);
					retour.setId(ut.getId_utilisateur());
					retour.setNom(ut.getNom());
					retour.setPhoto(ut.getPhoto());
					retour.setMessage("Bienvenue "+ut.getNom());
					if(ut.getValidation().equals(info.getMdp())) {
						utilisateurRep.deleteById(ut.getId_utilisateur());
						ut.setValidation("");
						utilisateurRep.save(ut);
					}
				}
				else{
					retour.setMessage("Verifiez votre mot de passe");
				}
		}
		return retour;
	}
	
	public String suggestion_Id() {
		int i=1;
		StringBuilder id=new StringBuilder();
		id.append("U");
		id.append(i);
		//String id="Q"+i;
		List<utilisateur> quest_list=utilisateurRep.findAll();
		while(already_exist(quest_list,id.toString())) {
			id.delete(0, id.length());
			i++;
			id.append("U");
			id.append(i);
		}
		return id.toString();
	}
	
	private boolean already_exist(List<utilisateur> var,String id) {
		boolean ret=false;
		for(utilisateur rep : var) {
			if(rep.getId_utilisateur().equals(id)) {
				ret=true;
				break;
			}
		}
		return ret;
	}
	public String Validation_code() {
		StringBuilder code=new StringBuilder();
		
		List<utilisateur> ut_list=utilisateurRep.findAll();
		do {
			code.delete(0, code.length());
			code.append(hasard_code());
		}while(already_exist_code(ut_list,code.toString()));
		return code.toString();
	}
	public String hasard_code() {
		int a;
		StringBuilder code=new StringBuilder();
		char[] lettre= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9'};
		for(int i=0;i<10;i++) {
			a=(int)(Math.random()*lettre.length);
			code.append(lettre[a]);
		}
		return code.toString();
	}
	
	private boolean already_exist_code(List<utilisateur> var,String code) {
		boolean ret=false;
		for(utilisateur ut : var) {
			if(ut.getValidation().equals(code)) {
				ret=true;
				break;
			}
		}
		return ret;
	}

	@Override
	public retourAct envoyer_code(String id) {
		retourAct retour=new retourAct();
		String code=Validation_code();
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
		utilisateur util=utilisateurRep.findById(id).get();
		utilisateurRep.deleteById(id);
		String code_cripte=bcrypt.encode(code);
		util.setValidation(code_cripte);
		//util.setValidation(Validation_code());
		utilisateurRep.save(util);
		
		//before mailing
		
		/**Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);*/
		//mailing
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(util.getMail());
		//message.setFrom("santatradev@gmail.com");
        message.setSubject("mot de passe oublié");
        message.setText("Salut, "+util.getNom()+" votre code de validation est :"+code);

        mailSender.send(message);
		retour.setSucces(true);
		retour.setMessage("Votre nouveau mot de passe à été envoyé via votre mail");
		return retour;
	}

}
