/*
	Copyright 2014 Adrien PAVIE
	
	This file is part of BasicOSMParser.
	
	BasicOSMParser is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	BasicOSMParser is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with BasicOSMParser. If not, see <http://www.gnu.org/licenses/>.
 */

package info.pavie.basicosmparser.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Relation;
import info.pavie.basicosmparser.model.Way;

/**
 * OSMParser parses XML file (OSM database extracts) and creates corresponding
 * Java objects.
 * 
 * @author Adrien PAVIE
 */
public class OSMParser extends DefaultHandler {
	// ATTRIBUTES
	/** The parsed OSM elements **/
	private Map<String, Element> elements;
	/** The current read element **/
	private Element current;
	// contadores de utilidad
	static int contadorTotales = 0;
	static int contadorPut = 0;
	static int contadorModify = 0;

	static boolean delete = false;
	static boolean create = false;
	// action==modify->modify = 2 -> old ->modify - 1 -> new -> modify -1 ---
	// modify vuelve a 0
	static int modify = 0;

	// CONSTRUCTOR
	public OSMParser() {
		super();
	}

	// OTHER METHODS
	/**
	 * Parses a XML file and creates OSM Java objects
	 * 
	 * @param f
	 *            The OSM database extract, in XML format, as a file
	 * @return The corresponding OSM objects as a Map. Keys are elements ID, and
	 *         values are OSM elements objects.
	 * @throws IOException
	 *             If an error occurs during file reading
	 * @throws SAXException
	 *             If an error occurs during parsing
	 */
	public Map<String, Element> parse(File f) throws IOException, SAXException {
		// File check
		if (!f.exists() || !f.isFile()) {
			throw new FileNotFoundException();
		}

		if (!f.canRead()) {
			throw new IOException("Can't read file");
		}
		InputStream inputS = new FileInputStream(f);
		Reader r = new InputStreamReader(inputS, "UTF-8");

		InputSource is = new InputSource(r);
		is.setEncoding("UTF-8");

		return parse(is);
	}

	/**
	 * Parses a XML file and creates OSM Java objects
	 * 
	 * @param s
	 *            The OSM database extract, in XML format, as a String
	 * @return The corresponding OSM objects as a Map. Keys are elements ID, and
	 *         values are OSM elements objects.
	 * @throws IOException
	 *             If an error occurs during file reading
	 * @throws SAXException
	 *             If an error occurs during parsing
	 */
	public Map<String, Element> parse(String s) throws SAXException, IOException {
		return parse(new InputSource(new ByteArrayInputStream(s.getBytes("UTF-8"))));
	}

	/**
	 * Parses a XML input and creates OSM Java objects
	 * 
	 * @param input
	 *            The OSM database extract, in XML format, as an InputSource
	 * @return The corresponding OSM objects as a Map. Keys are elements ID, and
	 *         values are OSM elements objects.
	 * @throws IOException
	 *             If an error occurs during reading
	 * @throws SAXException
	 *             If an error occurs during parsing
	 */
	public Map<String, Element> parse(InputSource input) throws SAXException, IOException {
		// Init elements set
		elements = new HashMap<String, Element>();

		// Start parsing
		XMLReader xr = XMLReaderFactory.createXMLReader();
		xr.setContentHandler(this);
		xr.setErrorHandler(this);
		xr.parse(input);

		return elements;
	}

	/**
	 * Get an object ID, in this format: X000000, where X is the object type (N
	 * for nodes, W for ways, R for relations).
	 * 
	 * @param type
	 *            The object type (node, way or relation)
	 * @param ref
	 *            The object ID in a given type
	 * @return The object ID, unique for all types
	 */
	private String getId(String type, String ref) {
		String result = null;

		switch (type) {
		case "node":
			result = "N" + ref;
			break;
		case "way":
			result = "W" + ref;
			break;
		case "relation":
			result = "R" + ref;
			break;
		default:
			throw new RuntimeException("Unknown element type: " + type);
		}

		return result;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);

