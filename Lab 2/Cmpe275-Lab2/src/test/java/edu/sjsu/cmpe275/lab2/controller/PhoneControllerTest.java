package edu.sjsu.cmpe275.lab2.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
	private MockMvc objMockMvc;
	@InjectMocks
	private PhoneController phoneController;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.objMockMvc = MockMvcBuilders.standaloneSetup(phoneController).build();

	}
	
	@Test
	public void RenderPhoneHomeTest() throws Exception {
		this.objMockMvc.perform(MockMvcRequestBuilders.get("/phone")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("phoneIndex")).andDo(print());
	}

}
