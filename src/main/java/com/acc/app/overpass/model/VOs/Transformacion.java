package com.acc.app.overpass.model.VOs;

import java.util.ArrayList;
import java.util.List;




/**
 * The Class Transformacion.
 */
@SuppressWarnings("unused")


public class Transformacion {
	
	private int idTransformacion;
	private String nombre;
	private ConceptoOSM idConceptoOSM;
	private ConceptoBD idConceptoBD;
	private Trabajo idTrabajo;
	private List<AtributoTransformacion> atributos = new ArrayList<AtributoTransformacion>();
	
	/**
	 * Instantiates a new transformacion.
	 */
	public Transformacion() {
		
	}



	



	/**
	 * Instantiates a new transformacion.
	 *
	 * @param idTransformacion the id transformacion
	 * @param nombre the nombre
	 * @param idConceptoOSM the id concepto osm
	 * @param idConceptoBD the id concepto bd
	 * @param idTrabajo the id trabajo
	 * @param atributos the atributos
	 */
	public Transformacion(int idTransformacion, String nombre, ConceptoOSM idConceptoOSM, ConceptoBD idConceptoBD,
			Trabajo idTrabajo, List<AtributoTransformacion> atributos) {
		super();
		this.idTransformacion = idTransformacion;
		this.nombre = nombre;
		this.idConceptoOSM = idConceptoOSM;
		this.idConceptoBD = idConceptoBD;
		this.idTrabajo = idTrabajo;
		this.atributos = atributos;
	}







	/**
	 * Gets the id transformacion.
	 *
	 * @return the id transformacion
	 */

	public int getIdTransformacion() {
		return idTransformacion;
	}

	/**
	 * Sets the id transformacion.
	 *
	 * @param idTransformacion the new id transformacion
	 */
	public void setIdTransformacion(int idTransformacion) {
		this.idTransformacion = idTransformacion;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */

	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * Gets the id concepto osm.
	 *
	 * @return the id concepto osm
	 */

	public ConceptoOSM  getIdConceptoOSM() {
		return idConceptoOSM;
	}



	/**
	 * Sets the id concepto osm.
	 *
	 * @param idConceptoOSM the new id concepto osm
	 */
	public void setIdConceptoOSM(ConceptoOSM  idConceptoOSM) {
		this.idConceptoOSM = idConceptoOSM;
	}

	/**
	 * Gets the id concepto bd.
	 *
	 * @return the id concepto bd
	 */

	public ConceptoBD getIdConceptoBD() {
		return idConceptoBD;
	}


	/**
	 * Sets the id concepto bd.
	 *
	 * @param idConceptoBD the new id concepto bd
	 */
	public void setIdConceptoBD(ConceptoBD idConceptoBD) {
		this.idConceptoBD = idConceptoBD;
	}



	/**
	 * Gets the id trabajo.
	 *
	 * @return the id trabajo
	 */

	public Trabajo getIdTrabajo() {
		return idTrabajo;
	}



	/**
	 * Sets the id trabajo.
	 *
	 * @param idTrabajo the new id trabajo
	 */
	public void setIdTrabajo(Trabajo idTrabajo) {
		this.idTrabajo = idTrabajo;
	}






	/**
	 * Gets the atributos.
	 *
	 * @return the atributos
	 */

	public List<AtributoTransformacion> getAtributos() {
		return atributos;
	}







	/**
	 * Sets the atributos.
	 *
	 * @param atributos the new atributos
	 */
	public void setAtributos(List<AtributoTransformacion> atributos) {
		this.atributos = atributos;
	}
	
	

}
