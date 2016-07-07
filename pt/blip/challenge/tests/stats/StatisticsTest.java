/**
 * 
 */
package pt.blip.challenge.tests.stats;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.blip.challenge.stats.Statistics;
import pt.blip.challenge.stats.StatisticsEntry;
import pt.blip.challenge.stats.StatisticsId;

/**
 * @author carlos.t.ferreira
 *
 */
public class StatisticsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompute() throws InterruptedException {
		Statistics statistics = Statistics.getInstance();
		
		StatisticsId id = StatisticsId.FETCH;
		
		StatisticsEntry entry = new StatisticsEntry(id);
		
		entry.markAsSuccessful();
		
		TimeUnit.MILLISECONDS.sleep(1000);
		
		statistics.reset();
		
		statistics.compute(entry);
		statistics.compute(new StatisticsEntry(StatisticsId.STORE));
		
		TimeUnit.MILLISECONDS.sleep(100);
		
		assertTrue(1L == statistics.getNumberOfRequests(id));
		assertTrue(1L == statistics.getNumberOfSuccessfulRequests(id));
		assertTrue(statistics.getAccumulatedExecutionTime(id) > 999L);
		assertTrue(statistics.getLongestExecutionTime(id) > 999L);
		assertTrue(statistics.getShortestExecutionTime(id) > 999L);
		assertTrue(statistics.getShortestExecutionTime(id) < Long.MAX_VALUE);
		assertTrue(statistics.getAverageExecutionTime(id) > 999L);
	}

	@Test
	public void testComputeWithNullEntry() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.compute(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNumberOfRequestsWithNullEntry() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getNumberOfRequests(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetNumberOfSuccessfulRequests() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getNumberOfSuccessfulRequests(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAccumulatedExecutionTime() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getAccumulatedExecutionTime(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetAverageExecutionTime() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getAverageExecutionTime(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetLongestExecutionTime() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getLongestExecutionTime(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetShortestExecutionTime() {
		Statistics statistics = Statistics.getInstance();
		
		statistics.getShortestExecutionTime(null);
	}
}
