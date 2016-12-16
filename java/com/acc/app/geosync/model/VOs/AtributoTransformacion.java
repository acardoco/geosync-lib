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

	@Id
	@SequenceGenerator(name = "idAtributoTransformacion", sequenceName = "id_atributoTransformacion_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idAtributoTransformacion")
	@Column()
	public Integer getIdAtributoTransformacion() {
		return idAtributoTransformacion;
	}

	public void setIdAtributoTransformacion(Integer idMapeadoAtributos) {
		this.idAtributoTransformacion = idMapeadoAtributos;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idAtributoBD")
	public AtributoBD getIdAtributoBD() {
		return idAtributoBD;
	}

	public void setIdAtributoBD(AtributoBD idAtributoBD) {
		this.idAtributoBD = idAtributoBD;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idAtributoOSM")
	public AtributoOSM getIdAtributoOSM() {
		return idAtributoOSM;
	}

	public void setIdAtributoOSM(AtributoOSM idAtributoOSM) {
		this.idAtributoOSM = idAtributoOSM;
	}

	@ManyToOne()
	@JoinColumn(name="idTransformacion")
	public Transformacion getIdTransformacion() {
		return idTransformacion;
	}

	public void setIdTransformacion(Transformacion idTransformacion) {
		this.idTransformacion = idTransformacion;
	}

	
}
