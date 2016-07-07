package pt.blip.challenge;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.blip.challenge.dao.entity.Details;
import pt.blip.challenge.dao.entity.Purchase;
import pt.blip.challenge.dao.facade.DetailsFacade;
import pt.blip.challenge.dao.facade.FacadeFactory;
import pt.blip.challenge.dao.facade.PurchaseFacade;
import pt.blip.challenge.format.FormatFactory;
import pt.blip.challenge.format.FormatTarget;
import pt.blip.challenge.format.FormatType;
import pt.blip.challenge.stats.Statistics;
import pt.blip.challenge.stats.StatisticsEntry;
import pt.blip.challenge.stats.StatisticsId;

/**
 * Application interface to process requests to fetch and store product purchases.<br />
 * It also computes and exposes the following metrics:
 * <ul>
 * <li> Longest execution time (by method);</li>
 * <li> Shortest execution time (by method);</li>
 * <li> Average execution time (by method);</li>
 * <li> Accumulated execution time (by method);</li>
 * <li> Total number of requests (by method);</li>
 * <li> Total number of successful requests (by method);</li>
 * </ul>
 * 
 * @author carlos.t.ferreira
 *
 */
public class RequestDispatcher {

	private Logger logger = Logger.getLogger(RequestDispatcher.class.getName());
	
	private final ResourceBundle bundle;
	
	/**
	 * Constructor. Defaults {@link Locale} to English.
	 */
	public RequestDispatcher() {
		this(Locale.ENGLISH);
	}
	
	/**
	 * Constructor
	 * @param locale {@link Locale} for output texts
	 */
	public RequestDispatcher(Locale locale) {
		if( locale == null ) {
			throw new IllegalArgumentException("Locale is mandatory");
		}
		bundle = ResourceBundle.getBundle("pt.blip.challenge.i18n.Texts", locale);
	}
	
	/**
	 * Fetches all purchase details that haven't yet expired
	 * @param outputFormat The output format, e.g. html, xml, json, etc.
	 * @return The formatted data
	 * @throws Exception
	 */
	public String fetchPurchaseDetails(String outputFormat) throws Exception {
		// initialize statistics
		StatisticsEntry statisticsEntry = new StatisticsEntry(StatisticsId.FETCH);
		
		String output = null;
		
		try {
			PurchaseFacade purchaseFacade = FacadeFactory.build(PurchaseFacade.class);
			
			// fetch valid purchases for the current time
			List<Purchase> purchases = purchaseFacade.findNotExpired(Calendar.getInstance().getTime());

			if( purchases != null && !purchases.isEmpty() ) {
				DetailsFacade detailsFacade = FacadeFactory.build(DetailsFacade.class);
				
				// fetch purchase details
				final List<Details> details = detailsFacade.findDetails(purchases);

				if( details != null && !details.isEmpty() ) {
					FormatType formatType = FormatType.lookup(outputFormat);
					
					// construct output
					output = FormatFactory.buildFormat(formatType).construct(new FormatTarget() {
						
						@Override
						public Object[][] organizeInput() {
							// data size plus header
							Object[][] input = new Object[details.size() + 1][];
							
							input[0] = new Object[] { 
									bundle.getString("id"), bundle.getString("description"), 
									bundle.getString("quantity"), bundle.getString("value")
							};
							
							for (int i = 1; i <= details.size(); i++) {
								Details detail = details.get(i - 1);
								
								input[i] = new Object[] {
									detail.getId(), detail.getDescription(), 
									detail.getQuantity(), detail.getValue()	
								};
							}
							
							return input;
						}
					});
				}
			}
			
			// it was a successful request
			statisticsEntry.markAsSuccessful();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			throw e;
		} finally {
			// compute statistics
			Statistics.getInstance().compute(statisticsEntry);
		}
		
		return output;
	}

	/**
	 * Inserts or updates the purchase
	 * @param purchase Record to be inserted/updated
	 * @throws Exception
	 */
	public void storePurchase(Purchase purchase) throws Exception {
		// initialize statistics
		StatisticsEntry statisticsEntry = new StatisticsEntry(StatisticsId.STORE);
		
		if( purchase == null ) {
			throw new IllegalArgumentException("Purchase must not be null");
		}
		
		try {
			PurchaseFacade purchaseFacade = FacadeFactory.build(PurchaseFacade.class);
			
			// verify if a purchase already exists and act accordingly
			if( purchaseFacade.findById(purchase.getId()) == null ) {
				purchaseFacade.insert(purchase);
			} else {
				purchaseFacade.update(purchase);
			}
			
			// it was a successful request
			statisticsEntry.markAsSuccessful();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			
			throw e;
		} finally {
			// compute statistics
			Statistics.getInstance().compute(statisticsEntry);
		}
	}

	/**
	 * Returns the method's longest time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Longest execution time in milliseconds
	 */
	public Long getLongestExecutionTime(StatisticsId id) {
		return Statistics.getInstance().getLongestExecutionTime(id);
	}
	
	/**
	 * Returns the method's shortest time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Shortest execution time in milliseconds
	 */
	public Long getShortestExecutionTime(StatisticsId id) {
		return Statistics.getInstance().getShortestExecutionTime(id);
	}
	
	/**
	 * Returns the method's average time to execute
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Average execution time in milliseconds
	 */
	public Double getAveragerExecutionTime(StatisticsId id) {
		return Statistics.getInstance().getAverageExecutionTime(id);
	}
	
	/**
	 * Returns the method's accumulated time of execution
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Average execution time in milliseconds
	 */
	public Long getAccumulatedExecutionTime(StatisticsId id) {
		return Statistics.getInstance().getAccumulatedExecutionTime(id);
	}
	
	/**
	 * Returns the method's total number of requests
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Total number of requests
	 */
	public Long getNumberOfRequests(StatisticsId id) {
		return Statistics.getInstance().getNumberOfRequests(id);
	}
	
	/**
	 * Returns the method's total number of successful requests
	 * @param id {@link StatisticsId} Method's id
	 * @return {@link Long} Total number of requests
	 */
	public Long getNumberOfSuccessfulRequests(StatisticsId id) {
		return Statistics.getInstance().getNumberOfSuccessfulRequests(id);
	}
}
