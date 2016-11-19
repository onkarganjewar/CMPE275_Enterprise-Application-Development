package edu.sjsu.cmpe275.lab2.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Shreya Prabhu Cmpe275-Lab2
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/META-INF/persistence.xml" ,
		"file:src/main/webapp/WEB-INF/spring-context.xml" })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc springMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.springMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void RenderHomeTest() throws Exception {
		this.springMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("home")).andDo(print());
	}

	@Test
	public void test_getUser() throws Exception {
		ResultActions resultActions = this.springMvc.perform(get("/user/{id}", 26)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("showUser"));
		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getUser_JSONTest() throws Exception {
		this.springMvc.perform(MockMvcRequestBuilders.get("/user/26?json=true").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print()).andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id").value(26));;
	}
}
