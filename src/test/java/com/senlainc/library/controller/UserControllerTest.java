package com.senlainc.library.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.hibernate.cache.internal.StrategyCreatorRegionFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.library.config.H2PersistanceConfig;
import com.senlainc.library.config.PersistenceConfig;
import com.senlainc.library.config.WebConfig;
import com.senlainc.library.entity.User;
import com.senlainc.library.entity.UserInfo;
import com.senlainc.library.entity.UserRole;
import com.senlainc.library.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class,
		H2PersistanceConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
    public void getActiveProfiles() {
        
    }

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("userController"));
	}

	@Test
	public void create() throws Exception {
		
		UserInfo userInfo = new UserInfo("Andru", "Shymko", "yanshimko@gmail.com");
		UserRole role = new UserRole("admin");
		role.setId(1);
		User user = new User("55anshimko", "1234", role, userInfo);
		
		this.mockMvc.perform(post("/users")
				.content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		
	}
	
//	@Test
//	public void read() throws Exception {
//		
//		this.mockMvc.perform(get("/users/1")
//				.accept(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.login").value("anshimko"));
//		
//	}

	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println(jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
