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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 
 * @author Shreya Prabhu Cmpe275-Lab2
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/META-INF/persistence.xml",
		"file:src/main/webapp/WEB-INF/spring-context.xml", })
@WebAppConfiguration

public class PhoneControllerTest {
	private MockMvc springMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup() throws Exception {
		this.springMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void RenderPhoneHomeTest() throws Exception {
		this.springMvc.perform(MockMvcRequestBuilders.get("/phone")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("phoneIndex")).andDo(print());
	}
	

	@Test
	public void GetPhoneTest() throws Exception {
		ResultActions resultActions = this.springMvc.perform(get("/phone/{id}", 5)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("showPhone"));
		resultActions.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void GetPhoneJsonTest() throws Exception {
		this.springMvc.perform(MockMvcRequestBuilders.get("/phone/5?json=true").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print())
				.andExpect(content().contentTypeCompatibleWith("application/json"))
				.andExpect(jsonPath("$.id").value(5));
	}

	@Test
	public void CreatePhoneTest() throws Exception {
		this.springMvc.perform(
				post("/phone/?number=353142&description=testing&street=state&city=BBB&state=CCC&zip=95012&users[]=177")
				.accept(MediaType.ALL)).andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("/phone/*")).andDo(print());
	
//		MvcResult mvcResult = this.springMvc.perform(
//				post("/phone/?number=223118&description=test&street=test&city=test&state=CCC&zip=95012&users[]=177&users[]=583")
//				.accept(MediaType.ALL)).andDo(print())
//				.andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrlPattern("/phone/*"))
//				.andReturn();
//	    String headerValue = mvcResult.getResponse().getHeader("Location");
//	    System.out.println(headerValue);
	    
	}

	
	@Test
	public void UpdatePhoneTest() throws Exception {
		this.springMvc.perform(
				post("/phone/2?number=22513&description=testing&street=test&city=test&state=test&zip=95012&users[]=177&users[]=583")
				.accept(MediaType.ALL)).andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/phone/2")).andDo(print());
	}

	
	@Test
	public void DeletePhoneTest() throws Exception {
		this.springMvc.perform(delete("/phone/{id}", 5)).andDo(print())
		.andExpect(status().is2xxSuccessful());
	}


}
