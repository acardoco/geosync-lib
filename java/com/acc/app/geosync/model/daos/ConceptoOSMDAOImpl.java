package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.model.VOs.Transformacion;

@Repository
public class ConceptoOSMDAOImpl implements ConceptoOSMDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(ConceptoOSM conceptoOSM) {
		int id = (int) sessionFactory.getCurrentSession().save(conceptoOSM);
		return id;
	}

	@Override
	public void update(ConceptoOSM conceptoOSM) {
		sessionFactory.getCurrentSession().update(conceptoOSM);
	}

	@Override
	public void delete(ConceptoOSM conceptoOSM) {
		sessionFactory.getCurrentSession().delete(conceptoOSM);
		
	}

	@Override
	public ConceptoOSM find(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(ConceptoOSM.class, idConceptoOSM);
		return (ConceptoOSM) categoriaO;
	}

	@Override
	public List<ConceptoOSM> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from ConceptoOSM  order by nombre");

		return (List<ConceptoOSM>) queryResult.list();
	}

	@Override
	public List<Filtro> findFiltrosByConceptoOSMId(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Filtro  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<Filtro>) queryResult.list();
	}


	@Override
	public List<FiltroClaveValor> findFiltrosClaveValorByConceptoOSMId(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from FiltroClaveValor  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<FiltroClaveValor>) queryResult.list();
	}

	@Override
	public List<FiltroNumerico> findFiltrosNumericosByConceptoOSMId(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from FiltroNumerico  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<FiltroNumerico>) queryResult.list();
	}

	@Override
	public List<FiltroExists> findFiltrosExistsByConceptoOSMId(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from FiltroExists  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<FiltroExists>) queryResult.list();
	}
	

	@Override
	public List<Transformacion> findTransformacionByConceptoOSMId(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Transformacion  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<Transformacion>) queryResult.list();
	}

}
