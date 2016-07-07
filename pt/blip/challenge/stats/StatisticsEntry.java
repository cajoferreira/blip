package pt.blip.challenge.stats;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public class StatisticsEntry {

	private StatisticsId id;
	private Long startTime;
	private Long endTime;
	private Boolean successful;
	
	/**
	 * Constructor
	 * @param id Allows the grouping of common statistics
	 */
	public StatisticsEntry(StatisticsId id) {
		if( id == null ) {
			throw new IllegalArgumentException("ID must not be null");
		}
		
		this.id = id;
		
		startTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
	}
	
	/**
	 * Registers the end of execution
	 */
	void markEndTime() {
		if( endTime == null ) {
			endTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
		}
	}
	
	/**
	 * Registers as a successful request
	 */
	public void markAsSuccessful() {
		if( successful == null ) {
			successful = Boolean.TRUE;
		}
	}

	/**
	 * Returns the timestamp when the execution started
	 * @return {@link Long} timestamp in milliseconds
	 */
	public Long getStartTime() {
		return startTime;
	}

	/**
	 * Returns the timestamp when the execution started
	 * @return {@link Long} timestamp in milliseconds
	 */
	public Long getEndTime() {
		return endTime;
	}

	/**
	 * Returns the ID of this execution entry
	 * @return {@link StatisticsId} ID
	 */
	public StatisticsId getId() {
		return id;
	}

	/**
	 * Returns the execution's status
	 * @return {@code true} if successful, {@code false} otherwise
	 */
	public Boolean isSuccessful() {
		return successful != null && successful;
	}

	@Override
	public String toString() {
		return "StatisticsEntry [id=" + id + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", successful=" + successful + "]";
	}
}
