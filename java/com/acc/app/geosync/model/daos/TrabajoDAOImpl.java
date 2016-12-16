package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;

@Repository
public class TrabajoDAOImpl implements TrabajoDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(Trabajo trabajo) {
		int id =  (int) sessionFactory.getCurrentSession().save(trabajo);
		return id;
	}

	@Override
	public void update(Trabajo trabajo) {
		sessionFactory.getCurrentSession().update(trabajo);
		
	}

	@Override
	public void delete(Trabajo trabajo) {
		sessionFactory.getCurrentSession().delete(trabajo);
		
	}

	@Override
	public Trabajo find(int idTrabajo) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(Trabajo.class, idTrabajo);
		return (Trabajo) categoriaO;
	}

	@Override
	public List<Trabajo> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Trabajo  order by nombre");

		return (List<Trabajo>) queryResult.list();
	}

	@Override
	public List<Transformacion> findTransformacionByTrabajoId(int idTrabajo) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from Transformacion  where idTrabajo = :id").setInteger("id",idTrabajo);

		return (List<Transformacion>) queryResult.list();
	}

}
