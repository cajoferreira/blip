package pt.blip.challenge.dao.facade;

import pt.blip.challenge.dao.facade.mock.DetailsFacadeImpl;
import pt.blip.challenge.dao.facade.mock.PurchaseFacadeImpl;

/**
 * 
 * @author carlos.t.ferreira
 *
 */
public class FacadeFactory {

	/**
	 * 
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T extends GenericFacade<?>> T build(Class<T> c) {
		if( c.isAssignableFrom(DetailsFacade.class) ) {
			return (T) new DetailsFacadeImpl();
		} else if( c.isAssignableFrom(PurchaseFacade.class) ) {
			return (T) new PurchaseFacadeImpl();
		} else {
			throw new IllegalArgumentException("Invalid facade type");
		}
	}
}
