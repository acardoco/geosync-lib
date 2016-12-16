package com.acc.app.geosync.apis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.service.ConceptoService;
import com.acc.app.geosync.service.TransformacionService;
import com.acc.app.geosync.util.ControllerUtils;
import com.acc.app.geosync.util.dbMap.JdbcPostgresDelete;
import com.acc.app.geosync.util.dbMap.JdbcPostgresInsert;
import com.acc.app.geosync.util.dbMap.JdbcPostgresUpdate;

import info.pavie.basicosmparser.model.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class XAPI.
 */
@Component
public class XAPI {

	/** The concepto service. */
	@Autowired
	private ConceptoService conceptoService;

	/** The transformacion service. */
	@Autowired
	private TransformacionService transformacionService;

	@Autowired
	private JdbcPostgresInsert jdbcInsert;

	@Autowired
	private JdbcPostgresDelete jdbcDelete;

	@Autowired
	private JdbcPostgresUpdate jdbcUpdate;

	/** The savedir. */
	// Paths importantes
	final static String SAVEDIR = "./downloads";
	final static String FILEDIR = "./downloads/map.osm";
	final static String OVERPASS_URL = "http://overpass-api.de/api/interpreter?data=";

	/**
	 * Gets the OSM region.
	 *
	 * @param url
	 *            the url
	 * @param rutaAlmacen
	 *            the ruta almacen
	 * @return the OSM region
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// Peticion al servidor
	public void getOSMRegion(String url, String rutaAlmacen) throws IOException {
		ControllerUtils.downloadFile(url, rutaAlmacen, "map.osm");
	}

	/*
	 * -------------------------------------------------------------------------
	 * ---------------
	 * --------------XAPI-------------------------------------------------------
	 * ---------------
	 * -------------------------------------------------------------------------
	 * ---------------
	 */

	/**
	 * Ejecucion filtrado tipo.
	 *
	 * @param filtros
	 *            the filtros
	 * @param tipo
	 *            the tipo
	 * @param trabajo
	 *            the trabajo
	 * @param t
	 *            the t
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// INSERT
	// (south,west,north,east)->43.36448,-8.42019,43.37144,-8.40335
	public void ejecucionFiltradoTipoXAPI(List<Filtro> filtros, String tipo, Trabajo trabajo, Transformacion t)
			throws IOException {
		try {
			String Xapi = "";
			if (tipo.equals("nodo")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + "node(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + FiltrarEnUrlOVERPASS(filtros) + ";out;";

			} else if (tipo.equals("camino")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + "(way(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + FiltrarEnUrlOVERPASS(filtros) + ";node(w)(" + trabajo.getPuntoYNegativo().toString()
						+ "," + trabajo.getPuntoXNegativo().toString() + "," + trabajo.getPuntoYPositivo().toString()
						+ "," + trabajo.getPuntoXPositivo().toString() + "););out;";
			}

			System.out.println("Url:" + Xapi);
			getOSMRegion(Xapi, SAVEDIR);
			// TODO cogemos los elementos del fichero
			Map<String, Element> osmMapeado = ControllerUtils.Mapear(FILEDIR);
			//borramos el fichero
			File f = new File(FILEDIR);
			f.delete();
			
			List<FiltroNumerico> filtrosN  = obtenerFiltrosNumericos(filtros);

			String nombreTabla = t.getIdConceptoBD().getNombreTabla();
			String BD = t.getIdConceptoBD().getNombreBD();
			String user = t.getIdConceptoBD().getUserBD();
			String password = t.getIdConceptoBD().getPassword();
			List<AtributoTransformacion> atributosTransformacion = transformacionService
					.AtributoTransformacionesTotalesByTransformacionId(t.getIdTransformacion());

			// TODO insertamos en la BD
			jdbcInsert.insertarEnBD(filtrosN,atributosTransformacion, osmMapeado, user, password, BD, nombreTabla, tipo);

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}

	}

	/**
	 * Filtrado_ ejecucion xapi.
	 *
	 * @param transformationL
	 *            the transformation l
	 * @param trabajo
	 *            the trabajo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// Comprobar el tipo del Concepto OSM y filtrar en listas
	public void mapeadoFiltrosXAPI(List<Transformacion> transformationL, Trabajo trabajo) throws IOException {

		try {
			for (Transformacion t : transformationL) {
				// Añadimos en listas de ejecucion segun el tipo
				List<Filtro> filtros = new ArrayList<Filtro>();

				String tipo = t.getIdConceptoOSM().getTipo();
				filtros = conceptoService.BuscarFiltrosConceptoOSM(t.getIdConceptoOSM().getIdConceptoOSM());

				// ejecutamos cada tipo(habra un fichero .osm por cada tipo)
				ejecucionFiltradoTipoXAPI(filtros, tipo, trabajo, t);
			}

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}

	}

	/**
	 * Bbox xapi.
	 *
	 * @param trabajo
	 *            the trabajo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	// ejecucion sin filtros ni mapeos(sin transformaciones)
	public void bboxXAPI(Trabajo trabajo) throws IOException {
		/*
		 * TODO: ?node->nodos ?way->caminos ?relation ?*->todo
		 * 
		 */
		String Xapi = "http://www.overpass-api.de/api/xapi?*";
		String puntos = "";
		puntos = Xapi + "[bbox=" + trabajo.getPuntoXNegativo().toString() + "," + trabajo.getPuntoYNegativo().toString()
				+ "," + trabajo.getPuntoXPositivo().toString() + "," + trabajo.getPuntoYPositivo().toString() + "]";

