package com.senlainc.library.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.senlainc.library.entity.User;

import org.junit.Test;

public class UserControllerTest {

	@Test
	public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
			throws ClientProtocolException, IOException {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String encoding = passwordEncoder.encode("anshimko" + ":" + "1234");
		
		System.out.println(encoding);
		
		// Given
		HttpUriRequest request = new HttpGet("http://localhost:8080/library/users/1");
		request.setHeader(HttpHeaders.AUTHORIZATION, "Basic YW5zaGlta286MTIzNA==");

		// When
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		// Then
		User resource = RetrieveUtil.retrieveResourceFromResponse(response, User.class);
		assertThat("anshimko", Matchers.is(resource.getLogin()));
	}

}
