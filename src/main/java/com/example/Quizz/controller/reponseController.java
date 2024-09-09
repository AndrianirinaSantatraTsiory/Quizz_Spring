package com.example.Quizz.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quizz.models.noteReponse;
import com.example.Quizz.models.reponse;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.services.reponseServiceImpl;

import lombok.AllArgsConstructor;
@RestController()
@RequestMapping("/reponse")
@AllArgsConstructor
public class reponseController {
	private final reponseServiceImpl reps;
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/ajouter")
	public retourAct Ajouter(@RequestBody reponse rep) {
		return reps.ajouter(rep);
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/supprimer/{id}")
	public retourAct Supprimer(@PathVariable String id) {
		return reps.supprimer(id);
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/afficheAll")
	public List<reponse> AfficheAll(){
		return reps.afficheAll();
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PutMapping("/modifier/{id}")
	public retourAct Modifier(@PathVariable String id,@RequestBody reponse rep ) {
		return reps.modifier(id, rep);
	}
	
	
	@GetMapping("/suggestion")
	public String Suggest() {
		return reps.suggestion_Id();
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/rechercher/{to_search}")
	public List<reponse> Rechercher(@PathVariable String to_search) {
		return reps.rechereche(to_search);
	}
	@GetMapping("/selectionner/{id}")
	public reponse Selectionnerr(@PathVariable String id) {
		return reps.selectionner(id);
	}
	@PutMapping("/corriger/{id}")
	public noteReponse corriger(@PathVariable String id,@RequestBody List<String> repList) {
		return reps.correction(id, repList);
	}
}
