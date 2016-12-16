package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;

public interface TransformacionDAO {
	public int create(Transformacion transformacion); 
	public void update(Transformacion transformacion);
	public void delete(Transformacion transformacion);
	public Transformacion find(int idTransformacion);
	public List<Transformacion> findAll();

}
