package com.example.Quizz.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quizz.models.infoAthentification;
import com.example.Quizz.models.repAthentification;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.models.utilisateur;
import com.example.Quizz.services.reponseServiceImpl;
import com.example.Quizz.services.utilisateurServiceImpl;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/utilisateur")
@AllArgsConstructor
public class utilisateurController {
	private final utilisateurServiceImpl users;
	
	@PostMapping("/creer")
	public retourAct Creer(@RequestBody utilisateur ut) {
		return users.ajouter(ut);
	}
	
	@GetMapping("/afficheAll")
	public List<utilisateur> AfficheAll(){
		return users.afficherAll();
	}
	
	@DeleteMapping("/supprimer/{id}")
	public retourAct Supprimer(@PathVariable String id) {
		return users.supprimer(id);
	}
	
	@PutMapping("/modifier/{id}")
	public retourAct Modifier(@PathVariable String id,@RequestBody utilisateur ut) {
		return users.modifier(id, ut);
	}
	
	@PostMapping("/authentifier")
	public repAthentification Athentifier(@RequestBody infoAthentification auth) {
		return users.Athentifier(auth);
	}
	
	@GetMapping("/changerRecord/{id}/{record}")
	public retourAct Record(@PathVariable String id,@PathVariable int record) {
		utilisateur ut=users.selectionner(id);
		ut.setRecord(record);
		return users.modifier(id, ut);
	}
	@GetMapping("/mdp")
	public String mot() {
		return users.Validation_code();
	}
	
	@GetMapping("/changer_mdp/{id}")
	public retourAct Changer(@PathVariable String id) {
		return users.envoyer_code(id);
	}
	

}
