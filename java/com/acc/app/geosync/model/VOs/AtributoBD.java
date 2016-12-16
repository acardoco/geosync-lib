package com.acc.app.geosync.model.VOs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("unused")

@Entity
@Table()
public class AtributoBD {

	private Integer idAtributoBD;
	private String nombre;
	private ConceptoBD idConceptoBD;
	public AtributoBD() {
		super();
	}
	public AtributoBD(Integer id, String clave, ConceptoBD idConceptoBD) {
		super();
		this.idAtributoBD = id;
		this.nombre = clave;
		this.idConceptoBD = idConceptoBD;
	}
	@Id
	@SequenceGenerator(name = "idAtributoBD", sequenceName = "id_atributoBD_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idAtributoBD")
	@Column()
	public Integer getIdAtributoBD() {
		return idAtributoBD;
	}
	public void setIdAtributoBD(Integer id) {
		this.idAtributoBD = id;
	}
	@Column()
	public String getNombre() {
		return  nombre;
	}
	public void setNombre(String clave) {
		this. nombre = clave;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idConceptoBD")
	public ConceptoBD getIdConceptoBD() {
		return idConceptoBD;
	}
	public void setIdConceptoBD(ConceptoBD idConceptoBD) {
		this.idConceptoBD = idConceptoBD;
	}
	
}

