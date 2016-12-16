package com.acc.app.geosync.service;

import java.util.List;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.Transformacion;

public interface TransformacionService {
	
	//TRANSFORMACION
	public int CrearTransformacion(Transformacion tranformacion);
	public void ActualizarTransformacion(Transformacion tranformacion);
	public void EliminarTransformacion(Transformacion tranformacion);
	public Transformacion BuscarTransformacion(int i);
	public List<Transformacion> TransformacionesTotales();
	public List<AtributoTransformacion> AtributoTransformacionesTotalesByTransformacionId(int i);
	//---ATRIBUTOS TRANSFORMACION
	public int CrearAtributoTransformacion(AtributoTransformacion Atributotranformacion);
	public void ActualizarAtributoTransformacion(AtributoTransformacion Atributotranformacion);
	public void EliminarAtributoTransformacion(AtributoTransformacion Atributotranformacion);
	public AtributoTransformacion BuscarAtributoTransformacion(int i);

}
