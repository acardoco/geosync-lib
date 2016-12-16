package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.AtributoBD;

@Repository
public class AtributoBDDAOImpl implements AtributoBDDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(AtributoBD atributoBD) {
		int id = (int) sessionFactory.getCurrentSession().save(atributoBD);
		return id;
	}

	@Override
	public void update(AtributoBD atributoBD) {
		sessionFactory.getCurrentSession().update(atributoBD);
		
	}

	@Override
	public void delete(AtributoBD atributoBD) {
		sessionFactory.getCurrentSession().delete(atributoBD);
		
	}

	@Override
	public AtributoBD find(int idAtributoBD) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(AtributoBD.class, idAtributoBD);
		return (AtributoBD) categoriaO;
	}

	@Override
	public List<AtributoBD> findAtributosBDByIdConceptoBD(int idConceptoBD) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from AtributoBD  where idConceptoBD = :id").setInteger("id",idConceptoBD);

		return (List<AtributoBD>) queryResult.list();
	}

}
