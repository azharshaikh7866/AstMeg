package com.meganet.pan;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PanApplicationTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testRetrieveStudentCourse() throws JSONException {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/pan/verify?pan=eszps2465c"),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"code\":\"1\",\"PAN1\":\"eszps2465c\",\"PAN1STATUS\":\"E\",\"PAN1LASTNAME\":\"SHAIKH\",\"PAN1FIRSTNAME\":\"AZHAR\",\"PAN1MIDDLENAME\":\"ASHRAF\",\"PAN1TITLE\":\"Shri\",\"PAN1LASTUPDATEDATE\":\"05/03/2013\",\"PAN1FILLER1\":\"\",\"PAN1FILLER2\":\"\",\"PAN1FILLER3\":\"\"}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	public void testRetrieveInvalidPan() throws JSONException {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/pan/verify?pan=esasd2465c"),
				HttpMethod.GET, entity, String.class);

		String expected = "{\"code\":\"1\"}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
