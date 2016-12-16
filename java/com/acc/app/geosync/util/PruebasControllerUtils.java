package com.acc.app.geosync.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.acc.app.geosync.util.dbMap.JdbcPostgresUtil;

import info.pavie.basicosmparser.controller.OSMParser;
import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Way;
import info.pavie.basicosmparser.model.Node;

public class PruebasControllerUtils {
	

	

	public static void main(String[] args) throws IOException {
		File file = new File("./"+File.separator+"test1.xml");
		file.createNewFile();
		System.out.println(file.getAbsoluteFile());// prints "c:\\eclipse\\eclipse.ini

	}
	// public static void main(String[] args) {
	// String fileURL =
	// "http://api06.dev.openstreetmap.org/api/0.6/map?bbox=11.54,48.14,11.543,48.145";
	// String saveDir = "C:\\Users\\Andrés\\Downloads";
	// Map<String, Element> osmMapeado =
	// ControllerUtils.Mapear("C:/Users/Andrés/Downloads/interpreter.osm");
	// int contadorOLD = 0;
	// int del = 0;
	// int cre = 0;
	// int nodostot = 0;
	// int nodos = 0;
	// for (Entry<String, Element> entry : osmMapeado.entrySet()) {
	//
	//
	// if (entry.getValue().getAction().equals("modify")) {
	// contadorOLD = contadorOLD + 1;
	// } else if (entry.getValue().getAction().equals("delete")) {
	// del = del + 1;
	// } else if (entry.getValue().getAction().equals("create")) {
	// cre = cre + 1;
	// }
	//
	// if (entry.getKey().startsWith("W")) {
	// Way w = (Way) entry.getValue();
	//
	// for (Node e : w.getNodes()) {
	// if (ControllerUtils.isEmpty(e)) {
	//
	// nodos++;
	// }
	// nodostot++;
	// }
	// }
	// }
	// System.out.println("modify:" + contadorOLD);
	// System.out.println("del:" + del);
	// System.out.println("cre:" + cre);
	// System.out.println("Nodos action:" + nodos);
	// System.out.println("Nodos totales:" + nodostot);
	// OSMParser.printStatistics(osmMapeado);
	//
	// }

	// public static void main(String[] args) {
	// Calendar fechaActual = Calendar.getInstance();
	// System.out.println("Fecha:"+ControllerUtils.dateToStringChangeset(fechaActual));
	// }
}
