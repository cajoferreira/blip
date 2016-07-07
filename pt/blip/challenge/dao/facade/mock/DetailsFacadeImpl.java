package pt.blip.challenge.dao.facade.mock;

import java.util.ArrayList;
import java.util.List;

import pt.blip.challenge.dao.entity.Details;
import pt.blip.challenge.dao.entity.Purchase;
import pt.blip.challenge.dao.facade.DetailsFacade;

/**
 * Dummy implementation
 * @author carlos.t.ferreira
 *
 */
public class DetailsFacadeImpl implements DetailsFacade {

	private static final long serialVersionUID = 957479324118983443L;

	@Override
	public List<Details> findAll() {
		return null;
	}

	@Override
	public Details findById(Long id) {
		return null;
	}

	@Override
	public void insert(Details purchase) {
		
	}

	@Override
	public void delete(Details purchase) {
		
	}

	@Override
	public void update(Details purchase) {
		
	}

	@Override
	public List<Details> findDetails(List<Purchase> purchases) {
		List<Details> details = new ArrayList<>();
		
		Details d = new Details(1L);
		d.setDescription("Detail #" + d.getId());
		d.setQuantity(10);
		d.setValue(100.00);
		details.add(d);
		
		d = new Details(2L);
		d.setDescription("Detail #" + d.getId());
		d.setQuantity(20);
		d.setValue(200.00);
		details.add(d);
		
		d = new Details(3L);
		d.setDescription("Detail #" + d.getId());
		d.setQuantity(30);
		d.setValue(300.00);
		details.add(d);
		
		return details;
	}

}
