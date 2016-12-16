package com.acc.app.geosync.service;

import java.util.List;

import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;

public interface TrabajoService {
	
	public int CrearTrabajo(Trabajo trabajo);
	public void ActualizarTrabajo(Trabajo trabajo);
	public void EliminarTrabajo(Trabajo trabajo);
	public Trabajo BuscarTrabajo(int i);
	public List<Trabajo> TrabajosTotales();
	public List<Transformacion> BuscarTransformacionesDeTrabajo(int idTrabajo);
}
