package com.cloudusage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DataController {

	@GetMapping("/fetch-data")
	public Map<String, Object> getDataset() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/v1/dataset"; // API URL
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		return response;
	}
}
