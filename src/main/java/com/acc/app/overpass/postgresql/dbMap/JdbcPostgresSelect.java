package com.acc.app.overpass.postgresql.dbMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.acc.app.overpass.model.VOs.AtributoBD;
import com.acc.app.overpass.model.VOs.ConceptoBD;

// TODO: Auto-generated Javadoc
/**
 * The Class JdbcPostgresSelect.
 */
public class JdbcPostgresSelect {

	/**
	 * S t_ x max.
	 *
	 * @param concepto the concepto
	 * @return the float
	 */
	public static float ST_XMax(ConceptoBD concepto) {
		float result = 0;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + concepto.getNombreBD(),
					concepto.getUserBD(), concepto.getPassword());
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			String Query = "SELECT ST_XMax(geom) maxGeom FROM " + concepto.getNombreTabla();
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next())
				result = rs.getFloat("maxGeom");
			rs.close();

			stmt.close();
			c.commit();
			c.close();

			return result;

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() + ":" + e.getLocalizedMessage());
			System.exit(0);
		}
		return result;

	}
	
	/**
	 * S t_ y max.
	 *
	 * @param concepto the concepto
	 * @return the float
	 */
	public static float ST_YMax(ConceptoBD concepto) {
		float result = 0;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + concepto.getNombreBD(),
					concepto.getUserBD(), concepto.getPassword());
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			String Query = "SELECT ST_YMax(geom) maxGeom FROM " + concepto.getNombreTabla();
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next())
				result = rs.getFloat("maxGeom");
			rs.close();

			stmt.close();
			c.commit();
			c.close();

			return result;

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() + ":" + e.getLocalizedMessage());
			System.exit(0);
		}
		return result;

	}

	/**
	 * Concepto bd to geo json.
	 *
	 * @param atributos the atributos
	 * @param concepto the concepto
	 * @return the string
	 */
	public static String conceptoBdToGeoJson(List<AtributoBD> atributos, ConceptoBD concepto) {
		String result = "[";
		String pre = "";
		Connection c = null;
		Statement stmt = null;
		try {

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + concepto.getNombreBD(),
					concepto.getUserBD(), concepto.getPassword());
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			String Query = "";
			//obtener todos los datos en BD de las cooordenadas en formato GeoJson y el resto de atributos
			Query = JdbcPostgresUtil.propiedadesSelect(atributos, concepto.getNombreTabla());
			
			ResultSet rs = stmt.executeQuery(Query);
			//pasar todas las coordenadas, que ya estaban en GeoJson, y valores de atributos a un solo GeoJson 
			while (rs.next()) {
				pre = result + JdbcPostgresUtil.resultSetToJson(rs);
				result = result + JdbcPostgresUtil.resultSetToJson(rs) + ",";

			}
			
			if (result.endsWith(",")) {
				result = pre;
			}

			rs.close();

			stmt.close();
			c.commit();
			c.close();

			result = result + "];";

			System.out.println(result);

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		return result;

	}

}
