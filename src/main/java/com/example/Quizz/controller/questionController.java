package com.example.Quizz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	private final questionServiceImpl questServiceImpl;
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/ajouter")
	public retourAct Ajouter(@RequestBody question quest ){
		return questServiceImpl.ajouter(quest);
		//return new retourAct(true, "message");
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/supprimer/{id}")
	public retourAct Supprimer(@PathVariable String id) {
		return questServiceImpl.supprimer(id);
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("/modifier/{id}")
	public retourAct Modifier(@PathVariable String id,@RequestBody question quest) {
		retourAct retour=new retourAct();
		retour=questServiceImpl.modifier(id,quest);
		return retour;
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/afficheAll")
	public List<question> AfficherAll(){
		return questServiceImpl.afficherAll();
	}
	
	@GetMapping("/suggestion")
	public String suggest() {
		return questServiceImpl.suggestion_Id();
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/rechercher/{to_search}")
	public List<question> Rechercher(@PathVariable String to_search) {
		return questServiceImpl.rechereche(to_search);
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/reponse_a_question/{id}")
	public List<reponse> ListerReponse(@PathVariable String id){
		return questServiceImpl.reponse_a_question(id);
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/verif_existance_reponse/{id}")
	public retourAct Verification_existance_reponse(@PathVariable String id) {
		return questServiceImpl.verif_existance_reponse(id);
	}
	
	@GetMapping("/question_hasard")
	public List<question> Tirage(){
		return questServiceImpl.question_hasard();
	}
	
	@GetMapping("/selectionner/{id}")
	public question Selectionner(@PathVariable String id){
		return questServiceImpl.selectionner(id);
	}
}
