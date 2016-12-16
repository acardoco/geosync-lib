package com.acc.app.overpass.model.VOs;




public class Filtro {

	protected int idFiltro;

	protected ConceptoOSM idConceptoOSM;

	private String clave;

	private String operacion;

	public Filtro() {

	}

	/*
	 * Gets the id filtro.
	 *
	 * @return the id filtro
	 */

	public int getIdFiltro() {
		return idFiltro;
	}

	/**
	 * Sets the id filtro.
	 *
	 * @param idFiltro
	 *            the new id filtro
	 */
	public void setIdFiltro(int idFiltro) {
		this.idFiltro = idFiltro;
	}

	/**
	 * Gets the id concepto osm.
	 *
	 * @return the id concepto osm
	 */

	public ConceptoOSM getIdConceptoOSM() {
		return idConceptoOSM;
	}

	/**
	 * Sets the id concepto osm.
	 *
	 * @param idConceptoOSM
	 *            the new id concepto osm
	 */
	public void setIdConceptoOSM(ConceptoOSM idConceptoOSM) {
		this.idConceptoOSM = idConceptoOSM;
	}

	/**
	 * Gets the clave.
	 *
	 * @return the clave
	 */

	public String getClave() {
		return clave;
	}

	/**
	 * Sets the clave.
	 *
	 * @param clave
	 *            the new clave
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * Gets the operacion.
	 *
	 * @return the operacion
	 */

	public String getOperacion() {
		return operacion;
	}

	/**
	 * Sets the operacion.
	 *
	 * @param operacion the new operacion
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

}
