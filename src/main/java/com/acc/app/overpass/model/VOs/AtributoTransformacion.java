package com.acc.app.overpass.model.VOs;


@SuppressWarnings("unused")


public class AtributoTransformacion {

	private Integer idAtributoTransformacion;
	private AtributoBD idAtributoBD;
	private AtributoOSM idAtributoOSM;
	private Transformacion idTransformacion;

	public AtributoTransformacion() {
		super();
	}

	public AtributoTransformacion(Integer idMapeadoAtributos, AtributoBD idAtributoBD, AtributoOSM idAtributoOSM, Transformacion idTransformacion) {
		super();
		this.idAtributoTransformacion = idMapeadoAtributos;
		this.idAtributoBD = idAtributoBD;
		this.idAtributoOSM = idAtributoOSM;
		this.idTransformacion = idTransformacion;
	}


	public Integer getIdAtributoTransformacion() {
		return idAtributoTransformacion;
	}

	public void setIdAtributoTransformacion(Integer idMapeadoAtributos) {
		this.idAtributoTransformacion = idMapeadoAtributos;
	}


	public AtributoBD getIdAtributoBD() {
		return idAtributoBD;
	}

	public void setIdAtributoBD(AtributoBD idAtributoBD) {
		this.idAtributoBD = idAtributoBD;
	}


	public AtributoOSM getIdAtributoOSM() {
		return idAtributoOSM;
	}

	public void setIdAtributoOSM(AtributoOSM idAtributoOSM) {
		this.idAtributoOSM = idAtributoOSM;
	}


	public Transformacion getIdTransformacion() {
		return idTransformacion;
	}

	public void setIdTransformacion(Transformacion idTransformacion) {
		this.idTransformacion = idTransformacion;
	}

	
}
