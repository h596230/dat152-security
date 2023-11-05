package no.hvl.dat152.rest.ws.main.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.Response;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestsAdminUser {
	
	private String API_ROOT = "http://localhost:8090/elibrary/api/v1/admin";
	
	private String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiZXJpdEBlbWFpbC5jb20iLCJpc3MiOiJEQVQxNTItTGVjdHVyZXJAVERPWSIs"
			+ "ImZpcnN0bmFtZSI6IkJlcml0IiwibGFzdG5hbWUiOiJKw7hyZ2Vuc2VuIiwicm9sZXMiOlsiU1VQRVJfQURNSU4iLCJVU0VSIiwiQURNSU"
			+ "4iXSwiaWF0IjoxNjk2MjAwMTk3LCJleHAiOjE2OTYyODY1OTd9.eLWV51s4rtauq04wiB_ohBMdpfC7k3D9gHPpIqPUmFinV_VBvI0iuHT"
			+ "ev4Xc7Sq5qiSV7aAG3abY96Hu-mG5r23EVFy1Ipwq8t_02-mCzvIcSh25TlcHWUtfzKqM2UPcAwnvZnpvjhDYyPUnKgTN42uZn-msSO0N3"
			+ "zo8XT859Eylh2OsRWKla8UYXEz_GttR4cQ1--0-aIIAvOgYk8HmgriOIpQs4WuolzfLsMelk0xEo_C5vMv3XEpaWrKOOhjUl7GwqqkR7wT"
			+ "_yISPlLHj28ZcUAgGbKa_R73Fm8br8tgwOaz_nHpi25Nf_VAlqeBKsb5Z8rykM1WVzb9Va242IA";
	
	@DisplayName("JUnit test for @PutMapping(/users/{id}) endpoint")
	@Test
	public void AupdateUserRole_thenOK() {
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ token)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.queryParam("role", "admin")
				.put(API_ROOT+"/users/{id}", 1);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertEquals("robert@email.com", response.jsonPath().get("email"));
	}
	
	@DisplayName("JUnit test for @DeleteMapping(/users/{id}) endpoint")
	@Test
	public void BdeleteUserRole_thenOK() {
		
		Response response = RestAssured.given()
				.header("Authorization", "Bearer "+ token)
				.queryParam("role", "user")
				.delete(API_ROOT+"/users/{id}", 1);
	    
	    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	    assertFalse(response.jsonPath().getList("roles").get(0).toString().contains("USER"));
	}

}
