package pt.blip.challenge.stats;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for computing all statistics.
 * All methods are thread safe.
 * 
 * @author carlos.t.ferreira
 *
 */
public class Statistics {

	private Logger logger = Logger.getLogger(Statistics.class.getName());
	
	private static Statistics INSTANCE = new Statistics();
	
	private final Map<StatisticsId, Counter> counters;
	
	private final Executor executor;
	
	/**
	 * Private constructor
	 */
	private Statistics() {
		counters = new ConcurrentHashMap<>();
		executor = Executors.newCachedThreadPool();
		
		// Populate map
		for (StatisticsId statisticsId : StatisticsId.values()) {
			counters.put(statisticsId, new Counter());
		}
	}
	
	/**
	 * Single instance per classloader
	 */
	public static Statistics getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Computes statistics for the entry, incrementing number of operations,
	 * calculating longest and shortest execution, and calculating the average time
	 * each time is taking to be processed.
	 * @param statisticsEntry
	 */
	public void compute(final StatisticsEntry statisticsEntry) {
		try {
			if( statisticsEntry != null ) {
				statisticsEntry.markEndTime();
				
				// Avoid bottleneck by computing on a separate thread
				executor.execute(new Runnable() {
					
					@Override
					public void run() {
						synchronized (counters) {
							Long duration = statisticsEntry.getEndTime() - statisticsEntry.getStartTime();
							
							// get the appropriate counter
							Counter counter = counters.get(statisticsEntry.getId());
							
							if( duration > counter.max ) {
								counter.max = duration;
							} 
							if( duration < counter.min ) {
								counter.min = duration;
							}
							
							counter.accumulated += duration;
							counter.totalRequests += 1;
							if( statisticsEntry.isSuccessful() ) {
								counter.successfulRequests += 1;
							}
							counter.average = (double) (counter.accumulated / counter.totalRequests);
						}
					}
				});
			}
		} catch (Exception e) {
			// we don't want computation errors to interfere with the method's execution
			logger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			// log current statistics
			logger.finest(this.toString());
		}
	}
	
	/**
	 * Resets all the counters
	 */
	public void reset() {
		synchronized (counters) {
			for (StatisticsId statisticsId : StatisticsId.values()) {
				counters.put(statisticsId, new Counter());
			}
		}
	}
	
	/**
	 * Returns the method's longest time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Longest execution time in milliseconds
	 */
	public Long getLongestExecutionTime(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).max;
	}
	
	/**
	 * Returns the method's shortest time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Shortest execution time in milliseconds
	 */
	public Long getShortestExecutionTime(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).min;
	}
	
	/**
	 * Returns the method's average time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Average execution time in milliseconds
	 */
	public Double getAverageExecutionTime(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).average;
	}
	
	/**
	 * Returns the method's accumulated time of execution
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Average execution time in milliseconds
	 */
	public Long getAccumulatedExecutionTime(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).accumulated;
	}
	
	/**
	 * Returns the method's total number of requests
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Total number of requests
	 */
	public Long getNumberOfRequests(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).totalRequests;
	}
	
	/**
	 * Returns the method's total number of successful requests
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Total number of successful requests
	 */
	public Long getNumberOfSuccessfulRequests(StatisticsId id) {
		validateId(id);
		
		return counters.get(id).successfulRequests;
	}

	/**
	 * 
	 * @param id
	 */
	private void validateId(StatisticsId id) {
		if( id == null ) {
			throw new IllegalArgumentException("Statistics ID must not be null");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Set<StatisticsId> keys = counters.keySet();
		
		for (StatisticsId statisticsId : keys) {
			Counter counter = counters.get(statisticsId);
			
			sb.append(statisticsId).append(" -> ");
			sb.append(counter).append(System.getProperty("line.separator"));
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @author carlos.t.ferreira
	 *
	 */
	private class Counter {
		private Long max = 0L;
		private Long min = Long.MAX_VALUE;
		private Double average = 0D;
		private Long accumulated = 0L;
		private Long totalRequests = 0L;
		private Long successfulRequests = 0L;
		
		@Override
		public String toString() {
			return String.format("[avr=%-7s ms] [acc=%-7s ms] [tot=%-7s] [suc=%-7s] [max=%-7s ms] [min=%-7s ms]", 
					average, accumulated, totalRequests, successfulRequests, max, min);
		}
	}
}
