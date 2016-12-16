package com.acc.app.geosync.web;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.acc.app.geosync.apis.XAPI;
import com.acc.app.geosync.model.VOs.AtributoBD;
import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.service.ConceptoService;
import com.acc.app.geosync.service.TrabajoService;
import com.acc.app.geosync.service.TransformacionService;
import com.acc.app.geosync.util.ControllerUtils;
import com.acc.app.geosync.util.dbMap.JdbcPostgresInsert;

import info.pavie.basicosmparser.model.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class TrabajosController.
 */
@Controller
@RequestMapping(value = "/trabajos")
public class TrabajosController {

	/** The trabajo service. */
	@Autowired
	private TrabajoService trabajoService;

	/** The xapi. */
	@Autowired
	private XAPI xapi;

	// -------DE TRABAJOS-----------------------------------------------------
	/**
	 * Trabajo list.
	 *
	 * @param model
	 *            the model
	 */
	// listar trabajos
	@RequestMapping(value = "/trabajoList", method = RequestMethod.GET)
	public void trabajoList(Model model) {
		List<Trabajo> list = trabajoService.TrabajosTotales();
		model.addAttribute("trabajoList", list);
	}

	/**
	 * Form.
	 *
	 * @param model
	 *            the model
	 * @return the trabajo
	 */
	// crear un nuevo trabajo
	@ModelAttribute("trabajo")
	@RequestMapping(value = "/trabajoFormAdd", method = RequestMethod.GET)
	public Trabajo form(Model model) {
		return new Trabajo();
	}

	/**
	 * Adds the trabajo.
	 *
	 * @param trabajo
	 *            the trabajo
	 * @param model
	 *            the model
	 * @param result
	 *            the result
	 * @param principal
	 *            the principal
	 * @return the string
	 */
	// acción del form crear un nuevo trabajo
	@RequestMapping(value = "/addTrabajo", method = RequestMethod.POST)
	public String addTrabajo(@ModelAttribute("trabajo") Trabajo trabajo, Model model, BindingResult result,
			Principal principal) {

		try {
			trabajoService.CrearTrabajo(trabajo);
			return "redirect:/trabajos/trabajoList";
		} catch (Exception e) {
			return "redirect:/trabajos/trabajoList";
		}

	}

	/**
	 * Trabajo delete.
	 *
	 * @param model
	 *            the model
	 * @param id
	 *            the id
	 * @return the string
	 */
	// borrar un trabajo
	@RequestMapping(value = "/trabajoDelete", method = RequestMethod.GET)
	public String trabajoDelete(Model model, @RequestParam("id") int id) {

		try {
			Trabajo tra = trabajoService.BuscarTrabajo(id);
			trabajoService.EliminarTrabajo(tra);
			return "redirect:/trabajos/trabajoList";
		} catch (Exception e) {
			return "redirect:/trabajos/trabajoList";
		}

	}

	/**
	 * Trabajo form update.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the object
	 */
	// actualizar un trabajo
	@ModelAttribute("trabajo")
	@RequestMapping(value = "/trabajoFormUpdate", method = RequestMethod.GET)
	public Object trabajoFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			return trabajoService.BuscarTrabajo(id);
		} catch (Exception e) {
			return "redirect:/geosync";
		}
	}

	/**
	 * Update trabajo.
	 *
	 * @param trabajo
	 *            the trabajo
	 * @param binding
	 *            the binding
	 * @param model
	 *            the model
	 * @return the string
	 */
	// acción actualizar un trabajo
	@RequestMapping(value = "/updateTrabajo", method = RequestMethod.POST)
	public String updateTrabajo(@ModelAttribute("trabajo") Trabajo trabajo, BindingResult binding, Model model) {

		try {
			// reseteamos la fecha si se cambia la zona de interes
			Trabajo trabajoAntiguo = trabajoService.BuscarTrabajo(trabajo.getIdTrabajo());
			if (compararZonas(trabajoAntiguo, trabajo))
				trabajo.setFechaUltimaModificacion(null);
			trabajoService.ActualizarTrabajo(trabajo);
			return "redirect:/trabajos/trabajoDetails?id=" + trabajo.getIdTrabajo();
		} catch (Exception e) {
			return "redirect:/trabajos";
		}

	}

	private boolean compararZonas(Trabajo trabajoAntiguo, Trabajo trabajo) {
		boolean returned = false;

		if (trabajoAntiguo.getPuntoXNegativo() != trabajo.getPuntoXNegativo()
				|| trabajoAntiguo.getPuntoYNegativo() != trabajo.getPuntoYNegativo()
				|| trabajoAntiguo.getPuntoYPositivo() != trabajo.getPuntoYPositivo()
				|| trabajoAntiguo.getPuntoXPositivo() != trabajo.getPuntoXPositivo())
			returned = true;
		return returned;
	}

	/**
	 * Trabajo details.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @return the string
	 */
	// listar detalles de un trabajo
	@RequestMapping(value = "/trabajoDetails", method = RequestMethod.GET)
	public String trabajoDetails(@RequestParam("id") int id, Model model) {
		Trabajo trabajo;
		try {
			trabajo = trabajoService.BuscarTrabajo(id);
			model.addAttribute("trabajo", trabajo);

			return null;
		} catch (Exception e) {
			return "redirect:/geosync";
		}

	}

	/*
	 * 
	 * 
	 * EJECUCION
	 * 
	 */

	/**
	 * Trabajo execute.
	 *
	 * @param id
	 *            the id
	 * @param model
	 *            the model
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the string
	 */
	@RequestMapping(value = "/trabajoExecute", method = RequestMethod.GET)
	public String trabajoExecute(@RequestParam("id") int id, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		// -8.42019 izquierda
		// 43.36448 abajo
		// -8.40335 derecha
		// 43.37144 arriba
		// http://api.openstreetmap.org/api/0.6/map?bbox=-8.42019,43.36448,-8.40335,43.37144
		// (south,west,north,east)->43.36448,-8.42019,43.37144,-8.40335

		try {
			Trabajo trabajo = trabajoService.BuscarTrabajo(id);
			List<Transformacion> transformacionList = trabajoService.BuscarTransformacionesDeTrabajo(id);

			//--
			trabajo.setFechaUltimaModificacion(null);
			 trabajoService.ActualizarTrabajo(trabajo);
			 //--
			if (trabajo.getFechaUltimaModificacion() == null) {
				// TODO si no hay reglas de transformacion, no realiza insercion
				// en BD
				if (transformacionList.isEmpty()) {

					xapi.bboxXAPI(trabajo);
				} else {

					xapi.mapeadoFiltrosXAPI(transformacionList, trabajo);

				}
			} else {
				if (transformacionList.isEmpty()) {

					xapi.bboxXAPIChangeset(trabajo);
				} else {

					xapi.mapeadoFiltrosXAPIChangeset(transformacionList, trabajo);

				}
			}
			 Calendar fechaActual = Calendar.getInstance();
			 trabajo.setFechaUltimaModificacion(fechaActual);
			 trabajoService.ActualizarTrabajo(trabajo);

			return "redirect:/trabajos/trabajoDetails?id=" + id;
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.toString());
			return "redirect:/trabajos/trabajoList";
		}

	}

}
