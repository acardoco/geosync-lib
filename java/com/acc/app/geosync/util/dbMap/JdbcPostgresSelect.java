package com.acc.app.geosync.util.dbMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.springframework.stereotype.Component;

import com.acc.app.geosync.model.VOs.AtributoBD;

@Component
public class JdbcPostgresSelect {

	public String conceptoBdToGeoJson(List<AtributoBD> atributos, String user, String pass, String bd_name,
			String nombreTabla) {
		String result = "[";
		String pre="";
		Connection c = null;
		Statement stmt = null;
		try {

			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + bd_name, user, pass);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			String Query = "";
			Query=JdbcPostgresUtil.propiedadesSelect(atributos,nombreTabla);
			System.out.println(Query);
			// select
			ResultSet rs = stmt.executeQuery(Query);
			while (rs.next()) {
				pre = result+JdbcPostgresUtil.resultSetToJson(rs,atributos);
				result=result+JdbcPostgresUtil.resultSetToJson(rs,atributos)+",";
				
			}
			
			if (result.endsWith(",")) {
				result = pre;
			}
			
			rs.close();

			stmt.close();
			c.commit();
			c.close();
			
			result= result+"];";

			System.out.println(result);
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Records created successfully");
		return result;

	}

	
}
