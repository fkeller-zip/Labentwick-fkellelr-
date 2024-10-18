package com.cloudusage.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsageCalculationService {

	public Map<String, Long> calculateUsage(List<Map<String, Object>> events) {
		Map<String, Long> usageMap = new HashMap<>();
		Map<String, Long> startTimes = new HashMap<>();

		for (Map<String, Object> event : events) {
			String customerId = (String) event.get("customerId");
			String workloadId = (String) event.get("workloadId");
			long timestamp = (Long) event.get("timestamp");
			String eventType = (String) event.get("eventType");

			String key = customerId + "-" + workloadId;

			if ("start".equals(eventType)) {
				startTimes.put(key, timestamp);
			} else if ("stop".equals(eventType)) {
				if (startTimes.containsKey(key)) {
					long usage = timestamp - startTimes.get(key);
					usageMap.put(customerId, usageMap.getOrDefault(customerId, 0L) + usage);
					startTimes.remove(key);
				}
			}
		}
		return usageMap;
	}
}
