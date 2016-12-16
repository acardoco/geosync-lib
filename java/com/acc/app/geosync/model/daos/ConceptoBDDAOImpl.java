package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.Transformacion;




@Repository
public class ConceptoBDDAOImpl implements ConceptoBDDAO{
  
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int create(ConceptoBD conceptoBd) {
		int id = (int) sessionFactory.getCurrentSession().save(conceptoBd);
		return id;
	}

	@Override
	public void update(ConceptoBD conceptoBd) {
		
		sessionFactory.getCurrentSession().update(conceptoBd);
		
	}

	@Override
	public void delete(ConceptoBD conceptoBd) {
		
		sessionFactory.getCurrentSession().delete(conceptoBd);
		
	}

	@Override
	public ConceptoBD find(int idConceptoBd) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(ConceptoBD.class, idConceptoBd);
		return (ConceptoBD) categoriaO;
	}

	@Override
	public List<ConceptoBD> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from ConceptoBD  order by nombre");

		return (List<ConceptoBD>) queryResult.list();
	}

	@Override
	public List<Transformacion> findTransformacionByConceptoBDId(int idConceptoBD) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Transformacion  where idConceptoBD = :id").setInteger("id",idConceptoBD);

		return (List<Transformacion>) queryResult.list();
	}

}
