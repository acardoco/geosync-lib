package overpass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.acc.app.overpass.apis.XAPI;
import com.acc.app.overpass.model.VOs.AtributoBD;
import com.acc.app.overpass.model.VOs.AtributoOSM;
import com.acc.app.overpass.model.VOs.AtributoTransformacion;
import com.acc.app.overpass.model.VOs.ConceptoBD;
import com.acc.app.overpass.model.VOs.ConceptoOSM;
import com.acc.app.overpass.model.VOs.Filtro;
import com.acc.app.overpass.model.VOs.FiltroClaveValor;
import com.acc.app.overpass.model.VOs.FiltroNumerico;
import com.acc.app.overpass.model.VOs.Trabajo;
import com.acc.app.overpass.model.VOs.Transformacion;
import com.acc.app.overpass.postgresql.dbMap.JdbcPostgresSelect;

public class test {
	
	public static void execute(){
		// listas
				List<Filtro> filtros = new ArrayList<Filtro>();
				List<Transformacion> transformaciones = new ArrayList<Transformacion>();
				List<AtributoBD> AtributosBD = new ArrayList<AtributoBD>();
				List<AtributoOSM> AtributoOSM = new ArrayList<AtributoOSM>();
				List<AtributoTransformacion> AtributosTransformacion = new ArrayList<AtributoTransformacion>();
				// filtros
				FiltroClaveValor fcv1 = new FiltroClaveValor();
				

				fcv1.setClave("addr:postcode");
				fcv1.setValor("15011");
				fcv1.setOperacion("=");
				filtros.add(fcv1);


				// atributos osm
				AtributoOSM aos1 = new AtributoOSM();
				aos1.setClave("id");
				// atributos bd
				AtributoBD bd1 = new AtributoBD();
				bd1.setNombre("id");
				// atributos osm
				AtributoOSM aos2 = new AtributoOSM();
				aos2.setClave("geom");
				// atributos bd
				AtributoBD bd2 = new AtributoBD();
				bd2.setNombre("geom");
				
				//atributos transformacion
				AtributoTransformacion at1 = new AtributoTransformacion();
				at1.setIdAtributoBD(bd1);
				at1.setIdAtributoOSM(aos1);
				
				AtributoTransformacion at2 = new AtributoTransformacion();
				at2.setIdAtributoBD(bd2);
				at2.setIdAtributoOSM(aos2);

				String nombreTabla = "pruebas";
				String BD = "geo_userBD";
				String user = "geo";
				String password = "geo";
				// trabajo
				Trabajo trabajo = new Trabajo("trabajillo", 43.37144, 43.36448, -8.40335, -8.42019);
				trabajo.setTransformaciones(new ArrayList<Transformacion>());
				trabajo.setIdTrabajo(1);
				// conceptos
				ConceptoBD bd = new ConceptoBD(1, "cbd", nombreTabla, new ArrayList<Transformacion>(), new ArrayList<AtributoBD>(), BD, user, password);
				ConceptoOSM osm = new ConceptoOSM(1, "osm", "camino", new ArrayList<Transformacion>(), new ArrayList<Filtro>(), new ArrayList<AtributoOSM>());
				Transformacion tr = new Transformacion(1, "trans", osm, bd, trabajo, new ArrayList<AtributoTransformacion>());
				
				//asimilaciones
				//atributos a transformacion
				tr.getAtributos().add(at1);
				tr.getAtributos().add(at2);
				//atributos a bd
				bd.getAtributos().add(bd1);
				bd.getAtributos().add(bd2);
				//transformacion a todo cristo
				bd.getTransformaciones().add(tr);
				osm.getTransformaciones().add(tr);
				trabajo.getTransformaciones().add(tr);

				
				try {
					XAPI.mapeadoFiltrosXAPI("[\"addr:postcode\"=\"15011\"]",new ArrayList<FiltroNumerico>(), trabajo, tr, bd);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getLocalizedMessage());
				}
	}
	public static void maxST(){
		float x = 0,y = 0;
		String nombreTabla = "pruebas";
		String BD = "geo_userBD";
		String user = "geo";
		String password = "geo";
		ConceptoBD bd = new ConceptoBD(1, "cbd", nombreTabla, new ArrayList<Transformacion>(), new ArrayList<AtributoBD>(), BD, user, password);

		x = JdbcPostgresSelect.ST_XMax(bd);
		y = JdbcPostgresSelect.ST_YMax(bd);
		System.out.println(x+":"+y);
	}
	public static void main(String[] args) throws IOException {
		
		
		maxST();
	}

}
