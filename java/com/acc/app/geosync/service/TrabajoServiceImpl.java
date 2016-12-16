package com.acc.app.geosync.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.model.daos.ConceptoOSMDAO;
import com.acc.app.geosync.model.daos.TrabajoDAO;

@Service
@Transactional()
public class TrabajoServiceImpl implements TrabajoService {

	@Autowired
	private TrabajoDAO trabajoDAO;

	@Autowired
	private ConceptoOSMDAO conceptoOSMDAO;

	@Override
	public int CrearTrabajo(Trabajo trabajo) {
		int id;
		try {
			id = trabajoDAO.create(trabajo);
			System.out.println("Creando trabajo");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public void ActualizarTrabajo(Trabajo trabajo) {
		try {
			trabajoDAO.update(trabajo);
			// log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			// log.error("Error al dar de alta una categoría: " +
			// minhaCategoria.toString());
			throw e;
		}

	}

	@Override
	public void EliminarTrabajo(Trabajo trabajo) {
		try {
			trabajoDAO.delete(trabajo);
			// log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			// log.error("Error al dar de alta una categoría: " +
			// minhaCategoria.toString());
			throw e;
		}

	}

	@Override
	public Trabajo BuscarTrabajo(int idTrabajo) {
		Trabajo trabajo = null;
		try {
			trabajo = trabajoDAO.find(idTrabajo);
			// log.info("Encontrada categoría con id: " +
			// categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("trabajo " + idTrabajo + " no encontrado");
			// log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return trabajo;
	}

	@Override
	public List<Trabajo> TrabajosTotales() {
		List<Trabajo> trabajos = null;
		try {
			trabajos = trabajoDAO.findAll();
			// log.info("Encontradas todas las categorias");
		} catch (DataAccessException e) {
			// log.error("Error al buscar todas las categorias");
			throw e;
		}
		return trabajos;
	}

	@Override
	public List<Transformacion> BuscarTransformacionesDeTrabajo(int idTrabajo) {
		List<Transformacion> tranformaciones = null;
		try {
			tranformaciones = trabajoDAO.findTransformacionByTrabajoId(idTrabajo);
			// log.info("Encontradas todas las categorias");
		} catch (DataAccessException e) {
			// log.error("Error al buscar todas las categorias");
			throw e;
		}
		return tranformaciones;
	}


}
