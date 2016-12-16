package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.Transformacion;

public interface ConceptoBDDAO {

	public int create(ConceptoBD conceptoBd); 
	public void update(ConceptoBD conceptoBd);
	public void delete(ConceptoBD conceptoBd);
	public ConceptoBD find(int idConceptoBd);
	public List<ConceptoBD> findAll();
	public List<Transformacion> findTransformacionByConceptoBDId(int idConceptoBD);

}