		if (localName.equals("node") || localName.equals("way") || localName.equals("relation")) {
			// Add element to list, and delete current
			if (current != null) {
				if ((localName.equals("way") && ((Way) current).getNodes().size() >= 2) || localName.equals("node")
						|| (localName.equals("relation") && ((Relation) current).getMembers().size() > 0)) {
					// TODO changeset
					if (delete == true)
						current.setAction("delete");
					else if (create == true)
						current.setAction("create");
					else if (modify == 2) {//TODO OLD
						current.setAction("modify");
						elements.put(current.getId() + "OLD", current);
						modify = modify - 1;
					} else if (modify == 1) {//TODO NEW
						current.setAction("modify");
						elements.put(current.getId(), current);
						modify = modify - 1;
					}
					if (modify == 0)
						elements.put(current.getId(), current);

					contadorPut = contadorPut + 1;
					delete = false;
					create = false;
					// --
				}
				current = null;
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		// Case of node
		if (localName.equals("node")) {
			Node n = new Node(Long.parseLong(attributes.getValue("id")), Double.parseDouble(attributes.getValue("lat")),
					Double.parseDouble(attributes.getValue("lon")));
			n.setUser(attributes.getValue("user"));

			if (attributes.getValue("uid") != null) {
				n.setUid(Long.parseLong(attributes.getValue("uid")));
			}

			n.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

			if (attributes.getValue("version") != null) {
				n.setVersion(Integer.parseInt(attributes.getValue("version")));
			}

			if (attributes.getValue("changeset") != null) {
				n.setChangeset(Long.parseLong(attributes.getValue("changeset")));
			}

			n.setTimestamp(attributes.getValue("timestamp"));
			current = n;
			contadorTotales = contadorTotales + 1;
		}
		// Case of way
		else if (localName.equals("way")) {
			Way w = new Way(Long.parseLong(attributes.getValue("id")));
			w.setUser(attributes.getValue("user"));

			if (attributes.getValue("uid") != null) {
				w.setUid(Long.parseLong(attributes.getValue("uid")));
			}

			w.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

			if (attributes.getValue("version") != null) {
				w.setVersion(Integer.parseInt(attributes.getValue("version")));
			}

			if (attributes.getValue("changeset") != null) {
				w.setChangeset(Long.parseLong(attributes.getValue("changeset")));
			}

			w.setTimestamp(attributes.getValue("timestamp"));
			current = w;
			contadorTotales = contadorTotales + 1;
		}
		// Case of way node
		else if (localName.equals("nd")) {
			// TODO añadido, comprobar que nodo no sea nulo
			Node nd = (Node) elements.get("N" + attributes.getValue("ref"));

			if (nd == null) {
				nd = new Node(Long.parseLong(attributes.getValue("ref")), 0, 0);
				((Way) current).addNode(nd);
			} else
				((Way) current).addNode(nd);

		}
		// Case of relation
		else if (localName.equals("relation")) {
			Relation r = new Relation(Long.parseLong(attributes.getValue("id")));
			r.setUser(attributes.getValue("user"));

			if (attributes.getValue("uid") != null) {
				r.setUid(Long.parseLong(attributes.getValue("uid")));
			}

			r.setVisible(Boolean.parseBoolean(attributes.getValue("visible")));

			if (attributes.getValue("version") != null) {
				r.setVersion(Integer.parseInt(attributes.getValue("version")));
			}

			if (attributes.getValue("changeset") != null) {
				r.setChangeset(Long.parseLong(attributes.getValue("changeset")));
			}

			r.setTimestamp(attributes.getValue("timestamp"));
			current = r;
			contadorTotales = contadorTotales + 1;
		}
		// Case of relation member
		else if (localName.equals("member")) {
			String refMember = getId(attributes.getValue("type"), attributes.getValue("ref"));

			// If member isn't contained in data, create stub object
			Element elemMember = null;
			if (!elements.containsKey(refMember)) {
				switch (attributes.getValue("type")) {
				case "node":
					elemMember = new Node(Long.parseLong(attributes.getValue("ref")), 0, 0);
					break;
				case "way":
					elemMember = new Way(Long.parseLong(attributes.getValue("ref")));
					break;
				case "relation":
					elemMember = new Relation(Long.parseLong(attributes.getValue("ref")));
					break;
				}
			} else {
				elemMember = elements.get(refMember);
			}

			// Add member
			((Relation) current).addMember(attributes.getValue("role"), elemMember);
		}
		// Case of tag
		else if (localName.equals("tag")) {
			if (current != null) {
				current.addTag(attributes.getValue("k"), attributes.getValue("v"));
			}
		} else if (localName.equals("action")) {
			if (attributes.getValue("type").equals("delete")) {
				delete = true;
			}
			if (attributes.getValue("type").equals("create")) {
				create = true;
			}
			if (attributes.getValue("type").equals("modify")) {
				modify = 2;
			}
		}

	}

	/**
	 * Displays some statistics about given elements
	 * 
	 * @param elements
	 *            The elements
	 */
	public static void printStatistics(Map<String, Element> elements) {
		int nbNodes = 0, nbWays = 0, nbRels = 0;

		for (Element e : elements.values()) {
			if (e instanceof Node) {
				nbNodes++;
			} else if (e instanceof Way) {
				nbWays++;
			} else if (e instanceof Relation) {
				nbRels++;
			}
		}

		System.out.println("= Elements statistics =");
		System.out.println("* Nodes:\t" + nbNodes);
		System.out.println("* Ways:\t\t" + nbWays);
		System.out.println("* Relations:\t" + nbRels);
		System.out.println("* Elementos totaltes: " + contadorTotales);
		System.out.println("* Elementos insertados: " + elements.size());
		System.out.println("* Elementos modificados: " + contadorModify);
	}
}
