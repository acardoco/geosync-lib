package com.acc.app.geosync.util.dbMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.util.ControllerUtils;

import info.pavie.basicosmparser.model.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcPostgresInsert.
 */
@Component
public class JdbcPostgresInsert {

	/**
	 * Insertar en bdxapi.
	 *
	 * @param atributos
	 *            the atributos
	 * @param osmMapeado
	 *            the osm mapeado
	 * @param user
	 *            the user
	 * @param pass
	 *            the pass
	 * @param bd_name
	 *            the bd_name
	 * @param nombreTabla
	 *            the nombre tabla
	 * @param tipo
	 *            the tipo
	 */
	public void insertarEnBD(List<FiltroNumerico> filtrosN, List<AtributoTransformacion> atributos,
			Map<String, Element> osmMapeado, String user, String pass, String bd_name, String nombreTabla,
			String tipo) {
		Connection c = null;
		Statement stmt = null;
		try {

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + bd_name, user, pass);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			// insertamos
			insertarAtributos(filtrosN, osmMapeado, atributos, stmt, nombreTabla, tipo);

			stmt.close();
			c.commit();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");

	}

	// por cada mapeado de atributos, se hacen los inserts segun los valores que
	/**
	 * Insertar atributos.
	 *
	 * @param osmMapeado
	 *            the osm mapeado
	 * @param atributosTransf
	 *            the atributos transf
	 * @param stmt
	 *            the stmt
	 * @param nombreTabla
	 *            the nombre tabla
	 * @param tipo
	 *            the tipo
	 * @throws SQLException
	 *             the SQL exception
	 */
	private static void insertarAtributos(List<FiltroNumerico> filtrosN, Map<String, Element> osmMapeado,
			List<AtributoTransformacion> atributosTransf, Statement stmt, String nombreTabla, String tipo)
					throws SQLException {

		Map<String, String> nombre_clave = new HashMap<String, String>();
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

				if (((entry.getKey().startsWith("W") && tipo.equals("camino"))
						|| (entry.getKey().startsWith("N") && tipo.equals("nodo"))
						|| (entry.getKey().startsWith("R") && tipo.equals("relacion")))
						&& (entry.getValue().getAction().equals("create") || entry.getValue().getAction().equals(""))
						&& JdbcPostgresUtil.cumpleFiltrosNumericos(filtrosN, entry.getValue().getTags())) {
					String sql = "INSERT INTO " + nombreTabla;

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
						 * insercion con ese atributo.
						 */

						for (Entry<String, String> tag : entry.getValue().getTags().entrySet()) {
							if (tag.getKey().equals(BD_OSN.getValue())) {
								nombre_clave_final.put(((BD_OSN.getKey().replaceAll("'", "''")).replace(",", ",,")),
										(tag.getValue().replaceAll("'", "''")).replace(",", ",,"));
							}
						}

						// TODO controlamos el tema de coordenadas
						if (JdbcPostgresUtil.tieneCoordenadas(BD_OSN.getKey())
								&& !ControllerUtils.isEmpty(entry.getValue())) {
							nombre_clave_final.put(BD_OSN.getKey(), "ST_GeomFromText('"
									+ JdbcPostgresUtil.obtenerCoordenadas(entry.getValue()) + "',4326)");
						}

						// TODO controlamos si tiene ID
						if (JdbcPostgresUtil.tieneIdOSM(BD_OSN.getKey())
								&& !ControllerUtils.isEmpty(entry.getValue())) {
							nombre_clave_final.put(BD_OSN.getKey(), entry.getValue().getId());
						}

					}

					List<String> atributosBD = new ArrayList<String>();
					List<String> valorBD = new ArrayList<String>();
					// pulimos la insercion
					for (Entry<String, String> at : nombre_clave_final.entrySet()) {

						atributosBD.add(at.getKey());
						valorBD.add(at.getValue());
					}

					// acomodamos la insercion
					String atributosBDFinal = JdbcPostgresUtil.listToStringAtributosTabla(atributosBD);
					String valoresBDFinal = JdbcPostgresUtil.listToString(valorBD);

					// INSERTAMOS en caso de que haya atributos con valores a
					// insertar
					if (!atributosBD.isEmpty() && !valorBD.isEmpty()) {
						sql = sql + atributosBDFinal + "VALUES " + valoresBDFinal + " ;";
						System.out.println(sql);
						stmt.executeUpdate(sql);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Error en insertar atributos-" + e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

}
