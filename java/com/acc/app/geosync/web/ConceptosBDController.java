package com.acc.app.geosync.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.acc.app.geosync.model.VOs.AtributoBD;
import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.service.ConceptoService;
import com.acc.app.geosync.util.dbMap.JdbcPostgresSelect;

@Controller
@RequestMapping(value = "/conceptosBD")
public class ConceptosBDController {
	@Autowired
	private ConceptoService conceptoService;
	@Autowired
	private JdbcPostgresSelect jdbcPostgresSelect;

	// -------DE conceptoS-----------------------------------------------------
	// listar conceptos
	@RequestMapping(value = "/conceptoBDList", method = RequestMethod.GET)
	public void conceptoBDList(Model model) {
		List<ConceptoBD> list = conceptoService.BuscarConceptosBDTotales();
		model.addAttribute("conceptoBDList", list);
	}

	// crear un nuevo concepto
	@ModelAttribute("conceptoBD")
	@RequestMapping(value = "/conceptoBDFormAdd", method = RequestMethod.GET)
	public ConceptoBD form(Model model) {
		return new ConceptoBD();
	}

	// acción del form crear un nuevo concepto
	@RequestMapping(value = "/addConceptoBD", method = RequestMethod.POST)
	public String addconceptoBD(@ModelAttribute("conceptoBD") ConceptoBD concepto, Model model, BindingResult result,
			Principal principal) {

		try {
			conceptoService.CrearConceptoBD(concepto);
			return "redirect:/conceptosBD/conceptoBDList";
		} catch (Exception e) {
			return "redirect:/conceptosBD/conceptoBDList";
		}

	}

	// borrar un concepto
	@RequestMapping(value = "/conceptoBDDelete", method = RequestMethod.GET)
	public String conceptoBDDelete(Model model, @RequestParam("id") int id) {

		try {
			ConceptoBD tra = conceptoService.BuscarConceptoBD(id);
			conceptoService.EliminarConceptoBD(tra);
			return "redirect:/conceptosBD/conceptoBDList";
		} catch (Exception e) {
			return "redirect:/conceptosBD/conceptoBDList";
		}

	}

	// actualizar un concepto
	@ModelAttribute("conceptoBD")
	@RequestMapping(value = "/conceptoBDFormUpdate", method = RequestMethod.GET)
	public Object conceptoBDFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			return conceptoService.BuscarConceptoBD(id);
		} catch (Exception e) {
			return "redirect:/geosync";
		}
	}

	// acción actualizar un concepto
	@RequestMapping(value = "/updateConceptoBD", method = RequestMethod.POST)
	public String updateconcepto(@ModelAttribute("conceptoBD") ConceptoBD concepto, BindingResult binding,
			Model model) {

		try {
			conceptoService.ActualizarConceptoBD(concepto);
			return "redirect:/conceptosBD/conceptoBDDetails?id=" + concepto.getIdConceptoBD();
		} catch (Exception e) {
			return "redirect:/conceptosBD";
		}

	}

	// listar detalles de un concepto
	@RequestMapping(value = "/conceptoBDDetails", method = RequestMethod.GET)
	public String conceptoDetails(@RequestParam("id") int id, Model model) {
		ConceptoBD concepto;
		try {
			concepto = conceptoService.BuscarConceptoBD(id);
			model.addAttribute("conceptoBD", concepto);

			return null;
		} catch (Exception e) {
			return "redirect:/geosync";
		}

	}

	// mapa de puntos
	@RequestMapping(value = "/conceptoBDMap", method = RequestMethod.GET)
	public String conceptoMap(@RequestParam("id") int id, Model model) {
		ConceptoBD concepto;
		try {
			concepto = conceptoService.BuscarConceptoBD(id);
			List<AtributoBD> atributos = conceptoService.BuscarAtributosBD(id);
			String geo = jdbcPostgresSelect.conceptoBdToGeoJson(atributos, concepto.getUserBD(), concepto.getPassword(),
					concepto.getNombreBD(), concepto.getNombreTabla());
			model.addAttribute("geojsonBD", geo);
			model.addAttribute("conceptoBD", concepto);
			return null;
		} catch (Exception e) {
			return "redirect:/geosync";
		}

	}

	// ------------ATRIBUTOS OSM----------------------
	//
	// listar atributos
	@RequestMapping(value = "/atributosBDList", method = RequestMethod.GET)
	public void atributoBDList(Model model, @RequestParam("id") int id) {
		List<AtributoBD> list = conceptoService.BuscarAtributosBD(id);
		ConceptoBD concepto = conceptoService.BuscarConceptoBD(id);
		model.addAttribute("atributoBDList", list);
		model.addAttribute("conceptoBD", concepto);
	}

	// crear un nuevo atributoBD
	@ModelAttribute("atributoBD")
	@RequestMapping(value = "/atributoBDFormAdd", method = RequestMethod.GET)
	public AtributoBD formAtributoBD(Model model, @RequestParam("id") int id) {
		AtributoBD atributo = new AtributoBD();
		atributo.setIdConceptoBD(conceptoService.BuscarConceptoBD(id));
		return atributo;
	}

	// acción del form crear un nuevo atributoBD
	@RequestMapping(value = "/addAtributoBD", method = RequestMethod.POST)
	public String addconceptoBD(@ModelAttribute("atributoBD") AtributoBD atributo, Model model, BindingResult result,
			Principal principal) {

		try {
			conceptoService.CrearAtributoBD(atributo);
			return "redirect:/conceptosBD/atributosBDList?id=" + atributo.getIdConceptoBD().getIdConceptoBD();
		} catch (Exception e) {
			return "redirect:/conceptosBD/conceptoBDList";
		}

	}

	// borrar un atributo
	@RequestMapping(value = "/atributoBDDelete", method = RequestMethod.GET)
	public String atributoBDDelete(Model model, @RequestParam("id") int id) {

		try {
			AtributoBD tra = conceptoService.BuscarAtributoBD(id);
			conceptoService.EliminarAtributoBD(tra);
			return "redirect:/conceptosBD/atributosBDList?id=" + tra.getIdConceptoBD().getIdConceptoBD();
		} catch (Exception e) {
			return "redirect:/conceptosBD/conceptoBDList";
		}

	}

	// actualizar un atributo
	@ModelAttribute("atributoBD")
	@RequestMapping(value = "/atributoBDFormUpdate", method = RequestMethod.GET)
	public Object atributoBDFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			return conceptoService.BuscarAtributoBD(id);
		} catch (Exception e) {
			return "redirect:/geosync";
		}
	}

	// acción actualizar un atributo
	@RequestMapping(value = "/updateAtributoBD", method = RequestMethod.POST)
	public String updateAtributoBD(@ModelAttribute("atributoBD") AtributoBD concepto, BindingResult binding,
			Model model) {

		try {
			conceptoService.ActualizarAtributoBD(concepto);
			return "redirect:/conceptosBD/atributosBDList?id=" + concepto.getIdConceptoBD().getIdConceptoBD();
		} catch (Exception e) {
			return "redirect:/conceptosBD";
		}

	}

}
