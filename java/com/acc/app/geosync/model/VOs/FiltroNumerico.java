package com.acc.app.geosync.model.VOs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class FiltroClaveValor.
 */
@SuppressWarnings("unused")

@Entity
@Table()
@DiscriminatorValue("FN")
public class FiltroNumerico extends Filtro {

	private String valor;

	/**
	 * Instantiates a new filtro clave valor.
	 */
	public FiltroNumerico() {
		super();
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	@Column()
	public String getValor() {
		return valor;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor
	 *            the new valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}
