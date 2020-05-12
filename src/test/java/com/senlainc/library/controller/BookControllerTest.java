package com.senlainc.library.controller;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.senlainc.library.config.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
public class BookControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
	 private static final String CONTENT_TYPE = "application/json";

	    @Before
	    public void setup() throws Exception {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	    }
	    
	//    @Test
	    public void givenWac_whenServletContext_thenItProvidesGreetController() {
	        final ServletContext servletContext = wac.getServletContext();
	        Assert.assertNotNull(servletContext);
	        Assert.assertTrue(servletContext instanceof MockServletContext);
	        Assert.assertNotNull(wac.getBean("greetController"));
	    }

}
