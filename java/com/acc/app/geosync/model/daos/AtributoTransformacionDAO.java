package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;


public interface AtributoTransformacionDAO {
	
	public int create(AtributoTransformacion mapeadoAtributos); 
	public void update(AtributoTransformacion mapeadoAtributos);
	public void delete(AtributoTransformacion mapeadoAtributos);
	public AtributoTransformacion find(int idAtributoBD);
	public List<AtributoTransformacion> findAtributoTransformacionByIdTransformacion(int idTransformacion);

}
