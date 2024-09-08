package com.example.Quizz.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Quizz.models.infoAthentification;
import com.example.Quizz.models.repAthentification;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.models.utilisateur;
import com.example.Quizz.services.utilisateurServiceImpl;

import lombok.AllArgsConstructor;

@RestController()
@RequestMapping("/utilisateur")
@AllArgsConstructor
public class utilisateurController {
	private final utilisateurServiceImpl userService;
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/images";
	
	
	@PostMapping("/creer")
	public retourAct Creer(@RequestBody utilisateur ut){
		//ut.setPhoto(originalName);
		return userService.ajouter(ut);
	}
	
	@PostMapping("/uploadProfil")
	public String Upload(@RequestParam("image") MultipartFile file) throws IOException {
		String originalName = file.getOriginalFilename();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileExtension = originalName.substring(originalName.lastIndexOf("."));
        originalName = originalName.substring(0, originalName.lastIndexOf(".")) + "_" + timeStamp + fileExtension;
		System.out.println(originalName);
		Path uploadPath = Paths.get(uploadDirectory,originalName);
		Files.write(uploadPath, file.getBytes());
		return originalName;
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/afficheAll")
	public List<utilisateur> AfficheAll(){
		return userService.afficherAll();
	}
	
	@DeleteMapping("/supprimer/{id}")
	public retourAct Supprimer(@PathVariable String id) {
		return userService.supprimer(id);
	}
	
	@PutMapping("/modifier/{id}")
	public retourAct Modifier(@PathVariable String id,@RequestBody utilisateur ut) {
		return userService.modifier(id, ut);
	}
	
	@PostMapping("/authentifier")
	public repAthentification Athentifier(@RequestBody infoAthentification auth) {
		return userService.Athentifier(auth);
	}
	
	@GetMapping("/changerRecord/{id}/{record}")
	public retourAct Record(@PathVariable String id,@PathVariable int record) {
		utilisateur ut=userService.selectionner(id);
		ut.setRecord(record);
		return userService.modifier(id, ut);
	}
	@GetMapping("/mdp")
	public String mot() {
		return userService.Validation_code();
	}
	
	@GetMapping("/changer_mdp/{id}")
	public retourAct Changer(@PathVariable String id) {
		return userService.envoyer_code(id);
	}
	

}
