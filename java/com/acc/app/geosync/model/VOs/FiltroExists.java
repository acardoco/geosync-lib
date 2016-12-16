package com.acc.app.geosync.model.VOs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table()
@DiscriminatorValue("FE")
public class FiltroExists extends Filtro {

	public FiltroExists() {
		super();
	}

}
