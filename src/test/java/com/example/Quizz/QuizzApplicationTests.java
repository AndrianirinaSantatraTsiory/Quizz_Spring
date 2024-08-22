package com.example.Quizz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuizzApplicationTests {
	int a;
	@BeforeEach
	void setUp() {
		a=4;
	}
	@Test
	void contextLoads() {
		assertEquals(a,5);
	}

}
