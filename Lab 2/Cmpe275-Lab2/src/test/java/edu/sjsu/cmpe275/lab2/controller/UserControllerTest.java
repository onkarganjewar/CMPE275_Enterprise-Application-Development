package edu.sjsu.cmpe275.lab2.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import edu.sjsu.cmpe275.lab2.model.User;
/**
 * 
 * @author Shreya Prabhu Cmpe275-Lab2
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= UserController.class)

@WebAppConfiguration

public class UserControllerTest {
	private MockMvc objMockMvc;
	
	private User user;
	
	private MediaType contentType=new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setup()throws Exception{
		
		this.objMockMvc=webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void test_renderHome() throws Exception{
		objMockMvc.perform(get("/"))  
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.forwardedUrl("WEB-INF/views/home.jsp"));
		
	}
	
	@Test
	public void test_getUser() throws Exception{
		objMockMvc.perform(get("/user/20"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
		
	}
	
}
