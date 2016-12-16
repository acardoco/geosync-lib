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

import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Filtro;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.FiltroClaveValor;
import com.acc.app.geosync.model.VOs.FiltroExists;
import com.acc.app.geosync.model.VOs.FiltroNumerico;
import com.acc.app.geosync.service.ConceptoService;
import com.acc.app.geosync.util.ControllerUtils;

@Controller
@RequestMapping(value = "/conceptosOSM")
public class ConceptosOSMController {
	@Autowired
	private ConceptoService conceptoService;
	
	private String actualizacionFiltroOperacionActual = "";

	// -------DE conceptoS-----------------------------------------------------
	// listar conceptos
	@RequestMapping(value = "/conceptoOSMList", method = RequestMethod.GET)
	public void conceptoOSMList(Model model) {
		List<ConceptoOSM> list = conceptoService.BuscarConceptosOSMTotales();
		model.addAttribute("conceptoOSMList", list);
	}

	// crear un nuevo concepto
	@ModelAttribute("conceptoOSM")
	@RequestMapping(value = "/conceptoOSMFormAdd", method = RequestMethod.GET)
	public ConceptoOSM form(Model model) {
		return new ConceptoOSM();
	}

	// acción del form crear un nuevo concepto
	@RequestMapping(value = "/addConceptoOSM", method = RequestMethod.POST)
	public String addconceptoOSM(@ModelAttribute("conceptoOSM") ConceptoOSM concepto, Model model, BindingResult result,
			Principal principal) {

		try {
			conceptoService.CrearConceptoOSM(concepto);
			return "redirect:/conceptosOSM/conceptoOSMList";
		} catch (Exception e) {
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// borrar un concepto
	@RequestMapping(value = "/conceptoOSMDelete", method = RequestMethod.GET)
	public String conceptoOSMDelete(Model model, @RequestParam("id") int id) {

		try {
			ConceptoOSM tra = conceptoService.BuscarConceptoOSM(id);
			// ELIMINAMOS los filtros que tenga
			List<Filtro> filtros = conceptoService.BuscarFiltrosConceptoOSM(id);
			if (!filtros.isEmpty()) {
				for (Filtro f : filtros) {
					conceptoService.EliminarFiltro(f);
				}
			}
			conceptoService.EliminarConceptoOSM(tra);
			return "redirect:/conceptosOSM/conceptoOSMList";
		} catch (Exception e) {
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// actualizar un concepto
	@ModelAttribute("conceptoOSM")
	@RequestMapping(value = "/conceptoOSMFormUpdate", method = RequestMethod.GET)
	public Object conceptoOSMFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			return conceptoService.BuscarConceptoOSM(id);
		} catch (Exception e) {
			return "redirect:/geosync";
		}
	}

	// acción actualizar un concepto
	@RequestMapping(value = "/updateConceptoOSM", method = RequestMethod.POST)
	public String updateconcepto(@ModelAttribute("conceptoOSM") ConceptoOSM concepto, BindingResult binding,
			Model model) {

		try {
			conceptoService.ActualizarConceptoOSM(concepto);
			return "redirect:/conceptosOSM/conceptoOSMDetails?id=" + concepto.getIdConceptoOSM();
		} catch (Exception e) {
			return "redirect:/conceptosOSM";
		}

	}

	// listar detalles de un concepto
	@RequestMapping(value = "/conceptoOSMDetails", method = RequestMethod.GET)
	public String conceptoDetails(@RequestParam("id") int id, Model model) {
		ConceptoOSM concepto;
		try {
			concepto = conceptoService.BuscarConceptoOSM(id);
			List<Filtro> filtros = conceptoService.BuscarFiltrosConceptoOSM(id);
			model.addAttribute("conceptoOSM", concepto);
			model.addAttribute("filtroList", filtros);
			return null;
		} catch (Exception e) {
			return "redirect:/geosync";
		}

	}

	// crear un nuevo filtro
	@ModelAttribute("filtro")
	@RequestMapping(value = "/filtroFormAdd", method = RequestMethod.GET)
	public FiltroClaveValor formFiltro(@RequestParam("id") int idConceptoOSM, Model model) {
		FiltroClaveValor filtro = new FiltroClaveValor();
		filtro.setIdConceptoOSM(conceptoService.BuscarConceptoOSM(idConceptoOSM));
		return filtro;
	}

	// acción del form crear un nuevo filtro
	@RequestMapping(value = "/addFiltro", method = RequestMethod.POST)
	public String addFiltro(@ModelAttribute("filtro") FiltroClaveValor filtro, Model model, BindingResult result,
			Principal principal) {

		try {
			if (filtro.getOperacion().equals("existe")) {
				FiltroExists fe = new FiltroExists();
				fe.setIdFiltro(filtro.getIdFiltro());
				fe.setIdConceptoOSM(filtro.getIdConceptoOSM());
				fe.setClave(filtro.getClave());
				fe.setOperacion(filtro.getOperacion());
				conceptoService.CrearFiltro(fe);
			} else if (ControllerUtils.esFiltroNumerico(filtro.getOperacion())) {
				FiltroNumerico fn = new FiltroNumerico();
				fn.setIdFiltro(filtro.getIdFiltro());
				fn.setIdConceptoOSM(filtro.getIdConceptoOSM());
				fn.setClave(filtro.getClave());
				fn.setOperacion(filtro.getOperacion());
				fn.setValor(filtro.getValor());
				conceptoService.CrearFiltro(fn);
			} else {
				conceptoService.CrearFiltro(filtro);
			}
			return "redirect:/conceptosOSM/conceptoOSMDetails?id=" + filtro.getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// borrar un filtro
	@RequestMapping(value = "/filtroDelete", method = RequestMethod.GET)
	public String filtroDelete(Model model, @RequestParam("id") int id) {

		try {
			Filtro tra = conceptoService.BuscarFiltro(id);
			conceptoService.EliminarFiltro(tra);
			return "redirect:/conceptosOSM/conceptoOSMDetails?id=" + tra.getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// actualizar un filtro
	@ModelAttribute("filtro")
	@RequestMapping(value = "/filtroFormUpdate", method = RequestMethod.GET)
	public Object filtroFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			Filtro filtro = conceptoService.BuscarFiltro(id);
			if (filtro.getOperacion().equals("existe")) {
				FiltroExists fe = (FiltroExists) conceptoService.BuscarFiltro(id);
				actualizacionFiltroOperacionActual = "existe";
				return fe;
			} else if (ControllerUtils.esFiltroNumerico(filtro.getOperacion())) {
				FiltroNumerico fn = (FiltroNumerico) conceptoService.BuscarFiltro(id);
				actualizacionFiltroOperacionActual = "numerico";
				return fn;
			} else {
				FiltroClaveValor fcv = (FiltroClaveValor) conceptoService.BuscarFiltro(id);
				actualizacionFiltroOperacionActual = "normal";
				return fcv;
			}
		} catch (Exception e) {
			System.out.println("Error en filtroFormUpdate: " + e.getLocalizedMessage());
			return "redirect:/geosync";
		}
	}

	// ----------UPDATES-------------
	// acción actualizar un filtro EXISTS
	@RequestMapping(value = "/updateFiltroExists", method = RequestMethod.POST)
	public String updateconcepto(@ModelAttribute("filtro") FiltroExists filtro, BindingResult binding, Model model) {

		try {

			conceptoService.ActualizarFiltro(filtro);

			return "redirect:/conceptosOSM/conceptoOSMDetails?id="
					+ ((Filtro) filtro).getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			System.out.println("Error en updateFiltro: " + e.getLocalizedMessage());
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// acción actualizar un filtro CLAVE VALOR
	@RequestMapping(value = "/updateFiltroClaveValor", method = RequestMethod.POST)
	public String updateconceptoCV(@ModelAttribute("filtro") FiltroClaveValor filtro, BindingResult binding,
			Model model) {

		try {

			conceptoService.ActualizarFiltro(filtro);

			return "redirect:/conceptosOSM/conceptoOSMDetails?id="
					+ ((Filtro) filtro).getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			System.out.println("Error en updateFiltro: " + e.getLocalizedMessage());
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// acción actualizar un filtro NUMERICO
	@RequestMapping(value = "/updateFiltroNumerico", method = RequestMethod.POST)
	public String updateconceptoN(@ModelAttribute("filtro") FiltroNumerico filtro, BindingResult binding, Model model) {

		try {

			conceptoService.ActualizarFiltro(filtro);

			return "redirect:/conceptosOSM/conceptoOSMDetails?id="
					+ ((Filtro) filtro).getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			System.out.println("Error en updateFiltro: " + e.getLocalizedMessage());
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// -----------ATRIBUTOS
	// OSM--------------------------------------------------------
	//
	// listar atributos
	@RequestMapping(value = "/atributosOSMList", method = RequestMethod.GET)
	public void atributoOSMList(Model model, @RequestParam("id") int id) {
		List<AtributoOSM> list = conceptoService.BuscarAtributosOSM(id);
		ConceptoOSM concepto = conceptoService.BuscarConceptoOSM(id);

		// añadimos un atributo de coordenadas
		if (list.isEmpty()) {
			AtributoOSM atributoGEOM = new AtributoOSM();
			atributoGEOM.setClave("geom");
			atributoGEOM.setIdConceptoOSM(concepto);
			conceptoService.CrearAtributoOSM(atributoGEOM);
			list = conceptoService.BuscarAtributosOSM(id);
		}

		model.addAttribute("atributoOSMList", list);
		model.addAttribute("conceptoOSM", concepto);
	}

	// crear un nuevo atributoOSM
	@ModelAttribute("atributoOSM")
	@RequestMapping(value = "/atributoOSMFormAdd", method = RequestMethod.GET)
	public AtributoOSM formAtributoOSM(Model model, @RequestParam("id") int id) {
		AtributoOSM atributo = new AtributoOSM();
		atributo.setIdConceptoOSM(conceptoService.BuscarConceptoOSM(id));
		return atributo;
	}

	// acción del form crear un nuevo atributoOSM
	@RequestMapping(value = "/addAtributoOSM", method = RequestMethod.POST)
	public String addconceptoOSM(@ModelAttribute("atributoOSM") AtributoOSM atributo, Model model, BindingResult result,
			Principal principal) {

		try {
			conceptoService.CrearAtributoOSM(atributo);
			return "redirect:/conceptosOSM/atributosOSMList?id=" + atributo.getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// borrar un atributo
	@RequestMapping(value = "/atributoOSMDelete", method = RequestMethod.GET)
	public String atributoOSMDelete(Model model, @RequestParam("id") int id) {

		try {
			AtributoOSM tra = conceptoService.BuscarAtributoOSM(id);
			conceptoService.EliminarAtributoOSM(tra);
			return "redirect:/conceptosOSM/atributosOSMList?id=" + tra.getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			return "redirect:/conceptosOSM/conceptoOSMList";
		}

	}

	// actualizar un atributo
	@ModelAttribute("atributoOSM")
	@RequestMapping(value = "/atributoOSMFormUpdate", method = RequestMethod.GET)
	public Object atributoOSMFormUpdate(@RequestParam("id") int id, Model model) {
		try {
			return conceptoService.BuscarAtributoOSM(id);
		} catch (Exception e) {
			return "redirect:/geosync";
		}
	}

	// acción actualizar un atributo
	@RequestMapping(value = "/updateAtributoOSM", method = RequestMethod.POST)
	public String updateAtributoOSM(@ModelAttribute("atributoOSM") AtributoOSM concepto, BindingResult binding,
			Model model) {

		try {
			conceptoService.ActualizarAtributoOSM(concepto);
			return "redirect:/conceptosOSM/atributosOSMList?id=" + concepto.getIdConceptoOSM().getIdConceptoOSM();
		} catch (Exception e) {
			return "redirect:/conceptosOSM";
		}

	}

}
