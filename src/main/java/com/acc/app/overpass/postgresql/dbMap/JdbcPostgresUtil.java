package com.acc.app.overpass.postgresql.dbMap;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.acc.app.overpass.model.VOs.AtributoBD;
import com.acc.app.overpass.model.VOs.AtributoTransformacion;
import com.acc.app.overpass.model.VOs.FiltroNumerico;
import com.acc.app.overpass.model.VOs.Trabajo;
import com.acc.app.overpass.util.ControllerUtils;

import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Relation;
import info.pavie.basicosmparser.model.Way;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcPostgresUtil.
 */
public class JdbcPostgresUtil {
	/**
	 * Obtener coordenadas.
	 *
	 * @param e
	 *            the e
	 * @return the string
	 */
	// "ST_GeomFromText('POINT(-71.060316 48.432044)',4326)"

	/*
	 * si nos bajamos un fichero con relaciones, excluyendo otros elementos
	 * (ways y nodes) no se puede insertar sus geometrias(estan a nulo)
	 * 
	 */
	public static String obtenerCoordenadas(Element e) {
		String returned = "";
		// TODO NODO

		if (e.getId().startsWith("N")) {
			Node node = (Node) e;
			double lat = node.getLat();
			double lon = node.getLon();
			if (!ControllerUtils.isEmpty(e))
				returned = "POINT(" + lon + " " + lat + ")";
			// TODO WAY
		} else if (e.getId().startsWith("W")) {
			Way way = (Way) e;
			List<Node> nodes = way.getNodes();

			// TODO caso que solo tenga un NODE valido, insertamos como POINT
			// esa way
			String pre = returned;

			int insertados = 0;
			Node insertado = null;

			if (!nodes.isEmpty() && !ControllerUtils.isEmpty(way)) {
				returned = "LINESTRING(";
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.get(i);
					if (!ControllerUtils.isEmpty(node)) {
						double lat = node.getLat();
						double lon = node.getLon();
						if (i == nodes.size() - 1) {
							returned = returned + lon + " " + lat + ")";
							insertados = insertados + 1;
							insertado = node;
						} else if (i < nodes.size() - 1) {
							int vacios = 0;
							int recorridos = 0;
							// miramos que los siguientes nodos no esten
							// vacios, para poner o no ","
							for (int j = i + 1; j < nodes.size(); j++) {
								recorridos = recorridos + 1;
								if (ControllerUtils.isEmpty(nodes.get(j))) {
									vacios = vacios + 1;
								}
							}
							if (vacios != recorridos) {
								returned = returned + lon + " " + lat + ",";
								insertados = insertados + 1;
								insertado = node;
							} else {
								returned = returned + lon + " " + lat + ")";
								insertados = insertados + 1;
								insertado = node;
							}
						}
					}
				}
				// TODO en geometry, no deja insertar un LINESTRING con un solo
				// POINT, asi que lo insertamos como point asecas
			}
			if (insertados == 1) {
				double lat = insertado.getLat();
				double lon = insertado.getLon();
				returned = pre + "POINT(" + lon + " " + lat + ")";
			}
			// TODO RELATION
		} else if (e.getId().startsWith("R")) {

			Relation relation = (Relation) e;
			List<Element> members = relation.getMembers();
			if (!members.isEmpty() && !ControllerUtils.isEmpty(relation)) {
				returned = "GEOMETRYCOLLECTION(";

				for (int i = 0; i < members.size(); i++) {

					Element element = members.get(i);
					if (!ControllerUtils.isEmpty(element)) {
						if (element.getId().startsWith("N")) {

							Node node = (Node) element;

							returned = returned + obtenerCoordenadas(node);

						} else if (element.getId().startsWith("W")) {

							Way way = (Way) element;

							returned = returned + obtenerCoordenadas(way);

						} else if (element.getId().startsWith("R")) {

							Relation rel = (Relation) element;

							returned = returned + obtenerCoordenadas(rel);
						}

						// caso elemento actual tiene valor pero siguiente/s
						// es/son
						// nulo/s
						if (i < members.size() - 1) {
							int vacios = 0;
							int recorridos = 0;
							// miramos que los siguientes elementos no esten
							// vacios, para poner o no ","
							for (int j = i + 1; j < members.size(); j++) {
								recorridos = recorridos + 1;
								if (ControllerUtils.isEmpty(members.get(j))) {
									vacios = vacios + 1;
								}
							}
							if (vacios != recorridos)
								returned = returned + ",";
						}
					}
				}

				returned = returned + ")";

			}
		}

