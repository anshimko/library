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

import com.senlainc.library.configDB.H2PersistenceConfig;
import com.senlainc.library.ConverterObjectToJson;
import com.senlainc.library.config.WebConfig;
import com.senlainc.library.entity.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class,
		H2PersistenceConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
@Transactional
public class BookControllerTest {
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
		Assert.assertNotNull(wac.getBean("bookController"));
	}

	@Test
	public void createBook() throws Exception {
		
		Book book = new Book();
		book.setTitle("Fanny");
		
		this.mockMvc.perform(post("/books")
				.content(ConverterObjectToJson.convert(book))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void readBook() throws Exception {
		
		this.mockMvc.perform(get("/books/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("War and piec"));
		
	}
	
	@Test
	public void readAllBooks() throws Exception {
		
		this.mockMvc.perform(get("/books?page=1&size=20")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("War and piec")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].title", is("Monblan")));
		
	}
	
	@Test
	public void updateBook() throws Exception {
		
		Book book = new Book(1);
		book.setTitle("Fanny M");
		
		this.mockMvc.perform(put("/books")
				.content(ConverterObjectToJson.convert(book))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	public void deleteBook() throws Exception {
		
		this.mockMvc.perform(delete("/books/{id}", "2")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
}

