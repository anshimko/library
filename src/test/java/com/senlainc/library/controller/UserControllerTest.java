package com.senlainc.library.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.senlainc.library.config.H2PersistenceConfig;
import com.senlainc.library.config.WebConfig;
import com.senlainc.library.entity.User;
import com.senlainc.library.entity.UserInfo;
import com.senlainc.library.entity.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class,
		H2PersistenceConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
@Transactional
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void givenWac_whenServletContext_thenItProvidesUserController() {
		ServletContext servletContext = wac.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("userController"));
	}

	@Test
	public void createUser() throws Exception {
		
		UserInfo userInfo = new UserInfo("Andru", "Shymko", "yanshimko@gmail.com");
		UserRole role = new UserRole("admin");
		role.setId(1);
		User user = new User("55anshimko", "1234", role, userInfo);
		
		this.mockMvc.perform(post("/users")
				.content(ConverterObjectToJson.convert(user))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void readUser() throws Exception {
		
		this.mockMvc.perform(get("/users/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.login").value("anshimko"));
		
	}
	
	@Test
	public void readAllUsers() throws Exception {
		
		this.mockMvc.perform(get("/users/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].login", is("anshimko")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].login", is("dandy")));
		
	}
	
	@Test
	public void updateUser() throws Exception {
		
		UserInfo userInfo = new UserInfo("Andru", "Shymko", "himko@gmail.com");
		UserRole role = new UserRole("admin");
		role.setId(1);
		User user = new User("bajron", "1234", role, userInfo);
		user.setId(1);
		
		this.mockMvc.perform(put("/users")
				.content(ConverterObjectToJson.convert(user))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteUser() throws Exception {
		
		this.mockMvc.perform(delete("/users/{id}", "2")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
