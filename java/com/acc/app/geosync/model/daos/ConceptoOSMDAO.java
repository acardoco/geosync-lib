package com.acc.app.geosync.model.daos;

import java.util.List;

import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.model.VOs.Transformacion;

public interface ConceptoOSMDAO {
	public int create(ConceptoOSM conceptoOSM); 
	public void update(ConceptoOSM conceptoOSM);
	public void delete(ConceptoOSM conceptoOSM);
	public ConceptoOSM find(int idConceptoOSM);
	public List<ConceptoOSM> findAll();
	//Filtros
	public List<Filtro> findFiltrosByConceptoOSMId(int idConceptoOSM);
	public List<FiltroClaveValor> findFiltrosClaveValorByConceptoOSMId(int idConceptoOSM);
	public List<FiltroNumerico> findFiltrosNumericosByConceptoOSMId(int idConceptoOSM);
	public List<FiltroExists> findFiltrosExistsByConceptoOSMId(int idConceptoOSM);
	//Transformaciones
	public List<Transformacion> findTransformacionByConceptoOSMId(int idConceptoOSM);

}
