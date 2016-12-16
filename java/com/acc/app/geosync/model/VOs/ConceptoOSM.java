package com.acc.app.geosync.model.VOs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The Class ConceptoOSM.
 */
@SuppressWarnings("unused")

@Entity
@Table()
public class ConceptoOSM {


	private Integer idConceptoOSM;
	
	private String nombre;
	/*
	 * nodo
	 * camino
	 * relacion
	 * todos
	 */
	private String tipo;

	private List<Transformacion> transformaciones = new ArrayList<Transformacion>();
	
	private List<Filtro> filtros = new ArrayList<Filtro>();
	
	private List<AtributoOSM> atributos = new ArrayList<AtributoOSM>();
	
	/**
	 * Instantiates a new concepto osm.
	 */
	public ConceptoOSM() {
	}




	public ConceptoOSM(Integer idConceptoOSM, String nombre, String tipo, List<Transformacion> transformaciones,
			List<Filtro> filtros, List<AtributoOSM> atributos) {
		super();
		this.idConceptoOSM = idConceptoOSM;
		this.nombre = nombre;
		this.tipo = tipo;
		this.transformaciones = transformaciones;
		this.filtros = filtros;
		this.atributos = atributos;
	}




	/**
	 * Gets the id concepto osm.
	 *
	 * @return the id concepto osm
	 */
	@Id
	@SequenceGenerator(name = "idConceptoOSM", sequenceName = "id_conceptoOSM_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idConceptoOSM")
	@Column()
	public Integer getIdConceptoOSM() {
		return idConceptoOSM;
	}

	/**
	 * Sets the id concepto osm.
	 *
	 * @param idConceptoOSM the new id concepto osm
	 */
	public void setIdConceptoOSM(Integer idConceptoOSM) {
		this.idConceptoOSM = idConceptoOSM;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	@Column(unique=true)
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
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	@Column(nullable=false)
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo the new tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the transformaciones.
	 *
	 * @return the transformaciones
	 */
	@OneToMany(mappedBy="idConceptoOSM")
	public List<Transformacion> getTransformaciones() {
		return transformaciones;
	}

	/**
	 * Sets the transformaciones.
	 *
	 * @param transformaciones the new transformaciones
	*/
	public void setTransformaciones(List<Transformacion> transformaciones) {
		this.transformaciones = transformaciones;
	}

	/**
	 * Gets the filtros.
	 *
	 * @return the filtros
	 */
	@OneToMany(mappedBy="idConceptoOSM", cascade={CascadeType.ALL})
	public List<Filtro> getFiltros() {
		return filtros;
	}

	/**
	 * Sets the filtros.
	 *
	 * @param filtros the new filtros
	*/
	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}



	/**
	 * Gets the atributos.
	 *
	 * @return the atributos
	 */
	@OneToMany(mappedBy="idConceptoOSM", cascade={CascadeType.ALL})
	public List<AtributoOSM> getAtributos() {
		return atributos;
	}




	/**
	 * Sets the atributos.
	 *
	 * @param atributos the new atributos
	 */
	public void setAtributos(List<AtributoOSM> atributos) {
		this.atributos = atributos;
	}

	
	
}
