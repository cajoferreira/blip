package pt.blip.challenge.dao.facade;

import java.util.Date;
import java.util.List;

import pt.blip.challenge.dao.entity.Purchase;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public interface PurchaseFacade extends GenericFacade<Purchase> {

	List<Purchase> findNotExpired(Date date);
	
}
