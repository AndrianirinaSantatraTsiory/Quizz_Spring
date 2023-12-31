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

import com.example.Quizz.models.question;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.services.questionServiceImpl;


import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/question")
@AllArgsConstructor
public class questionController {
	private final questionServiceImpl quests;
	
	@PostMapping("/ajouter")
	public retourAct Ajouter(@RequestBody question quest ){
		return quests.ajouter(quest);
	}
	
	@DeleteMapping("/supprimer/{id}")
	public retourAct Supprimer(@PathVariable String id) {
		return quests.supprimer(id);
	}
	
	@PutMapping("/modifier/{id}")
	public retourAct Modifier(@PathVariable String id,@RequestBody question quest) {
		retourAct retour=new retourAct();
		retour=quests.modifier(id,quest);
		return retour;
	}
	
	@GetMapping("/afficheAll")
	public List<question> AfficherAll(){
		return quests.afficherAll();
	}
	
	@GetMapping("/suggestion")
	public String suggest() {
		return quests.suggestion_Id();
	}
	@GetMapping("/rechercher/{to_search}")
	public List<question> Rechercher(@PathVariable String to_search) {
		return quests.rechereche(to_search);
	}
	@GetMapping("/reponse_a_question/{id}")
	public List<reponse> ListerReponse(@PathVariable String id){
		return quests.reponse_a_question(id);
		
	}
	
	@GetMapping("/verif_existance_reponse/{id}")
	public retourAct Verification_existance_reponse(@PathVariable String id) {
		return quests.verif_existance_reponse(id);
	}
	
	@GetMapping("/question_hasard")
	public List<question> Tirage(){
		return quests.question_hasard();
	}
	
	@GetMapping("/selectionner/{id}")
	public question Selectionner(@PathVariable String id){
		return quests.selectionner(id);
	}
}
