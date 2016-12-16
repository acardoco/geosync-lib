package com.acc.app.overpass.apis;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.acc.app.overpass.model.VOs.AtributoTransformacion;
import com.acc.app.overpass.model.VOs.ConceptoBD;
import com.acc.app.overpass.model.VOs.FiltroNumerico;
import com.acc.app.overpass.model.VOs.Trabajo;
import com.acc.app.overpass.model.VOs.Transformacion;
import com.acc.app.overpass.postgresql.dbMap.JdbcPostgresDelete;
import com.acc.app.overpass.postgresql.dbMap.JdbcPostgresInsert;
import com.acc.app.overpass.postgresql.dbMap.JdbcPostgresUpdate;
import com.acc.app.overpass.util.ControllerUtils;

import info.pavie.basicosmparser.model.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class XAPI.
 */

public class XAPI {

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
	public static void getOSMRegion(String url, String rutaAlmacen) throws IOException {
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
	public static void ejecucionFiltradoTipoXAPI(String filtrado,List<FiltroNumerico> filtrosnum, String tipo, Trabajo trabajo, Transformacion t,
			List<AtributoTransformacion> atributosTransformacion, String nombreTabla, String BD, String user,
			String password) throws IOException {
		try {
			String Xapi = "";
			if (tipo.equals("nodo")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + "node(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + filtrado + ";out;";

			} else if (tipo.equals("camino")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + "(way(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + filtrado + ";node(w)(" + trabajo.getPuntoYNegativo().toString()
						+ "," + trabajo.getPuntoXNegativo().toString() + "," + trabajo.getPuntoYPositivo().toString()
						+ "," + trabajo.getPuntoXPositivo().toString() + "););out;";
			}

			System.out.println("Url:" + Xapi);
			getOSMRegion(Xapi, SAVEDIR);
			// TODO cogemos los elementos del fichero
			Map<String, Element> osmMapeado = ControllerUtils.Mapear(FILEDIR);
			// borramos el fichero
			File f = new File(FILEDIR);
			f.delete();

			List<FiltroNumerico> filtrosN = filtrosnum;

			// TODO insertamos en la BD
			JdbcPostgresInsert.insertarEnBD(filtrosN, atributosTransformacion, osmMapeado, user, password, BD, nombreTabla,
					tipo);

		} catch (Exception e) {
			System.out.println("Excepcion ejecucionFiltrado: " + e.getLocalizedMessage());
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
	public static void mapeadoFiltrosXAPI(String filtrado,List<FiltroNumerico> filtrosnum, Trabajo trabajo, Transformacion t, ConceptoBD concepto)
			throws IOException {

		try {

			String tipo = t.getIdConceptoOSM().getTipo();

			// ejecutamos cada tipo(habra un fichero .osm por cada tipo)
			ejecucionFiltradoTipoXAPI(filtrado,filtrosnum, tipo, trabajo, t, t.getAtributos(), concepto.getNombreTabla(),
					concepto.getNombreBD(), concepto.getUserBD(), concepto.getPassword());

		} catch (Exception e) {
			System.out.println("ExcepcionMapeado: " + e.getLocalizedMessage());
		}

	}

	/*
	 * --- --- --- -------------UPDATE-------------- --- --- ---
	 */

	/**
	 * Mapeado filtros xapi changeset.
	 *
	 * @param transformacionList
	 *            the transformacion list
	 * @param trabajo
	 *            the trabajo
	 */
	public static void mapeadoFiltrosXAPIChangeset(String filtrado,List<FiltroNumerico> filtrosnum, Trabajo trabajo, Transformacion t,
			ConceptoBD concepto) throws IOException {

		try {

			String tipo = t.getIdConceptoOSM().getTipo();
			// ejecutamos cada tipo(habra un fichero .osm por cada tipo)
			ejecucionFiltradoTipoXAPIChangeset(filtrado,filtrosnum, tipo, trabajo, t, t.getAtributos(),concepto.getNombreTabla(),
					concepto.getNombreBD(), concepto.getUserBD(), concepto.getPassword());

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
	public static void ejecucionFiltradoTipoXAPIChangeset(String filtrado,List<FiltroNumerico> filtrosnum, String tipo, Trabajo trabajo, Transformacion t,
			List<AtributoTransformacion> atributosTransformacion, String nombreTabla, String BD, String user,
			String password) throws IOException {
		try {
			// TODO creamos la URL
			String Xapi = "";
			if (tipo.equals("nodo")) {
				Xapi = OVERPASS_URL;
				Xapi = Xapi + ControllerUtils.dateToStringChangeset(trabajo.getFechaUltimaModificacion());
				Xapi = Xapi + ";node(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + filtrado + ";out;";

			} else if (tipo.equals("camino")) {

				Xapi = OVERPASS_URL;
				Xapi = Xapi + ControllerUtils.dateToStringChangeset(trabajo.getFechaUltimaModificacion());
				Xapi = Xapi + ";(way(";
				Xapi = Xapi + trabajo.getPuntoYNegativo().toString() + "," + trabajo.getPuntoXNegativo().toString()
						+ "," + trabajo.getPuntoYPositivo().toString() + "," + trabajo.getPuntoXPositivo().toString()
						+ ")" + filtrado + ";node(w)(" + trabajo.getPuntoYNegativo().toString()
						+ "," + trabajo.getPuntoXNegativo().toString() + "," + trabajo.getPuntoYPositivo().toString()
						+ "," + trabajo.getPuntoXPositivo().toString() + "););out;";
			}

			System.out.println("Url:" + Xapi);
			getOSMRegion(Xapi, SAVEDIR);
			// TODO cogemos los elementos del fichero
			Map<String, Element> osmMapeado = ControllerUtils.Mapear(FILEDIR);
			List<FiltroNumerico> filtrosN = filtrosnum;

			// borramos el fichero
			File f = new File(FILEDIR);
			f.delete();

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
					JdbcPostgresInsert.insertarEnBD(filtrosN, atributosTransformacion, insertsUpdates, user, password, BD,
							nombreTabla, tipo);
				if (mod > 0)
					JdbcPostgresUpdate.actualizarEnBD(filtrosN, atributosTransformacion, insertsUpdates, user, password, BD,
							nombreTabla, tipo, trabajo);
			}
			// TODO DELETE
			if (!deletes.isEmpty())
				JdbcPostgresDelete.borrarEnBD(atributosTransformacion, deletes, user, password, BD, nombreTabla, tipo);

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}
	}

}
