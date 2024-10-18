package com.cloudusage.controller;

import com.cloudusage.service.UsageCalculationService;
import com.cloudusage.service.ResultSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AggregationController {

	@Autowired
	private UsageCalculationService calculationService;

	@Autowired
	private ResultSenderService resultSenderService;

	@GetMapping("/process-data")
	public void processData() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/v1/dataset";
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);
		List<Map<String, Object>> events = (List<Map<String, Object>>) response.get("events");

		// Berechne die Nutzungszeit
		Map<String, Long> usage = calculationService.calculateUsage(events);

		// Sende die Ergebnisse an das Referenzsystem
		resultSenderService.sendResults(usage);
	}
}
