package com.kata.bdd;

import com.kata.Application;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
public abstract class SpringBootBaseIntegrationTest {

}