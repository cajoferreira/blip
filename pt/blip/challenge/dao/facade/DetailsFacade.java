package pt.blip.challenge.dao.facade;

import java.util.List;

import pt.blip.challenge.dao.entity.Details;
import pt.blip.challenge.dao.entity.Purchase;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public interface DetailsFacade extends GenericFacade<Details> {

	List<Details> findDetails(List<Purchase> purchases);
}
