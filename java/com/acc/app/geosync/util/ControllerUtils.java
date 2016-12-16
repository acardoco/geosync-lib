package com.acc.app.geosync.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.util.dbMap.JdbcPostgresInsert;

//BasicOSM parser 
import info.pavie.basicosmparser.controller.OSMParser;
import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Relation;
import info.pavie.basicosmparser.model.Way;

// TODO: Auto-generated Javadoc
/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 *
 */
public class ControllerUtils {

	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 4096;

	/** The savedir. */
	final static String SAVEDIR = "./downloads";
	
	final static String OVERPASS = "http://api.openstreetmap.org/api/0.6/";

	/**
	 * Downloads a file from a URL.
	 *
	 * @param fileURL
	 *            HTTP URL of the file to be downloaded
	 * @param saveDir
	 *            path of the directory to save the file
	 * @param filename
	 *            the filename
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void downloadFile(String fileURL, String saveDir, String filename) throws IOException {
		URL url = new URL(fileURL);

		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
			String contentType = httpConn.getContentType();
			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				// TODO antes
				// estaba-->fileURL.substring(fileURL.lastIndexOf("/") + 1,
				// fileURL.length())
				fileName = filename;
			}

			System.out.println("Content-Type = " + contentType);
			System.out.println("Content-Disposition = " + disposition);
			System.out.println("Content-Length = " + contentLength);
			System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			String saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			System.out.println("File downloaded");
		} else {
			System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		}
		httpConn.disconnect();
	}

	/**
	 * Filtrar y mapear.
	 *
	 * @param lista
	 *            the lista
	 * @param clave
	 *            the clave
	 * @return the map
	 */
	public static boolean esRepetido(List<String> lista, String clave) {
		boolean result = false;
		for (String elemento : lista) {
			if (elemento.equals(clave)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Mapear.
	 *
	 * @param rutaFichero
	 *            the ruta fichero
	 * @return the map
	 */
	public static Map<String, Element> Mapear(String rutaFichero) {

		OSMParser p = new OSMParser(); // Initialization of the parser
		File osmFile = new File(rutaFichero); // Create a file object for your
		Map<String, Element> returned = new HashMap<String, Element>();
		// OSM XML file

		try {

			returned = p.parse(osmFile); // Parse OSM data,
											// and put result in
											// a Map object
			/**/

		} catch (IOException | SAXException e) {
			e.printStackTrace(); // Input/output errors management
		}
		return returned;
	}
	

	/**
	 * Filtrar en url xapi.
	 *
	 * @param filtrosList
	 *            the filtros list
	 * @return the string
	 */
	public static String FiltrarEnUrlXAPI(List<FiltroClaveValor> filtrosList) {
		String result = "";
		for (FiltroClaveValor filtro : filtrosList) {
			result = result + "[" + filtro.getClave() + "=" + filtro.getValor().replaceAll(" ", "+") + "]";
		}

		return result;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param element
	 *            the element
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Element element) {
		boolean result = false;
		if (element == null) {
			result = true;
		} else if (element.getId().startsWith("N")) {
			Node node = (Node) element;
			if (node.getLat() == 0 && node.getLon() == 0) {
				result = true;
			}
		} else if (element.getId().startsWith("W")) {
			Way way = (Way) element;
			List<Node> nodes = way.getNodes();
			int vacios = 0;
			for (Node n : nodes) {
				if (isEmpty(n)) {
					vacios = vacios + 1;
				}
			}
			if (nodes.size() == vacios) {
				result = true;
			}

		} else if (element.getId().startsWith("R")) {
			Relation relation = (Relation) element;
			List<Element> members = relation.getMembers();
			int vacios = 0;
			for (Element e : members) {
				if (isEmpty(e)) {
					vacios = vacios + 1;
				}
			}
			if (members.size() == vacios) {
				result = true;
			}

		}

		return result;
	}

	/**
	 * Comprobar nodos way.
	 *
	 * @param inserts
	 *            the inserts
	 * @param trabajo
	 *            the trabajo
	 * @return the map
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Map<String, Element> comprobarNodosWay(Map<String, Element> inserts, Trabajo trabajo)
			throws IOException {
		Map<String, Element> ways = new HashMap<String, Element>();
		try {
			for (Entry<String, Element> entry : inserts.entrySet()) {
				if (entry.getValue().getId().startsWith("W") && entry.getValue().getAction().equals("create")) {
					ways.put(entry.getKey(), entry.getValue());
				}
			}

			for (Entry<String, Element> entry : ways.entrySet()) {
				if (entry.getValue().getAction().equals("create")) {

					Way way = (Way) entry.getValue();
					obtenerNodosVacios(way, trabajo, inserts);

				}
			}

		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getLocalizedMessage());
		}
		return inserts;
	}

	/**
	 * Date to string changeset.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	// [diff:"2012-09-14T15:00:00Z"]
	public static String dateToStringChangeset(Calendar date) {

		String returned = "";
		String mes = "";
		String hora = "";
		String minuto = "";
		String segundo = "";
		String dia = "";
		if ((date.getInstance().get(Calendar.MONTH) + 1) < 10)
			mes = "0" + (date.getInstance().get(Calendar.MONTH) + 1);
		else
			mes = mes + (date.getInstance().get(Calendar.MONTH) + 1);

		if ((date.getInstance().get(Calendar.DAY_OF_MONTH)) < 10)
			dia = "0" + (date.getInstance().get(Calendar.DAY_OF_MONTH));
		else
			dia = dia + (date.getInstance().get(Calendar.DAY_OF_MONTH));

		if ((date.getInstance().get(Calendar.HOUR_OF_DAY)) < 10)
			hora = "0" + (date.getInstance().get(Calendar.HOUR_OF_DAY));
		else
			hora = hora + (date.getInstance().get(Calendar.HOUR_OF_DAY));

		if ((date.getInstance().get(Calendar.MINUTE)) < 10)
			minuto = "0" + (date.getInstance().get(Calendar.MINUTE));
		else
			minuto = minuto + (date.getInstance().get(Calendar.MINUTE));

		if ((date.getInstance().get(Calendar.SECOND)) < 10)
			segundo = "0" + (date.getInstance().get(Calendar.SECOND));
		else
			segundo = segundo + (date.getInstance().get(Calendar.SECOND));

		returned = "[diff:\"" + date.getInstance().get(Calendar.YEAR) + "-" + mes + "-" + dia + "T" + hora + ":"
				+ minuto + ":" + segundo + "Z\"]";

		return returned;

	}

	/**
	 * Esta dentro coordenadas.
	 *
	 * @param trabajo
	 *            the trabajo
	 * @param nodo
	 *            the nodo
	 * @return true, if successful
	 */
	public static boolean estaDentroCoordenadas(Trabajo trabajo, Node nodo) {
		boolean returned = false;
		if (trabajo.getPuntoXPositivo() >= nodo.getLon() && trabajo.getPuntoYPositivo() >= nodo.getLat())
			returned = true;
		return returned;
	}

	/**
	 * Obtener nodos vacios.
	 *
	 * @param way
	 *            the way
	 * @param trabajo
	 *            the trabajo
	 * @param inserts
	 *            the inserts
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void obtenerNodosVacios(Way way, Trabajo trabajo, Map<String, Element> inserts) throws IOException {
		List<Node> nodes = way.getNodes();
		String Xapi = "";

		Node nodePerdido = null;
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			// TODO primero miramos si ya lo habiamos bajado en otra
			// peticion previa. Si no es así, se lanza una nueva
			if (isEmpty(node)) {
				nodePerdido = (Node) inserts.get(node.getId());
				if (nodePerdido != null) {
					if (estaDentroCoordenadas(trabajo, nodePerdido)) {
						nodePerdido.setAction("mutante");
						way.addNode(nodePerdido);
					}

				} else {
					Xapi = OVERPASS;
					Xapi = Xapi + "node/";
					Xapi = Xapi + node.getId().replace("N", "");
					System.out.println("recogemos nodo:" + Xapi);
					downloadFile(Xapi, SAVEDIR, "mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");
					// TODO cogemos los elementos del fichero
					Map<String, Element> osmMapeadoInsertsNode = ControllerUtils.Mapear(
							SAVEDIR+"/mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");
					//borramos el fichero
					File f = new File(SAVEDIR+"/mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");
					f.delete();
					
					for (Entry<String, Element> nuevo : osmMapeadoInsertsNode.entrySet()) {
						Node nodoUrl = (Node) nuevo.getValue();
						nodoUrl.setAction("mutante");
						if (estaDentroCoordenadas(trabajo, nodoUrl)) {
							way.addNode(nodoUrl);
						}

						inserts.put(nodoUrl.getId(), nodoUrl);

					}

				}
			}
		}
	}

	public static void obtenerNodosVacios(Way way, Trabajo trabajo, Map<String, Element> inserts,
			Map<String, Element> inputs) throws IOException {
		List<Node> nodes = way.getNodes();
		String Xapi = "";

		Node nodePerdido = null;
		Node nodePerdidoInput = null;
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			// TODO primero miramos si ya lo habiamos bajado en otra
			// peticion previa. Si no es así, se lanza una nueva
			if (isEmpty(node)) {
				nodePerdido = (Node) inserts.get(node.getId());
				nodePerdidoInput = (Node) inputs.get(node.getId());
				if (nodePerdido != null) {
					if (estaDentroCoordenadas(trabajo, nodePerdido)) {
						nodePerdido.setAction("mutante");
						way.addNode(nodePerdido);
					}

				} else if (nodePerdidoInput != null) {
					if (estaDentroCoordenadas(trabajo, nodePerdidoInput)) {
						nodePerdidoInput.setAction("mutante");
						way.addNode(nodePerdidoInput);
					}

				} else {
					Xapi = OVERPASS;
					Xapi = Xapi + "node/";
					Xapi = Xapi + node.getId().replace("N", "");
					System.out.println("recogemos nodo:" + Xapi);
					downloadFile(Xapi, SAVEDIR, "mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");
					// TODO cogemos los elementos del fichero
					Map<String, Element> osmMapeadoInsertsNode = ControllerUtils.Mapear(
							SAVEDIR+"/mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");

					//borramos el fichero
					File f = new File(SAVEDIR+"/mapNodeIdFromWay" + node.getId() + way.getId() + ".osm");
					f.delete();
					
					for (Entry<String, Element> nuevo : osmMapeadoInsertsNode.entrySet()) {
						Node nodoUrl = (Node) nuevo.getValue();
						nodoUrl.setAction("mutante");
						if (estaDentroCoordenadas(trabajo, nodoUrl)) {
							way.addNode(nodoUrl);
						}

						inputs.put(nodoUrl.getId(), nodoUrl);

					}

				}
			}
		}
	}

	public static boolean esFiltroNumerico(String operador) {
		boolean es = false;
		if (operador.equals("<") || operador.equals("<=") || operador.equals(">") || operador.equals(">="))
			es = true;
		return es;
	}
}