		// TODO llamada a la API con la url construida
		getOSMRegion(puntos, SAVEDIR);
		// TODO cogemos los elementos del fichero
		Map<String, Element> osmMapeado = ControllerUtils.Mapear("C:/Users/Andrés/Downloads/map.osm");

		// TODO no insertamos ya que no se especifican transformaciones ni
		// mapeos de atributos
	}

	/*
	 * --- --- --- -------------UPDATE-------------- --- --- ---
	 */

	/**
	 * Bbox xapi changeset.
	 *
	 * @param trabajo
	 *            the trabajo
	 */
	public void bboxXAPIChangeset(Trabajo trabajo) {
		// TODO Auto-generated method stub

	}

	/**
	 * Mapeado filtros xapi changeset.
	 *
	 * @param transformacionList
	 *            the transformacion list
	 * @param trabajo
	 *            the trabajo
	 */
	public void mapeadoFiltrosXAPIChangeset(List<Transformacion> transformacionL, Trabajo trabajo) {

		try {
			for (Transformacion t : transformacionL) {
				// Añadimos en listas de ejecucion segun el tipo
				List<Filtro> filtros = new ArrayList<Filtro>();

				String tipo = t.getIdConceptoOSM().getTipo();
				filtros = conceptoService.BuscarFiltrosConceptoOSM(t.getIdConceptoOSM().getIdConceptoOSM());

				// ejecutamos cada tipo(habra un fichero .osm por cada tipo)
				ejecucionFiltradoTipoXAPIChangeset(filtros, tipo, trabajo, t);
			}

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}

	}

	/**
	 * Ejecucion filtrado tipo xapi changeset.
	 *
	 * @param filtros
	 *            the filtros
	 * @param tipo
	 *            the tipo
	 * @param trabajo
	 *            the trabajo
	 * @param t
	 *            the t
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void ejecucionFiltradoTipoXAPIChangeset(List<Filtro> filtros, String tipo, Trabajo trabajo, Transformacion t)
			throws IOException {
		try {
			// TODO creamos la URL
			String Xapi = "";
			if (tipo.equals("nodo")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + ControllerUtils.dateToStringChangeset(trabajo.getFechaUltimaModificacion());
				Xapi = Xapi + ";node(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + FiltrarEnUrlOVERPASS(filtros) + ";out;";

			} else if (tipo.equals("camino")) {

				Xapi = OVERPASS_URL;
				Xapi = Xapi + ControllerUtils.dateToStringChangeset(trabajo.getFechaUltimaModificacion());
				Xapi = Xapi + ";(way(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + FiltrarEnUrlOVERPASS(filtros) + ";node(w)(" + trabajo.getPuntoYNegativo().toString()
						+ "," + trabajo.getPuntoXNegativo().toString() + "," + trabajo.getPuntoYPositivo().toString()
						+ "," + trabajo.getPuntoXPositivo().toString() + "););out;";
			}

			System.out.println("Url:" + Xapi);
			getOSMRegion(Xapi, SAVEDIR);
			// TODO cogemos los elementos del fichero
			Map<String, Element> osmMapeado = ControllerUtils.Mapear(FILEDIR);
			List<FiltroNumerico> filtrosN  = obtenerFiltrosNumericos(filtros);

			//borramos el fichero
			File f = new File(FILEDIR);
			f.delete();

			String nombreTabla = t.getIdConceptoBD().getNombreTabla();
			String BD = t.getIdConceptoBD().getNombreBD();
			String user = t.getIdConceptoBD().getUserBD();
			String password = t.getIdConceptoBD().getPassword();
			List<AtributoTransformacion> atributosTransformacion = transformacionService
					.AtributoTransformacionesTotalesByTransformacionId(t.getIdTransformacion());

			// TODO separamos en listas en funcion de si hay que insertar,
			// eliminar o modificar
			int cre = 0, mod = 0;
			Map<String, Element> insertsUpdates = new HashMap<String, Element>();
			Map<String, Element> deletes = new HashMap<String, Element>();
			for (Entry<String, Element> entry : osmMapeado.entrySet()) {
				Element element = entry.getValue();
				if (element.getAction().equals("modify")) {
					mod++;
					insertsUpdates.put(entry.getKey(), entry.getValue());
				} else if (element.getAction().equals("create")) {
					cre++;
					insertsUpdates.put(entry.getKey(), entry.getValue());
				}

				else if (element.getAction().equals("delete")) {
					deletes.put(entry.getKey(), entry.getValue());
				}
			}

			// TODO UPDATE
			if (!insertsUpdates.isEmpty()) {

				/*
				 * caso de que aparezca una nueva Way, pero que no esten todos
				 * sus nodos del bbox en el fichero changeset .osm
				 */
				if (tipo.equals("camino"))
					ControllerUtils.comprobarNodosWay(insertsUpdates, trabajo);

				if (cre > 0)
					jdbcInsert.insertarEnBD(filtrosN, atributosTransformacion, insertsUpdates, user, password, BD, nombreTabla,
							tipo);
				if (mod > 0)
					jdbcUpdate.actualizarEnBD(filtrosN,atributosTransformacion, insertsUpdates, user, password, BD, nombreTabla,
							tipo, trabajo);
			}
			// TODO DELETE
			if (!deletes.isEmpty())
				jdbcDelete.borrarEnBD(atributosTransformacion, deletes, user, password, BD, nombreTabla, tipo);

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Filtrar en url overpass.
	 *
	 * @param filtros
	 *            the filtros list
	 * @return the string
	 */
	public String FiltrarEnUrlOVERPASS(List<Filtro> filtros) {
		String result = "";
		for (Filtro filtro : filtros) {
			if (!ControllerUtils.esFiltroNumerico(filtro.getOperacion()) && !filtro.getOperacion().equals("existe")) {
				FiltroClaveValor fcv = (FiltroClaveValor) conceptoService.BuscarFiltro(filtro.getIdFiltro());
				result = result + "[\"" + fcv.getClave().replace(" ", "+") + "\"" + fcv.getOperacion().trim() + "\"" + fcv.getValor().replace(" ", "+") + "\"]";
			} else if (filtro.getOperacion().equals("existe")) {
				result = result + "[\"" + filtro.getClave().replace(" ", "+") + "\"]";
			}
		}

		return result;
	}
	
	public List<FiltroNumerico> obtenerFiltrosNumericos(List<Filtro> filtros){
		List<FiltroNumerico> filtrosN = new ArrayList<FiltroNumerico>();
		for (Filtro filtro: filtros){
			if (ControllerUtils.esFiltroNumerico(filtro.getOperacion()))
					filtrosN.add((FiltroNumerico) conceptoService.BuscarFiltro(filtro.getIdFiltro()));
		}
		
		return filtrosN;
	}

}
