package com.senlainc.library.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

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
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class,
		H2PersistenceConfig.class }, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
@Transactional
public class RentControllerTest {
	
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
	public void givenWac_whenServletContext_thenItProvidesRentController() {
		ServletContext servletContext = wac.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("rentController"));
	}

	@Test
	public void testRead() throws Exception {
		
		this.mockMvc.perform(get("/rents/book/{id}", "1")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].user.id", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is("19-03-2020")))
                .andExpect(jsonPath("$[0].returned", is(true)));
	}
	
	@Test
	public void testReadAvailable() throws Exception {
		this.mockMvc.perform(get("/rents/books/available?page=1&size=20")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("War and piec")));
	}

	@Test
	public void testReadBorrow() throws Exception {
		this.mockMvc.perform(get("/rents/borrow?page=1&size=20")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("War and piec")));
               
	}

	@Test
	public void testReadBorrowOverdue() throws Exception {
		this.mockMvc.perform(get("/rents/borrow/overdue?page=1&size=20")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("War and piec")));
	}

	@Test
	public void testCreate() throws Exception {
		
		User user = new User();
		user.setId(2);
		Book book = new Book();
		book.setId(1);
		RentHistory rent = new RentHistory(user, book, Date.valueOf("2020-06-17"),  Date.valueOf("2020-07-17"), false);
		rent.setId(5);
		rent.getUser().getRentHistories().add(rent);
		rent.getBook().getRentHistories().add(rent);
		
		this.mockMvc.perform(post("/rents")
				.content(ConverterObjectToJson.convert(rent))
				.contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void testReturned() throws Exception {
		
		RentHistory rent = new RentHistory();
		rent.setId(2);
		rent.setReturned(true);
		
		this.mockMvc.perform(put("/rents/book/{id}", 2)
				.content(ConverterObjectToJson.convert(rent))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
