package com.acc.app.overpass.postgresql.dbMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import com.acc.app.overpass.model.VOs.AtributoTransformacion;
import com.acc.app.overpass.model.VOs.FiltroNumerico;
import com.acc.app.overpass.model.VOs.Trabajo;
import com.acc.app.overpass.util.ControllerUtils;

import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;


public class JdbcPostgresUpdate {

	public static void actualizarEnBD(List<FiltroNumerico> filtrosN, List<AtributoTransformacion> atributosTransformacion,
			Map<String, Element> osmMapeado, String user, String password, String bD, String nombreTabla, String tipo,
			Trabajo trabajo) {
		Connection c = null;
		Statement stmt = null;
		try {

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + bD, user, password);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			// actualizamos
			actualizarAtributos(filtrosN, osmMapeado, atributosTransformacion, stmt, nombreTabla, tipo, trabajo);

			stmt.close();
			c.commit();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");

	}

	private  static void actualizarAtributos(List<FiltroNumerico> filtrosN, Map<String, Element> osmMapeado,
			List<AtributoTransformacion> atributosTransf, Statement stmt, String nombreTabla, String tipo,
			Trabajo trabajo) {
		Map<String, String> nombre_clave = new HashMap<String, String>();
		// --variable para borrado de nodos de un Way
		boolean hayBorrados = false;
		String geomAtt = "";
		List<Node> nodosBorrados = null;
		Map<String, Element> nodosDescargados = new HashMap<String, Element>();
		// --
		try {
			// relacionamos cada nombre de atributoBD con la clave de
			// atributoOSM
			// respectivamente
			for (AtributoTransformacion at : atributosTransf) {
				nombre_clave.put(at.getIdAtributoBD().getNombre(), at.getIdAtributoOSM().getClave());
			}

			/*
			 * para cada elemento(nodo|way|relation) del .osm , realizamos una
			 * insercion segun los mapeos de atributos de la transf. Si las ways
			 * vienen con nodos, no inserta estos, pero coge las coordenadas de
			 * estos. Si las relaciones vienen con nodos y ways, tampoco los
			 * inserta, pero se queda con sus coordenadas
			 */
			for (Entry<String, Element> entry : osmMapeado.entrySet()) {

				Map<String, String> nombre_clave_final = new HashMap<String, String>();

				if (((entry.getKey().startsWith("N") && tipo.equals("nodo"))
						|| (entry.getKey().startsWith("W") && tipo.equals("camino")))
						&& entry.getValue().getAction().equals("modify") && !entry.getKey().endsWith("OLD")
						&& JdbcPostgresUtil.cumpleFiltrosNumericos(filtrosN, entry.getValue().getTags())) {
					String sql = "UPDATE " + nombreTabla + " SET ";

					/*
					 * para cada relacion entre atributoBD y atributoOSM,
					 * buscamos los valores posibles de la clave atributoOSM en
					 * el .osm del elemento concreto .osm
					 */
					for (Entry<String, String> BD_OSN : nombre_clave.entrySet()) {
						/*
						 * relacionamos cada nombre de atributoBD con el valor
						 * del tag(segun la clave atributoOSM que tiene
						 * relacionado). No puede haber 2 tags con la misma
						 * clave en un elemento. Si no encuentra tag, no hace
						 * actualizacion con ese atributo.
						 */

						for (Entry<String, String> tag : entry.getValue().getTags().entrySet()) {
							if (tag.getKey().equals(BD_OSN.getValue())) {
								nombre_clave_final.put(((BD_OSN.getKey().replaceAll("'", "''")).replace(",", ",,")),
										(tag.getValue().replaceAll("'", "''")).replace(",", ",,"));
							}
						}

						// TODO controlamos el tema de coordenadas
						// TODO NODOS
						if (JdbcPostgresUtil.tieneCoordenadas(BD_OSN.getKey())
								&& !ControllerUtils.isEmpty(entry.getValue())
								&& entry.getValue().getId().startsWith("N")) {

							nombre_clave_final.put(BD_OSN.getKey(), "ST_GeomFromText('"
									+ JdbcPostgresUtil.obtenerCoordenadas(entry.getValue()) + "',4326)");

						} // TODO CAMINOS
						else if (JdbcPostgresUtil.tieneCoordenadas(BD_OSN.getKey())
								&& entry.getValue().getId().startsWith("W")) {

							String geomWay = JdbcPostgresUtil.actualizarWayNuevosNodos(BD_OSN.getKey(),
									entry.getValue(), osmMapeado, trabajo, nodosDescargados);

							if (!geomWay.equals(""))
								nombre_clave_final.put(BD_OSN.getKey(), geomWay);

							// TODO miramos si ha quitado nodos del way, y lo
							// ejecutamos despues de haber insertado los nuevos
							// nodos
							nodosBorrados = JdbcPostgresUtil.obtenerNodosFromWayDeleted(entry.getValue(), osmMapeado,
									trabajo, nodosDescargados);
							if (!nodosBorrados.isEmpty()) {
								hayBorrados = true;
								geomAtt = BD_OSN.getKey();

							}
						}

					}

					List<String> atributosBD = new ArrayList<String>();
					List<String> valorBD = new ArrayList<String>();
					// pulimos la actualizacion
					for (Entry<String, String> at : nombre_clave_final.entrySet()) {

						atributosBD.add(at.getKey());
						valorBD.add(at.getValue());
					}

					// acomodamos la actualizacion
					String atributosYValoresBDFinal = JdbcPostgresUtil
							.listToStringAtributosTablaUpdate(nombre_clave_final);

					// ACTUALIZAMOS en caso de que haya atributos con valores a
					// actualizar
					if (!atributosBD.isEmpty() && !valorBD.isEmpty()) {
						sql = sql + atributosYValoresBDFinal + " WHERE  " + JdbcPostgresUtil.obtenerId(atributosTransf)
								+ " = '" + entry.getKey() + "';";
						System.out.println(sql);
						stmt.executeUpdate(sql);
					}

					// TODO borramos al final si hay que quitar nodos de un
					// Way
					if (hayBorrados) {
						JdbcPostgresUtil.actualizarNodosBorrados(atributosTransf, nombreTabla, geomAtt, nodosBorrados,
								stmt, entry.getKey());
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error en insertar atributos-" + e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

}

// // TODO SELECT id_pk
// ResultSet rs = stmt.executeQuery("SELECT st_astext(geom) as geom FROM pruebas
// ;");
// String geom;
// while (rs.next()) {
// geom = rs.getString("geom");
// System.out.println("geometria:"+geom);
// }
// rs.close();
// //UPDATE ADD
// String up = "UPDATE pruebas SET geom = ST_AddPoint(geom,
// ST_MakePoint(2.2,3.3));";
// stmt.executeUpdate(up);

// UPDATE DELETE
// String up = "UPDATE pruebas" + " SET geom = ST_RemovePoint(geom,
// t.pt_num-1)" + "FROM "
// + "(SELECT line_id, pt_num FROM"
// + " (SELECT id line_id, (ST_DumpPoints(geom)).path[1] pt_num,
// (ST_DumpPoints(geom)).geom geom"
// + " FROM pruebas) my_points"
// + " WHERE ST_Equals(geom, ST_GeomFromText('SRID=4326;POINT(2.2 3.3)'))) t
// " + "WHERE id = t.line_id";
// System.out.println(up);
