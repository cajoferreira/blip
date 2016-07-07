/**
 * 
 */
package pt.blip.challenge.tests;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pt.blip.challenge.RequestDispatcher;
import pt.blip.challenge.dao.entity.Purchase;
import pt.blip.challenge.dao.facade.FacadeFactory;
import pt.blip.challenge.dao.facade.PurchaseFacade;
import pt.blip.challenge.stats.Statistics;
import pt.blip.challenge.stats.StatisticsId;

/**
 * @author carlos.t.ferreira
 *
 */
public class RequestDispatcherTest {

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
	public void testFetchPurchaseDetails() throws Exception {
		RequestDispatcher requestDispatcher = new RequestDispatcher();
		
		String json = "{\"data\":[{\"ID\":\"1\",\"Description\":\"Detail #1\",\"Quantity\":\"10\",\"Value\":\"100.0\"},{\"ID\":\"2\",\"Description\":\"Detail #2\",\"Quantity\":\"20\",\"Value\":\"200.0\"},{\"ID\":\"3\",\"Description\":\"Detail #3\",\"Quantity\":\"30\",\"Value\":\"300.0\"}]}";
		String html = "<html><head></head><body><table><tr><td>ID</td><td>Description</td><td>Quantity</td><td>Value</td></tr><tr><td>1</td><td>Detail #1</td><td>10</td><td>100.0</td></tr><tr><td>2</td><td>Detail #2</td><td>20</td><td>200.0</td></tr><tr><td>3</td><td>Detail #3</td><td>30</td><td>300.0</td></tr></table></body></html>";
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rows><row><column>ID</column><column>Description</column><column>Quantity</column><column>Value</column></row><row><column>1</column><column>Detail #1</column><column>10</column><column>100.0</column></row><row><column>2</column><column>Detail #2</column><column>20</column><column>200.0</column></row><row><column>3</column><column>Detail #3</column><column>30</column><column>300.0</column></row></rows>";
		String plain = "ID Description Quantity Value \r\n1 Detail #1 10 100.0 \r\n2 Detail #2 20 200.0 \r\n3 Detail #3 30 300.0 ";
		
		assertEquals(json, requestDispatcher.fetchPurchaseDetails("json"));
		assertEquals(html, requestDispatcher.fetchPurchaseDetails("html"));
		assertEquals(xml, requestDispatcher.fetchPurchaseDetails("xml"));
		assertEquals(plain, requestDispatcher.fetchPurchaseDetails(null));
		
		TimeUnit.MILLISECONDS.sleep(100);
		
		Statistics statistics = Statistics.getInstance();
				
		StatisticsId id = StatisticsId.FETCH;
		
		assertTrue(4L == statistics.getNumberOfRequests(id));
		assertTrue(4L == statistics.getNumberOfSuccessfulRequests(id));
	}
	
	@Test
	public void testStorePurchase() throws Exception {
		RequestDispatcher requestDispatcher = new RequestDispatcher();
		
		PurchaseFacade facade = FacadeFactory.build(PurchaseFacade.class);
		
		Purchase purchase = facade.findById(1L);
		purchase.setProductType("MOCK");
		
		requestDispatcher.storePurchase(purchase);

		assertEquals("MOCK", facade.findById(1L).getProductType());
		
		purchase = new Purchase(2L);
		purchase.setProductType("MOCK2");

		requestDispatcher.storePurchase(purchase);

		assertEquals("MOCK2", facade.findById(2L).getProductType());

		TimeUnit.MILLISECONDS.sleep(100);
		
		Statistics statistics = Statistics.getInstance();
				
		StatisticsId id = StatisticsId.STORE;
		
		assertTrue(2L == statistics.getNumberOfRequests(id));
		assertTrue(2L == statistics.getNumberOfSuccessfulRequests(id));
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testWithNullLocale() {
		new RequestDispatcher(null);
	}
}
