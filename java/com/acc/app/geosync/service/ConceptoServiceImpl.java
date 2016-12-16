package com.acc.app.geosync.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.app.geosync.model.VOs.AtributoBD;
import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.model.daos.AtributoBDDAO;
import com.acc.app.geosync.model.daos.AtributoOSMDAO;
import com.acc.app.geosync.model.daos.ConceptoBDDAO;
import com.acc.app.geosync.model.daos.ConceptoOSMDAO;
import com.acc.app.geosync.model.daos.FiltroDAO;


@Service
@Transactional()
public class ConceptoServiceImpl implements ConceptoService{

	@Autowired
	private ConceptoBDDAO conceptoBDDAO;
	@Autowired
	private ConceptoOSMDAO conceptoOSMDAO;
	@Autowired
	private FiltroDAO filtroDAO;
	@Autowired
	private AtributoBDDAO atributoBDDAO;
	@Autowired
	private AtributoOSMDAO atributoOSMDAO;
	
	@Override
	public int CrearConceptoBD(ConceptoBD concepto) {
		int id;
		try {
		 id = conceptoBDDAO.create(concepto);
		 System.out.println("Creando conceptoBD");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public ConceptoBD BuscarConceptoBD(int i) {
		ConceptoBD conceptoBD = null;
		try {
			conceptoBD = conceptoBDDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("conceptoBD "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return conceptoBD;
	}

	@Override
	public void EliminarConceptoBD(ConceptoBD concepto) {
		try {
			conceptoBDDAO.delete(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void ActualizarConceptoBD(ConceptoBD concepto) {
		try {
			conceptoBDDAO.update(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public List<ConceptoBD> BuscarConceptosBDTotales() {
		List<ConceptoBD> conceptoBDs = null;
		try {
			conceptoBDs = conceptoBDDAO.findAll();
			//log.info("Encontradas todas las categorias");
		} catch (DataAccessException e) {
			//log.error("Error al buscar todas las categorias");
			throw e;
		}
		return conceptoBDs;
	}
	/*
	 * OSMMMMMMMMMMMMMMMM
	 * 
	 * (non-Javadoc)
	 * @see com.acc.app.geosync.service.ConceptoService#CrearConceptoOSM(com.acc.app.geosync.model.VOs.ConceptoOSM)
	 */
	@Override
	public int CrearConceptoOSM(ConceptoOSM concepto) {
		int id;
		try {
		 id = conceptoOSMDAO.create(concepto);
		 System.out.println("Creando conceptoOSM");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public ConceptoOSM BuscarConceptoOSM(int i) {
		ConceptoOSM conceptoOSM = null;
		try {
			conceptoOSM = conceptoOSMDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("conceptoOSM "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return conceptoOSM;
	}

	@Override
	public void EliminarConceptoOSM(ConceptoOSM concepto) {
		try {
			conceptoOSMDAO.delete(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void ActualizarConceptoOSM(ConceptoOSM concepto) {
		try {
			conceptoOSMDAO.update(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public List<ConceptoOSM> BuscarConceptosOSMTotales() {
		List<ConceptoOSM> conceptoOSMs = null;
		try {
			conceptoOSMs = conceptoOSMDAO.findAll();
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}

	@Override
	public List<Filtro> BuscarFiltrosConceptoOSM(int i) {
		List<Filtro> conceptoOSMs = null;
		try {
			conceptoOSMs = conceptoOSMDAO.findFiltrosByConceptoOSMId(i);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}
	
	@Override
	public List<FiltroClaveValor> BuscarFiltrosClaveValorConceptoOSM(int i) {
		List<FiltroClaveValor> conceptoOSMs = null;
		try {
			conceptoOSMs = conceptoOSMDAO.findFiltrosClaveValorByConceptoOSMId(i);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}

	@Override
	public List<FiltroNumerico> BuscarFiltrosNumericosConceptoOSM(int i) {
		List<FiltroNumerico> conceptoOSMs = null;
		try {
			conceptoOSMs = conceptoOSMDAO.findFiltrosNumericosByConceptoOSMId(i);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}

	@Override
	public List<FiltroExists> BuscarFiltrosExistsConceptoOSM(int i) {
		List<FiltroExists> conceptoOSMs = null;
		try {
			conceptoOSMs = conceptoOSMDAO.findFiltrosExistsByConceptoOSMId(i);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}
	

	/*
	 * FILTROSSSSSSSSSSSSSSSSSSSss
	 * (non-Javadoc)
	 * @see com.acc.app.geosync.service.ConceptoService#CrearFiltro(com.acc.app.geosync.model.VOs.FiltroClaveValor)
	 */
	@Override
	public int CrearFiltro(Filtro concepto) {
		int id;
		try {
		 id = filtroDAO.create(concepto);
		 System.out.println("Creando filtro");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public Filtro BuscarFiltro(int i) {
		Filtro filtro= null;
		try {
			filtro = filtroDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("filtro "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return filtro;
	}

	@Override
	public void EliminarFiltro(Filtro concepto) {
		try {
			filtroDAO.delete(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void ActualizarFiltro(Filtro concepto) {
		try {
			filtroDAO.update(concepto);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public List<AtributoBD> BuscarAtributosBD(int idConceptoBD) {
		List<AtributoBD> conceptoOSMs = null;
		try {
			conceptoOSMs = atributoBDDAO.findAtributosBDByIdConceptoBD(idConceptoBD);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}

	@Override
	public List<AtributoOSM> BuscarAtributosOSM(int idConceptoOSM) {
		List<AtributoOSM> conceptoOSMs = null;
		try {
			conceptoOSMs = atributoOSMDAO.findAtributosOSMByIdConceptoOSM(idConceptoOSM);
		} catch (DataAccessException e) {
			throw e;
		}
		return conceptoOSMs;
	}

	@Override
	public int CrearAtributoBD(AtributoBD atributoBD) {
		int id;
		try {
		 id = atributoBDDAO.create(atributoBD);
		 System.out.println("Creando atributo BD");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public void EliminarAtributoBD(AtributoBD atributoBD) {
		try {
			atributoBDDAO.delete(atributoBD);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void ActualizarAtributoBD(AtributoBD atributoBD) {
		try {
			atributoBDDAO.update(atributoBD);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public int CrearAtributoOSM(AtributoOSM atributoOSM) {
		int id;
		try {
		 id = atributoOSMDAO.create(atributoOSM);
		 System.out.println("Creando atributo OSM");
		} catch (DataAccessException e) {
			throw e;
		}
		return id;
	}

	@Override
	public void EliminarAtributoOSM(AtributoOSM atributoOSM) {
		try {
			atributoOSMDAO.delete(atributoOSM);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public void ActualizarAtributoOSM(AtributoOSM atributoOSM) {
		try {
			atributoOSMDAO.update(atributoOSM);
			//log.info("Dada de alta categoría: " + minhaCategoria.toString());
		} catch (DataAccessException e) {
			//log.error("Error al dar de alta una categoría: " + minhaCategoria.toString());
			throw e;
		}
		
	}

	@Override
	public AtributoBD BuscarAtributoBD(int i) {
		AtributoBD filtro= null;
		try {
			filtro = atributoBDDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("AtributoBD "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return filtro;
	}

	@Override
	public AtributoOSM BuscarAtributoOSM(int i) {
		AtributoOSM filtro= null;
		try {
			filtro = atributoOSMDAO.find(i);
			//log.info("Encontrada categoría con id: " + categoria.getIdCategoria());
		} catch (Exception e) {
			System.out.println("AtributoOSM "+ i+" no encontrado");
			//log.error("NO ENCONTRADA la categoría con id: " + id);
		}
		return filtro;
	}



}
