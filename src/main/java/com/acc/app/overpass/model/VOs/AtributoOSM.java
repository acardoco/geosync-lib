package com.acc.app.overpass.model.VOs;


@SuppressWarnings("unused")


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

	public Integer getIdAtributoOSM() {
		return idAtributoOSM;
	}
	public void setIdAtributoOSM(Integer id) {
		this.idAtributoOSM = id;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}


	public ConceptoOSM getIdConceptoOSM() {
		return idConceptoOSM;
	}
	public void setIdConceptoOSM(ConceptoOSM idConceptoOSM) {
		this.idConceptoOSM = idConceptoOSM;
	}
	
}
