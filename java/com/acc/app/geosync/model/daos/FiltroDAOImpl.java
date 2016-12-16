package com.acc.app.geosync.model.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.Filtro;

@Repository
public class FiltroDAOImpl implements FiltroDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public int create(Filtro Filtro) {
		int id = (int) sessionFactory.getCurrentSession().save(Filtro);
		return id;
	}

	@Override
	public void update(Filtro Filtro) {
		sessionFactory.getCurrentSession().update(Filtro);
		
	}

	@Override
	public void delete(Filtro Filtro) {
		sessionFactory.getCurrentSession().delete(Filtro);
		
	}

	@Override
	public Filtro find(int idFiltro) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(Filtro.class, idFiltro);
		return (Filtro) categoriaO;
	}

}
