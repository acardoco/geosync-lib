package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.Transformacion;

@Repository
public class TransformacionDAOImpl implements TransformacionDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(Transformacion transformacion) {
		int id = (int) sessionFactory.getCurrentSession().save(transformacion);
		return id;
	}

	@Override
	public void update(Transformacion transformacion) {
		sessionFactory.getCurrentSession().update(transformacion);
		
	}

	@Override
	public void delete(Transformacion transformacion) {
		sessionFactory.getCurrentSession().delete(transformacion);
		
	}

	@Override
	public Transformacion find(int idTransformacion) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(Transformacion.class, idTransformacion);
		return (Transformacion) categoriaO;
	}

	@Override
	public List<Transformacion> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Transformacion  order by nombre");

		return (List<Transformacion>) queryResult.list();
	}

}
