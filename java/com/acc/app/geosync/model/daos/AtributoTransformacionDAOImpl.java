package com.acc.app.geosync.model.daos;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;

@Repository
public class AtributoTransformacionDAOImpl implements AtributoTransformacionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public int create(AtributoTransformacion mapeadoAtributos) {
		int id = (int) sessionFactory.getCurrentSession().save(mapeadoAtributos);
		return id;
	}

	@Override
	public void update(AtributoTransformacion mapeadoAtributos) {
		sessionFactory.getCurrentSession().update(mapeadoAtributos);
	}

	@Override
	public void delete(AtributoTransformacion mapeadoAtributos) {
		sessionFactory.getCurrentSession().delete(mapeadoAtributos);

	}

	@Override
	public AtributoTransformacion find(int idAtributoBD) {
		Session session = sessionFactory.getCurrentSession();
		Object categoriaO = session.get(AtributoTransformacion.class, idAtributoBD);
		return (AtributoTransformacion) categoriaO;
	}

	@Override
	public List<AtributoTransformacion> findAtributoTransformacionByIdTransformacion(int idTransformacion) {
		Session session = sessionFactory.getCurrentSession();
		Query queryResult = session.createQuery("from AtributoTransformacion  where idTransformacion = :id")
				.setInteger("id", idTransformacion);

		return (List<AtributoTransformacion>) queryResult.list();
	}

}
