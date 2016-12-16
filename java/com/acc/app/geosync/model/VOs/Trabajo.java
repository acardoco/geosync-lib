package com.acc.app.geosync.model.VOs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.JoinColumn;



// TODO: Auto-generated Javadoc
/**
 * The Class Trabajo.
 */
@SuppressWarnings("unused")

@Entity()
@Table()
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
	@Id
	@SequenceGenerator(name = "idTrabajo", sequenceName = "id_trabajo_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idTrabajo")
	@Column(name = "idTrabajo")
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
	@Column()
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
	@OneToMany(mappedBy="idTrabajo",cascade={CascadeType.ALL})
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
	@Column()
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
	@Column()
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
	@Column()
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
	@Column()
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
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
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
