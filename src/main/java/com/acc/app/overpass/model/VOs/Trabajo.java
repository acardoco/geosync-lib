package com.acc.app.overpass.model.VOs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;





// TODO: Auto-generated Javadoc
/**
 * The Class Trabajo.
 */
@SuppressWarnings("unused")


public class Trabajo {


	/** The id trabajo. */
	private int idTrabajo;
	
	/** The nombre. */
	private String nombre;
	
	/** The punto y positivo. */
	private Double puntoYPositivo ;
	
	/** The punto y negativo. */
	private Double puntoYNegativo ;
	
	/** The punto x positivo. */
	private Double puntoXPositivo ;
	
	/** The punto x negativo. */
	private Double puntoXNegativo ;
	
	/** The transformaciones. */
	private List<Transformacion> transformaciones = new ArrayList<Transformacion>();
	
	/** The fecha ultima modificacion. */
	private Calendar fechaUltimaModificacion;
	
	/**
	 * Instantiates a new trabajo.
	 */
	public Trabajo() {
	}





	/**
	 * Instantiates a new trabajo.
	 *
	 * @param idTrabajo the id trabajo
	 * @param nombre the nombre
	 * @param puntoYPositivo the punto y positivo
	 * @param puntoYNegativo the punto y negativo
	 * @param puntoXPositivo the punto x positivo
	 * @param puntoXNegativo the punto x negativo
	 * @param transformaciones the transformaciones
	 * @param fechaUltimaModificacion the fecha ultima modificacion
	 */
	public Trabajo(int idTrabajo, String nombre, Double puntoYPositivo, Double puntoYNegativo, Double puntoXPositivo,
			Double puntoXNegativo, List<Transformacion> transformaciones, Calendar fechaUltimaModificacion) {
		super();
		this.idTrabajo = idTrabajo;
		this.nombre = nombre;
		this.puntoYPositivo = puntoYPositivo;
		this.puntoYNegativo = puntoYNegativo;
		this.puntoXPositivo = puntoXPositivo;
		this.puntoXNegativo = puntoXNegativo;
		this.transformaciones = transformaciones;
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}





	/**
	 * Instantiates a new trabajo.
	 *
	 * @param nombre the nombre
	 * @param puntoYPositivo the punto y positivo
	 * @param puntoYNegativo the punto y negativo
	 * @param puntoXPositivo the punto x positivo
	 * @param puntoXNegativo the punto x negativo
	 */
	public Trabajo( String nombre, Double puntoYPositivo, Double puntoYNegativo, Double puntoXPositivo,
			Double puntoXNegativo) {
		super();
		this.nombre = nombre;
		this.puntoYPositivo = puntoYPositivo;
		this.puntoYNegativo = puntoYNegativo;
		this.puntoXPositivo = puntoXPositivo;
		this.puntoXNegativo = puntoXNegativo;
		
	}



	/**
	 * Gets the id trabajo.
	 *
	 * @return the id trabajo
	 */

	public int getIdTrabajo() {
		return idTrabajo;
	}

	/**
	 * Sets the id trabajo.
	 *
	 * @param idTrabajo the new id trabajo
	 */
	public void setIdTrabajo(int idTrabajo) {
		this.idTrabajo =  idTrabajo;
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */

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
	 * Gets the transformaciones.
	 *
	 * @return the transformaciones
	 */

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
	 * Gets the punto y positivo.
	 *
	 * @return the punto y positivo
	 */

	//@Type(type="org.hibernate.spatial.GeometryType")
	public Double getPuntoYPositivo() {
		return puntoYPositivo;
	}



	/**
	 * Sets the punto y positivo.
	 *
	 * @param puntoYPositivo the new punto y positivo
	 */
	public void setPuntoYPositivo(Double puntoYPositivo) {
		this.puntoYPositivo = puntoYPositivo;
	}



	/**
	 * Gets the punto y negativo.
	 *
	 * @return the punto y negativo
	 */

	//@Type(type="org.hibernate.spatial.GeometryType")
	public Double getPuntoYNegativo() {
		return puntoYNegativo;
	}



	/**
	 * Sets the punto y negativo.
	 *
	 * @param puntoYNegativo the new punto y negativo
	 */
	public void setPuntoYNegativo(Double puntoYNegativo) {
		this.puntoYNegativo = puntoYNegativo;
	}



	/**
	 * Gets the punto x positivo.
	 *
	 * @return the punto x positivo
	 */

	//@Type(type="org.hibernate.spatial.GeometryType")
	public Double getPuntoXPositivo() {
		return puntoXPositivo;
	}



	/**
	 * Sets the punto x positivo.
	 *
	 * @param puntoXPositivo the new punto x positivo
	 */
	public void setPuntoXPositivo(Double puntoXPositivo) {
		this.puntoXPositivo = puntoXPositivo;
	}



	/**
	 * Gets the punto x negativo.
	 *
	 * @return the punto x negativo
	 */

	//@Type(type="org.hibernate.spatial.GeometryType")
	public Double getPuntoXNegativo() {
		return puntoXNegativo;
	}



	/**
	 * Sets the punto x negativo.
	 *
	 * @param puntoXNegativo the new punto x negativo
	 */
	public void setPuntoXNegativo(Double puntoXNegativo) {
		this.puntoXNegativo = puntoXNegativo;
	}



	/**
	 * Gets the fecha ultima modificacion.
	 *
	 * @return the fecha ultima modificacion
	 */

	public Calendar getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}




	/**
	 * Sets the fecha ultima modificacion.
	 *
	 * @param fechaUltimaModificacion the new fecha ultima modificacion
	 */
	public void setFechaUltimaModificacion(Calendar fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	
	

}
