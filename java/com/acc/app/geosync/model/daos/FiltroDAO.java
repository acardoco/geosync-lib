package com.acc.app.geosync.model.daos;

import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;

public interface FiltroDAO {
	
	public int create(Filtro filtroClaveValor); 
	public void update(Filtro filtroClaveValor);
	public void delete(Filtro filtroClaveValor);
	public Filtro find(int idFiltro);
	
}
