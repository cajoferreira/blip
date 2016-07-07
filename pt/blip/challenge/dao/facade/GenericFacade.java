package pt.blip.challenge.dao.facade;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author carlos.t.ferreira
 *
 * @param <T>
 */
public interface GenericFacade<T> extends Serializable {

	List<T> findAll();

	T findById(Long id);
	
	void insert(T purchase);
	
	void delete(T purchase);
	
	void update(T purchase);
	
}
