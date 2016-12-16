package com.acc.app.overpass.model.VOs;



@SuppressWarnings("unused")


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

	public Integer getIdAtributoBD() {
		return idAtributoBD;
	}
	public void setIdAtributoBD(Integer id) {
		this.idAtributoBD = id;
	}

	public String getNombre() {
		return  nombre;
	}
	public void setNombre(String clave) {
		this. nombre = clave;
	}


	public ConceptoBD getIdConceptoBD() {
		return idConceptoBD;
	}
	public void setIdConceptoBD(ConceptoBD idConceptoBD) {
		this.idConceptoBD = idConceptoBD;
	}
	
}

