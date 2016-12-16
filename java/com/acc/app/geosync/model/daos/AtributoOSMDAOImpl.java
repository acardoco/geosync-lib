package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.AtributoOSM;

@Repository
public class AtributoOSMDAOImpl implements AtributoOSMDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(AtributoOSM atributoOSM) {
		int id = (int) sessionFactory.getCurrentSession().save(atributoOSM);
		return id;
	}

	@Override
	public void update(AtributoOSM atributoOSM) {
		sessionFactory.getCurrentSession().update(atributoOSM);
		
	}

	@Override
	public void delete(AtributoOSM atributoOSM) {
		sessionFactory.getCurrentSession().delete(atributoOSM);
		
	}

	@Override
	public AtributoOSM find(int idAtributoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(AtributoOSM.class, idAtributoOSM);
		return (AtributoOSM) categoriaO;
	}

	@Override
	public List<AtributoOSM> findAtributosOSMByIdConceptoOSM(int idConceptoOSM) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from AtributoOSM  where idConceptoOSM = :id").setInteger("id",idConceptoOSM);

		return (List<AtributoOSM>) queryResult.list();
	}


}
