package com.acc.app.geosync.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.model.daos.AtributoTransformacionDAO;
import com.acc.app.geosync.model.daos.TransformacionDAO;


@Service
@Transactional()
public class TransformacionServiceImpl implements TransformacionService{
	@Autowired
	private TransformacionDAO transformacionDAO;
	@Autowired
	private AtributoTransformacionDAO atributoTransformacionDAO;
	
	@Override
	public int CrearTransformacion(Transformacion transformacion) {
		int id;
		try {
		 id = transformacionDAO.create(transformacion);
		 System.out.println("Creando transformacion");
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			System.out.println("Fallo al crear transformacion");
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;

		}
		return id;
	}

	@Override
	public void ActualizarTransformacion(Transformacion transformacion) {
		try {
			transformacionDAO.update(transformacion);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void EliminarTransformacion(Transformacion transformacion) {
		try {
			transformacionDAO.delete(transformacion);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public Transformacion BuscarTransformacion(int i) {
		Transformacion transformacion = null;
		try {
			transformacion = transformacionDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("transformacion "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return transformacion;
	}

	@Override
	public List<Transformacion> TransformacionesTotales() {
		List<Transformacion> transformacions = null;
		try {
			transformacions = transformacionDAO.findAll();
			//log.info("Encontradas todas las categorias");
		} catch (DataAccessException e) {
			//log.error("Error al buscar todas las categorias");
			throw e;
		}
		return transformacions;
	}

	@Override
	public List<AtributoTransformacion> AtributoTransformacionesTotalesByTransformacionId(int i) {
		List<AtributoTransformacion> transformacions = null;
		try {
			transformacions = atributoTransformacionDAO.findAtributoTransformacionByIdTransformacion(i);
			//log.info("Encontradas todas las categorias");
		} catch (DataAccessException e) {
			//log.error("Error al buscar todas las categorias");
			throw e;
		}
		return transformacions;
	}

	@Override
	public int CrearAtributoTransformacion(AtributoTransformacion Atributotranformacion) {
		int id;
		try {
		 id = atributoTransformacionDAO.create(Atributotranformacion);
		 System.out.println("Creando Atributo transformacion");
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			System.out.println("Fallo al crear atributo transformacion");
			throw e;

		}
		return id;
	}

	@Override
	public void ActualizarAtributoTransformacion(AtributoTransformacion Atributotranformacion) {
		try {
			atributoTransformacionDAO.update(Atributotranformacion);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void EliminarAtributoTransformacion(AtributoTransformacion Atributotranformacion) {
		try {
			atributoTransformacionDAO.delete(Atributotranformacion);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public AtributoTransformacion BuscarAtributoTransformacion(int i) {
		AtributoTransformacion transformacion = null;
		try {
			transformacion = atributoTransformacionDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("Atributo transformacion "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return transformacion;
	}

}
