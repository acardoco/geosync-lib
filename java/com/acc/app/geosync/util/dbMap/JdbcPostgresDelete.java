package com.acc.app.geosync.util.dbMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.acc.app.geosync.model.VOs.AtributoTransformacion;

import info.pavie.basicosmparser.model.Element;

@Component
public class JdbcPostgresDelete {

	public void borrarEnBD(List<AtributoTransformacion> atributosTransformacion, Map<String, Element> osmMapeado,
			String user, String password, String bD, String nombreTabla, String tipo) {
		Connection c = null;
		Statement stmt = null;
		try {

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + bD, user, password);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			// borramos
			for (Entry<String, Element> entry : osmMapeado.entrySet()) {
				if ((entry.getKey().startsWith("W") && tipo.equals("camino"))
						|| (entry.getKey().startsWith("N") && tipo.equals("nodo"))) {
					String sql = "DELETE FROM " + nombreTabla;
					String id = JdbcPostgresUtil.obtenerId(atributosTransformacion);
					sql = sql + " WHERE " + id + " = '" + entry.getValue().getId() + "'";
					System.out.println(sql);
					stmt.executeUpdate(sql);
				}
			}
			stmt.close();
			c.commit();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");

	}

}
