public class UsageAggregator {
	public long calculateTotalUsage(List<Event> events) {
		Map<String, Long> usageMap = new HashMap<>();

		for (Event event : events) {
			String key = event.getCustomerId() + "-" + event.getWorkloadId();
			if (event.getEventType().equals("start")) {
				usageMap.put(key, event.getTimestamp());
			} else if (event.getEventType().equals("stop")) {
				long startTime = usageMap.getOrDefault(key, 0L);
				long duration = event.getTimestamp() - startTime;
				usageMap.put(key, duration);
			}
		}

		return usageMap.values().stream().mapToLong(Long::longValue).sum();
	}
}
