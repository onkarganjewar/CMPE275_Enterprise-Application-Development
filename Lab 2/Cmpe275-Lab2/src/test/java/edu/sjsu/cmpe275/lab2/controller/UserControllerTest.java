package edu.sjsu.cmpe275.lab2.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * 
 * @author Shreya Prabhu Cmpe275-Lab2
 *
 */
// @ContextConfiguration(loader = AnnotationConfigContextLoader.class,
// classes = { AppConfig.class, ServletConfig.class })
// "classpath:**/WEB-INF/spring-context.xml"

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/META-INF/persistence.xml",
		"file:src/main/webapp/WEB-INF/spring-context.xml", })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc objMockMvc;

//	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	// @Autowired
	// private ApplicationContext context;

	@InjectMocks
	private UserController userController;

	// @Autowired
	// private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		// this.objMockMvc =
		// MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
		this.objMockMvc = MockMvcBuilders.standaloneSetup(userController).build();

	}

	@Test
	public void RenderHomeTest() throws Exception {
		this.objMockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("home")).andDo(print());
	}

	@Test
	public void test_getUser() throws Exception {
		objMockMvc.perform(MockMvcRequestBuilders.get("/user/26")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("showUser")).andDo(print());
	}

	// @Test
	// public void test_renderHome() throws Exception{
	// objMockMvc.perform(get("/user"))
	// .andExpect(status().isOk())
	// .andExpect(MockMvcResultMatchers.forwardedUrl("home.jsp"));
	//
	// }

	// public void setApplicationContext(ApplicationContext applicationContext)
	// throws BeansException {
	// // TODO Auto-generated method stub
	// context = applicationContext;
	// }

}
