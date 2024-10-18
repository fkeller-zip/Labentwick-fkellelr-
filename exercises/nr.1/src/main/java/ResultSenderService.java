package com.cloudusage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ResultSenderService {

	public void sendResults(Map<String, Long> results) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/v1/results"; // Ziel-URL
		restTemplate.postForObject(url, results, String.class);
	}
}
