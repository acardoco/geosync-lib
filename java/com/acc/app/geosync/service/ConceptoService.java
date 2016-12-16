package com.acc.app.geosync.service;

import java.util.List;

import com.acc.app.geosync.model.VOs.AtributoBD;
import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;

public interface ConceptoService {
	
	//-------CONCEPTO BD ----------------
	public int CrearConceptoBD( ConceptoBD concepto);
	public ConceptoBD BuscarConceptoBD(int i);
	public void EliminarConceptoBD( ConceptoBD concepto);
	public void ActualizarConceptoBD(ConceptoBD concepto);
	public List<ConceptoBD> BuscarConceptosBDTotales();
	public List<AtributoBD> BuscarAtributosBD(int idConceptoBD);
	//-------CONCEPTO OSM ----------------
	public int CrearConceptoOSM( ConceptoOSM concepto);
	public ConceptoOSM BuscarConceptoOSM(int i);
	public void EliminarConceptoOSM( ConceptoOSM concepto);
	public void ActualizarConceptoOSM(ConceptoOSM concepto);
	public List<ConceptoOSM> BuscarConceptosOSMTotales();
	public List<AtributoOSM> BuscarAtributosOSM(int idConceptoOSM);
	//------FILTROS DE CONCEPTO OSM--------
	public int CrearFiltro( Filtro concepto);
	public Filtro BuscarFiltro(int i);
	public void EliminarFiltro( Filtro concepto);
	public void ActualizarFiltro(Filtro concepto);
	public List<Filtro> BuscarFiltrosConceptoOSM(int i);
	public List<FiltroClaveValor> BuscarFiltrosClaveValorConceptoOSM(int i);
	public List<FiltroNumerico> BuscarFiltrosNumericosConceptoOSM(int i);
	public List<FiltroExists> BuscarFiltrosExistsConceptoOSM(int i);
	//------ATRIBUTOS BD-------------------
	public int CrearAtributoBD( AtributoBD atributoBD);
	public void EliminarAtributoBD( AtributoBD atributoBD);
	public void ActualizarAtributoBD(AtributoBD atributoBD);
	public AtributoBD BuscarAtributoBD(int i);
	//------ATRIBUTOS OSM-------------------
	public int CrearAtributoOSM( AtributoOSM atributoOSM);
	public void EliminarAtributoOSM( AtributoOSM atributoOSM);
	public void ActualizarAtributoOSM(AtributoOSM atributoOSM);
	public AtributoOSM BuscarAtributoOSM(int i);
	

}
