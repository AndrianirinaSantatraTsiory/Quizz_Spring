package com.example.Quizz;

import com.example.Quizz.models.question;
import com.example.Quizz.models.retourAct;
import com.example.Quizz.services.questionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Quizz.controller.questionController;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

public class questionControllerTest {

    private MockMvc mockMvc;

    @Mock    
    private questionServiceImpl questServiceImpl;

    @InjectMocks
    private questionController questController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc=MockMvcBuilders.standaloneSetup(questController).build();
    }

    @Test
    public void testAjouterQuestion() throws Exception {
        question newQuestion = new question("Q1", "Sample Question", 1);
        retourAct mockRetour = new retourAct(true, "Question added successfully");

        when(questServiceImpl.ajouter(any(question.class))).thenReturn(mockRetour);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/question/ajouter")
                .content(asJsonString(newQuestion)) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succes").value(true));
    }
  
    @Test
	public void should_succes_saveQuest() {
		//Given
		question quest = new question("Q01", "Qui est le développeur de ce site", 1);
		//Mock the calls
		Mockito.when(questServiceImpl.ajouter(quest)).thenReturn(new retourAct(true,"Ajout avec succès"));
		//When
		retourAct ret = questController.Ajouter(quest);
		//Then
		assertTrue(ret.isSucces());
	}
    @Test
    public void testSupprimerQuestion() throws Exception {
        String questionId = "Q1";
        retourAct mockRetour = new retourAct(true, "Question deleted successfully");

        when(questServiceImpl.supprimer(questionId)).thenReturn(mockRetour);

        mockMvc.perform(MockMvcRequestBuilders.delete("/question/supprimer/{id}", questionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succes").value(true));
    }

    @Test 
    public void testModifierQuestion() throws Exception{
    	String questionId = "Q1";
    	retourAct mockRetour = new retourAct(true, "Question updated succesfully");
    	question updatedQuestion = new question("Q1", "Who is the owner of this website?", 1);
    	when(questServiceImpl.modifier(any(String.class), any(question.class))).thenReturn(mockRetour);
    	
     	mockMvc.perform(MockMvcRequestBuilders.put("/question/modifier/{id}", questionId)
    		   .contentType(MediaType.APPLICATION_JSON)
    		   .content(asJsonString(updatedQuestion)))
    		   .andExpect(status().isOk())
		       .andExpect(jsonPath("$.succes").value(true));
    }
    
    @Test
    public void testAfficheAllQuestion() throws Exception{
    	question quest1 = new question("Q1", "Who is the owner of this website?", 1);
    	question quest2 = new question("Q2", "Avec quelle techno cette appli a été developpé?", 2);
    	question quest3 = new question("Q3", "Quelle est le pseudo du développeur sur codingame?", 3);
    	ArrayList<question> quests = new ArrayList<>();
    	quests.add(quest1);
    	quests.add(quest2);
    	quests.add(quest3);
    	when(questServiceImpl.afficherAll()).thenReturn(quests);
    	
    	mockMvc.perform(MockMvcRequestBuilders.get("/question/afficheAll") 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[2].enonce").value("Quelle est le pseudo du développeur sur codingame?"));
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
