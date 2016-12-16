package com.acc.app.overpass.model.VOs;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class ConceptoBD.
 */
@SuppressWarnings("unused")


public class ConceptoBD {

	private Integer idConceptoBD;

	private String nombre;

	private String nombreTabla;

	private List<Transformacion> transformaciones = new ArrayList<Transformacion>();
	
	private List<AtributoBD> atributos = new ArrayList<AtributoBD>();
	
	//info de conexiones
	
	private String nombreBD;
	
	private String userBD;
	
	private String password;
	
	


	/**
	 * Instantiates a new concepto bd.
	 */
	public ConceptoBD() {
	}


	public ConceptoBD(Integer idConceptoBD, String nombre, String nombreTabla, List<Transformacion> transformaciones,
			List<AtributoBD> atributos, String nombreBD, String userBD, String password) {
		super();
		this.idConceptoBD = idConceptoBD;
		this.nombre = nombre;
		this.nombreTabla = nombreTabla;
		this.transformaciones = transformaciones;
		this.atributos = atributos;
		this.nombreBD = nombreBD;
		this.userBD = userBD;
		this.password = password;
	}


	/**
	 * Gets the id concepto bd.
	 *
	 * @return the id concepto bd
	 */

	public Integer getIdConceptoBD() {
		return idConceptoBD;
	}

	/**
	 * Sets the id concepto bd.
	 *
	 * @param idConceptoBD
	 *            the new id concepto bd
	 */
	public void setIdConceptoBD(Integer idConceptoBD) {
		this.idConceptoBD = idConceptoBD;
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
	 * @param nombre
	 *            the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the nombre tabla.
	 *
	 * @return the nombre tabla
	 */

	public String getNombreTabla() {
		return nombreTabla;
	}

	/**
	 * Sets the nombre tabla.
	 *
	 * @param nombreTabla
	 *            the new nombre tabla
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * Gets the transformaciones.
	 *
	 * @return the transformaciones
	 */

	public List<Transformacion> getTransformaciones() {
		return transformaciones;
	}

	/**
	 * Sets the transformaciones.
	 *
	 * @param transformaciones
	 *            the new transformaciones
	 */
	public void setTransformaciones(List<Transformacion> transformaciones) {
		this.transformaciones = transformaciones;
	}



	/**
	 * Gets the atributos.
	 *
	 * @return the atributos
	 */

	public List<AtributoBD> getAtributos() {
		return atributos;
	}



	/**
	 * Sets the atributos.
	 *
	 * @param atributos the new atributos
	 */
	public void setAtributos(List<AtributoBD> atributos) {
		this.atributos = atributos;
	}



	public String getNombreBD() {
		return nombreBD;
	}


	public void setNombreBD(String nombreBD) {
		this.nombreBD = nombreBD;
	}



	public String getUserBD() {
		return userBD;
	}


	public void setUserBD(String userBD) {
		this.userBD = userBD;
	}



	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