		return returned;
	}

	/**
	 * Obtener id.
	 *
	 * @param cadena
	 *            the cadena
	 * @return the long
	 */
	public static Long obtenerId(String cadena) {
		return Long.parseLong(cadena.substring(1));
	}

	/**
	 * Pulir tags.
	 *
	 * @param tags
	 *            the tags
	 * @return the map
	 */
	/*
	 * Cambia '->''
	 * 
	 */
	public static Map<String, String> pulirTags(Map<String, String> tags) {
		Map<String, String> tagsPulidos = new HashMap<String, String>();
		for (Entry<String, String> entry : tags.entrySet()) {
			String key = entry.getKey().replaceAll("'", "''");
			String value = entry.getValue().replaceAll("'", "''");
			tagsPulidos.put(key, value);
		}

		return tagsPulidos;
	}

	// funcion que estable todos los elementos de una lista entre parentesis y
	/**
	 * List to string.
	 *
	 * @param cadena
	 *            the cadena
	 * @return the string
	 */
	// separados por comas en un String y añadimos "'".
	public static String listToString(List<String> cadena) {
		String result = "(";
		for (int i = 0; i < cadena.size(); i++) {
			if (i >= cadena.size() - 1) {
				if (cadena.get(i).startsWith("ST_GeomFromText")) {
					result = result + cadena.get(i) + ")";
				} else
					result = result + "'" + cadena.get(i) + "'" + ")";
			} else {
				if (cadena.get(i).startsWith("ST_GeomFromText"))
					result = result + cadena.get(i) + ",";
				else
					result = result + "'" + cadena.get(i) + "'" + ",";
			}
		}
		return result;

	}

	// funcion que estable todos los elementos de una lista entre parentesis y
	/**
	 * List to string atributos tabla.
	 *
	 * @param cadena
	 *            the cadena
	 * @return the string
	 */
	// separados por comas en un String
	public static String listToStringAtributosTabla(List<String> cadena) {
		String result = "(";
		for (int i = 0; i < cadena.size(); i++) {
			if (i >= cadena.size() - 1) {
				result = result + cadena.get(i) + ")";
			} else
				result = result + cadena.get(i) + ",";
		}

		return result;

	}

	/**
	 * Tiene coordenadas.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	/*
	 * Admite: geom, coordenadas, geometry y coor
	 */
	public static boolean tieneCoordenadas(String key) {
		boolean returned = false;
		if (key.equals("geom") || key.equals("coordenadas") || key.equals("coor") || key.equals("geometry"))
			returned = true;
		return returned;
	}

	/**
	 * Tiene id osm.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	/*
	 * Admite: id, idOSM,identificador y osmId
	 */
	public static boolean tieneIdOSM(String key) {
		boolean returned = false;
		if (key.toUpperCase().equals("ID") || key.toUpperCase().equals("IDOSM")
				|| key.toUpperCase().equals("IDENTIFICADOR") || key.toUpperCase().equals("OSMID")
				|| key.toUpperCase().equals("OSM_ID"))
			returned = true;
		return returned;
	}

	/**
	 * List to string atributos tabla update.
	 *
	 * @param nombre_clave_final
	 *            the nombre_clave_final
	 * @return the string
	 */
	public static String listToStringAtributosTablaUpdate(Map<String, String> nombre_clave_final) {
		String returned = "";
		int size = 0;
		for (Entry<String, String> entry : nombre_clave_final.entrySet()) {
			returned = returned + " " + entry.getKey() + " = ";
			if (!entry.getValue().startsWith("ST_"))
				returned = returned + "'" + entry.getValue() + "'";
			else
				returned = returned + entry.getValue();
			if (size < nombre_clave_final.size() - 1)
				returned = returned + ",";
			size++;
		}
		return returned;
	}

	/**
	 * Obtener id.
	 *
	 * @param atributosTransformacion
	 *            the atributos transformacion
	 * @return the string
	 */
	public static String obtenerId(List<AtributoTransformacion> atributosTransformacion) {
		String returned = "id";
		for (AtributoTransformacion at : atributosTransformacion) {
			String key = at.getIdAtributoBD().getNombre();
			if (key.toUpperCase().equals("ID") || key.toUpperCase().equals("IDOSM")
					|| key.toUpperCase().equals("IDENTIFICADOR") || key.toUpperCase().equals("OSMID"))
				returned = key;
		}
		return returned;
	}

	/**
	 * Actualizar way nuevos nodos.
	 *
	 * @param geom
	 *            the geom
	 * @param value
	 *            the value
	 * @param osmMapeado
	 *            the osm mapeado
	 * @param trabajo
	 *            the trabajo
	 * @return the string
	 * @throws IOException
	 */
	public static String actualizarWayNuevosNodos(String geom, Element value, Map<String, Element> osmMapeado,
			Trabajo trabajo, Map<String, Element> nodosDescargados) throws IOException {
		String returned = "";

		String coordenadasInsert = "";

		coordenadasInsert = actualizarWayInsert(value, osmMapeado, trabajo, nodosDescargados);
		if (!coordenadasInsert.equals("")) {

			returned = returned + "ST_MakeLine( " + geom + ", ST_GeomFromText('" + coordenadasInsert + "',4326)) ";

		}

		return returned;

	}

	/**
	 * Obtener nodos from way deleted.
	 *
	 * @param value
	 *            the value
	 * @param osmMapeado
	 *            the osm mapeado
	 * @param trabajo
	 *            the trabajo
	 * @return the list
	 * @throws IOException
	 */
	public static List<Node> obtenerNodosFromWayDeleted(Element value, Map<String, Element> osmMapeado, Trabajo trabajo,
			Map<String, Element> nodosDescargados) throws IOException {

		Way way = (Way) value;
		Way wayDeletes = new Way(0);

		Way wayOLD = (Way) osmMapeado.get(value.getId() + "OLD");

		List<Node> oldNodes = wayOLD.getNodes();
		List<Node> actualNodes = way.getNodes();

		// añadimos los delete a la lista de deletes
		for (Node oldNode : oldNodes) {
			boolean esDelete = true;
			for (Node actualNode : actualNodes) {
				if (oldNode.getId().equals(actualNode.getId())) {
					esDelete = false;
				}

			}

			// TODO borramos si es un nodo modificado(el nodo "old")
			if (!ControllerUtils.isEmpty(osmMapeado.get(oldNode.getId()))
					&& (osmMapeado.get(oldNode.getId()).getAction().equals("modify"))) {
				wayDeletes.addNode((Node) osmMapeado.get(oldNode.getId() + "OLD"));

			}

			if (esDelete == true) {
				wayDeletes.addNode(oldNode);
			}
		}
		ControllerUtils.obtenerNodosVacios(wayDeletes, trabajo, osmMapeado, nodosDescargados);

		return wayDeletes.getNodes();

	}

	/**
	 * Actualizar way insert.
	 *
	 * @param value
	 *            the value
	 * @param osmMapeado
	 *            the osm mapeado
	 * @param trabajo
	 *            the trabajo
	 * @return the string
	 * @throws IOException
	 */
	public static String actualizarWayInsert(Element value, Map<String, Element> osmMapeado, Trabajo trabajo,
			Map<String, Element> nodosDescargados) throws IOException {

		String coordenadasInsert = "";
		Way way = (Way) value;
		Way wayInsert = new Way(0);

		Way wayOLD = (Way) osmMapeado.get(value.getId() + "OLD");

		List<Node> oldNodes = wayOLD.getNodes();
		List<Node> actualNodes = way.getNodes();

		// añadimos los insert a la lista de inserts
		for (Node actualNode : actualNodes) {
			boolean esInsert = true;
			for (Node oldNode : oldNodes) {
				if (actualNode.getId().equals(oldNode.getId())) {
					esInsert = false;
				}

			}
			// TODO insertamos si es un nodo modificado(el nodo "new")
			if (!ControllerUtils.isEmpty(osmMapeado.get(actualNode.getId()))
					&& (osmMapeado.get(actualNode.getId()).getAction().equals("modify"))) {
				wayInsert.addNode((Node) osmMapeado.get(actualNode.getId()));
			}

			if (esInsert == true) {
				wayInsert.addNode(actualNode);
			}
		}
		ControllerUtils.obtenerNodosVacios(wayInsert, trabajo, osmMapeado, nodosDescargados);
		coordenadasInsert = coordenadasInsert + obtenerCoordenadas(wayInsert);

		return coordenadasInsert;

	}

	/**
	 * Actualizar nodos borrados.
	 *
	 * @param atributosTransf
	 *            the atributos transf
	 * @param nombreTabla
	 *            the nombre tabla
	 * @param geomAtt
	 *            the geom att
	 * @param nodosBorrados
	 *            the nodos borrados
	 * @param stmt
	 *            the stmt
	 * @throws SQLException
	 *             the SQL exception
	 */
	public static void actualizarNodosBorrados(List<AtributoTransformacion> atributosTransf, String nombreTabla,
			String geomAtt, List<Node> nodosBorrados, Statement stmt, String key) throws SQLException {
		String id = JdbcPostgresUtil.obtenerId(atributosTransf);

		for (Node nodeElimnado : nodosBorrados) {
			if (!ControllerUtils.isEmpty(nodeElimnado)) {
				String up = "UPDATE " + nombreTabla + " SET " + geomAtt + " = ST_RemovePoint(" + geomAtt
						+ ",t.pt_num-1) " + "FROM " + "(SELECT line_id, pt_num FROM " + " (SELECT " + id
						+ " line_id, (ST_DumpPoints(" + geomAtt + ")).path[1] pt_num," + "(ST_DumpPoints(" + geomAtt
						+ "))." + geomAtt + " " + geomAtt + "" + " FROM " + nombreTabla + " WHERE " + id + " = '" + key
						+ "'" + ") my_points" + " WHERE ST_Equals(" + geomAtt + ", ST_GeomFromText('SRID=4326;"
						+ JdbcPostgresUtil.obtenerCoordenadas(nodeElimnado) + "'))) t" + " WHERE " + id
						+ " = t.line_id";
				System.out.println(up);
				stmt.executeUpdate(up);
			}
		}

	}

	// si no tiene el tag numerico, no lo inserta
	public static boolean cumpleFiltrosNumericos(List<FiltroNumerico> filtros, Map<String, String> tags) {
		boolean returned = false;

		if (filtros.isEmpty() || filtros ==null) {
			returned = true;
		} else {

			for (Entry<String, String> tag : tags.entrySet()) {

				for (FiltroNumerico filtro : filtros) {
					if (tag.getKey().equals(filtro.getClave())) {
						if (filtro.getOperacion().equals("<")) {
							if (Integer.parseInt(tag.getValue()) < Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals("<=")) {
							if (Integer.parseInt(tag.getValue()) <= Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals(">")) {
							if (Integer.parseInt(tag.getValue()) > Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals(">=")) {
							if (Integer.parseInt(tag.getValue()) >= Integer.parseInt(filtro.getValor()))
								returned = true;
						}

					}
				}
			}
		}

		return returned;
	}

	// inserta igualmente si no tiene el tag Numerico(key)
	public static boolean cumpleFiltrosNumericosLigero(List<FiltroNumerico> filtros, Map<String, String> tags) {
		boolean returned = false;
		boolean tieneTagNumerico = false;

		if (filtros.isEmpty()) {
			returned = true;
		} else {

			for (Entry<String, String> tag : tags.entrySet()) {

				for (FiltroNumerico filtro : filtros) {
					if (tag.getKey().equals(filtro.getClave())) {
						if (filtro.getOperacion().equals("<")) {
							if (Integer.parseInt(tag.getValue()) < Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals("<=")) {
							if (Integer.parseInt(tag.getValue()) <= Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals(">")) {
							if (Integer.parseInt(tag.getValue()) > Integer.parseInt(filtro.getValor()))
								returned = true;
						} else if (filtro.getOperacion().equals(">=")) {
							if (Integer.parseInt(tag.getValue()) >= Integer.parseInt(filtro.getValor()))
								returned = true;
						}
						tieneTagNumerico = true;
					}
				}
			}
		}
		if (tieneTagNumerico == false)
			returned = true;
		return returned;
	}

	public static String propiedadesSelect(List<AtributoBD> atributos, String nombreTabla) {
		String result = "SELECT ST_AsGeoJSON(geom) geom";
		for (int i = 0; i < atributos.size(); i++) {
			String columna = atributos.get(i).getNombre();
			// TODO
			if (!columna.equals("geom")) {
				result = result + "," + columna;
			}
		}

		result = result + " FROM " + nombreTabla + ";";
		return result;
	}

	public static String resultSetToJson(ResultSet rs) throws SQLException {
		String result = "{\"type\": \"Feature\",\"properties\":{";
		String propiedades = "";

		try {

			result = result + obtenerPropiedadesToJSON(rs);
			
			// cerramos properties
			result = result + propiedades + "},";

			// geometry
			String coordenadas = rs.getString("geom");
			result = result.concat(" \"geometry\": ") + coordenadas;

			// cerramos todo
			result = result + "}";

			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return result;

	}

	public static String obtenerPropiedadesToJSON(ResultSet rs) throws SQLException {
		String result = "";
		String pre = "";
		ResultSetMetaData rsmd = rs.getMetaData();
		int numColumns = rsmd.getColumnCount();
		for (int i = 1; i < numColumns + 1; i++) {
			String columna = rsmd.getColumnName(i);
			String columnaValue = rs.getString(columna);
			// atributos properties
			if (!columna.equals("geom") && columnaValue != null) {
				columnaValue = columnaValue.trim();
				pre = result + " \"" + columna + "\":" + " \"" + columnaValue + "\" ";
				result = result + " \"" + columna + "\":" + " \"" + columnaValue + "\" ,";
			}
		}

		// por si acaba con una ,
		if (result.endsWith(",")) {
			result = pre;
		}

		return result;
	}

}
