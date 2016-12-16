package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;

public interface TrabajoDAO {

	public int create(Trabajo trabajo); 
	public void update(Trabajo trabajo);
	public void delete(Trabajo trabajo);
	public Trabajo find(int idTrabajo);
	public List<Trabajo> findAll();
	public List<Transformacion> findTransformacionByTrabajoId(int idTrabajo);
}
