package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.AtributoOSM;

public interface AtributoOSMDAO {
	
	public int create(AtributoOSM atributoOSM); 
	public void update(AtributoOSM atributoOSM);
	public void delete(AtributoOSM atributoOSM);
	public AtributoOSM find(int idAtributoOSM);
	public List<AtributoOSM> findAtributosOSMByIdConceptoOSM(int idConceptoOSM);

}
