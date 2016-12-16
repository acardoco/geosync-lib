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
public class AtributoOSM {

	private Integer idAtributoOSM;
	private String clave;
	private ConceptoOSM idConceptoOSM;
	public AtributoOSM() {
		super();
	}
	public AtributoOSM(Integer id, String clave, ConceptoOSM idConceptoOSM) {
		super();
		this.idAtributoOSM = id;
		this.clave = clave;
		this.idConceptoOSM = idConceptoOSM;
	}
	@Id
	@SequenceGenerator(name = "idAtributoOSM", sequenceName = "id_atributoOSM_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idAtributoOSM")
	@Column()
	public Integer getIdAtributoOSM() {
		return idAtributoOSM;
	}
	public void setIdAtributoOSM(Integer id) {
		this.idAtributoOSM = id;
	}
	@Column()
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idConceptoOSM")
	public ConceptoOSM getIdConceptoOSM() {
		return idConceptoOSM;
	}
	public void setIdConceptoOSM(ConceptoOSM idConceptoOSM) {
		this.idConceptoOSM = idConceptoOSM;
	}
	
}
