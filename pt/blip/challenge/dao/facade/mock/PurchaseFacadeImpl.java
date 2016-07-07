package pt.blip.challenge.dao.facade.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.blip.challenge.dao.entity.Purchase;
import pt.blip.challenge.dao.facade.PurchaseFacade;

/**
 * Dummy implementation
 * @author carlos.t.ferreira
 *
 */
public class PurchaseFacadeImpl implements PurchaseFacade {

	private static final long serialVersionUID = -2371070022842462007L;

	private static Map<Long, Purchase> database = new HashMap<Long, Purchase>();
	
	public PurchaseFacadeImpl() {
		Purchase dummy = new Purchase(1L);
		dummy.setProductType("Type X");
		dummy.setExpires(Calendar.getInstance().getTime());
		
		database.put(dummy.getId(), dummy);
	}
	
	@Override
	public List<Purchase> findAll() {
		return Collections.list(Collections.enumeration(database.values()));
	}

	@Override
	public Purchase findById(Long id) {
		return database.get(id);
	}

	@Override
	public void insert(Purchase purchase) {
		database.put(purchase.getId(), purchase);
	}

	@Override
	public void delete(Purchase purchase) {
		database.remove(purchase.getId());
	}

	@Override
	public void update(Purchase purchase) {
		if( database.containsKey(purchase.getId()) ) {
			database.put(purchase.getId(), purchase);
		}
	}

	@Override
	public List<Purchase> findNotExpired(Date date) {
		List<Purchase> purchases = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Purchase purchase = new Purchase(1L);
		purchase.setProductType("Type A");
		cal.add(Calendar.DATE, 1);
		purchase.setExpires(cal.getTime());
		purchases.add(purchase);
		
		purchase = new Purchase(2L);
		purchase.setProductType("Type B");
		cal.add(Calendar.DATE, 1);
		purchase.setExpires(cal.getTime());
		purchases.add(purchase);
		
		purchase = new Purchase(3L);
		purchase.setProductType("Type C");
		cal.add(Calendar.DATE, 1);
		purchase.setExpires(cal.getTime());
		purchases.add(purchase);
		
		return purchases;
	}

}
