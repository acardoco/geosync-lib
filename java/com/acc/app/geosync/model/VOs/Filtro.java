package com.acc.app.geosync.model.VOs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_filtro", discriminatorType = DiscriminatorType.STRING)
@Table()
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
	@Id
	@SequenceGenerator(name = "idFiltro", sequenceName = "id_filtro_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idFiltro")
	@Column()
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idConceptoOSM", nullable = false, referencedColumnName = "idConceptoOSM")
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
	@Column()
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
	@Column()
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
