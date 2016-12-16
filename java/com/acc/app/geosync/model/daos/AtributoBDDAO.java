package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.AtributoBD;

public interface AtributoBDDAO {
	
	public int create(AtributoBD atributoBD); 
	public void update(AtributoBD atributoBD);
	public void delete(AtributoBD atributoBD);
	public AtributoBD find(int idAtributoBD);
	public List<AtributoBD> findAtributosBDByIdConceptoBD(int idConceptoBD);

}
