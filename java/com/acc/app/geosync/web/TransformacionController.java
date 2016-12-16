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
import com.acc.app.geosync.model.VOs.AtributoOSM;
import com.acc.app.geosync.model.VOs.AtributoTransformacion;
import com.acc.app.geosync.model.VOs.ConceptoBD;
import com.acc.app.geosync.model.VOs.ConceptoOSM;
import com.acc.app.geosync.model.VOs.Trabajo;
import com.acc.app.geosync.model.VOs.Transformacion;
import com.acc.app.geosync.service.ConceptoService;
import com.acc.app.geosync.service.TrabajoService;
import com.acc.app.geosync.service.TransformacionService;

@Controller
@RequestMapping(value = "/transformaciones")
public class TransformacionController {
	
	@Autowired
	private TransformacionService transformacionService;
	
	@Autowired
	private TrabajoService trabajoService;
	
	@Autowired
	private ConceptoService conceptoService;

	// listar 
		@RequestMapping(value = "/transformacionList", method = RequestMethod.GET)
		public void transformacionList(@RequestParam("id") int idTrabajo, Model model) {
			List<Transformacion> list =  trabajoService.BuscarTransformacionesDeTrabajo(idTrabajo);
			Trabajo trabajo = trabajoService.BuscarTrabajo(idTrabajo);
			model.addAttribute("transformacionList", list);
			model.addAttribute("trabajo",trabajo);
		}
	
	// crear 
		@ModelAttribute("transformacion")
		@RequestMapping(value = "/transformacionFormAdd", method = RequestMethod.GET)
		public Transformacion form(@RequestParam("id") int id, Model model) {
			Transformacion t = new Transformacion();
			Trabajo idTrabajoo = trabajoService.BuscarTrabajo(id);
			t.setIdTrabajo(idTrabajoo);
			List<ConceptoOSM> conceptosOSM = conceptoService.BuscarConceptosOSMTotales();
			model.addAttribute("conceptosOSM",conceptosOSM);
			List<ConceptoBD> conceptosBD = conceptoService.BuscarConceptosBDTotales();
			model.addAttribute("conceptosBD",conceptosBD);
			return t;
		}

		// acción del form crear 
		@RequestMapping(value = "/addTransformacion", method = RequestMethod.POST)
		public String addTransformacion(@ModelAttribute("transformacion")  Transformacion transformacion, Model model, BindingResult result
			){
			try {				
				transformacionService.CrearTransformacion(transformacion);			
				return "redirect:/transformaciones/transformacionList?id="+transformacion.getIdTrabajo().getIdTrabajo();
			} catch (Exception e) {
				return "redirect:/exceptions/inputValidation";
			}

		}

		// borrar un transformacion
		@RequestMapping(value = "/transformacionDelete", method = RequestMethod.GET)
		public String transformacionDelete(Model model, @RequestParam("id") int id) {
			Transformacion tra;
			try {
				tra = transformacionService.BuscarTransformacion(id);
				transformacionService.EliminarTransformacion(tra);
				return "redirect:/transformaciones/transformacionList?id="+tra.getIdTrabajo().getIdTrabajo();
			} catch (Exception e) {
				return "redirect:/trabajos/trabajoList";
			}

		}
		
		//actualizar una transformacion
			@ModelAttribute("transformacion")
			@RequestMapping(value = "/transformacionFormUpdate", method = RequestMethod.GET)
			public Object transformacionFormUpdate(@RequestParam("id") int id,Model model) {
				try {
					List<ConceptoOSM> conceptosOSM = conceptoService.BuscarConceptosOSMTotales();
					model.addAttribute("conceptosOSM",conceptosOSM);
					List<ConceptoBD> conceptosBD = conceptoService.BuscarConceptosBDTotales();
					model.addAttribute("conceptosBD",conceptosBD);
					return transformacionService.BuscarTransformacion(id);
					
				} catch (Exception e) {
					return "redirect:/geosync";
				}
			}

			// acción actualizar un transformacion
			@RequestMapping(value = "/updateTransformacion", method = RequestMethod.POST)
			public String updateTransformacion(@ModelAttribute("transformacion") Transformacion transformacion,BindingResult binding, Model model) {

				try {
					transformacionService.ActualizarTransformacion(transformacion);
					return "redirect:/transformaciones/transformacionList?id="+transformacion.getIdTrabajo().getIdTrabajo();
				} catch (Exception e) {
					return "redirect:/exceptions/inputValidation";
				}

			}
			//---------ATRIBUTOS MAPEADOS-------------
			// listar atributos
			@RequestMapping(value = "/atributoTransformacionList", method = RequestMethod.GET)
			public void atributoTransformacionList(@RequestParam("id") int idTransformacion, Model model) {
				List<AtributoTransformacion> list =  transformacionService.AtributoTransformacionesTotalesByTransformacionId(idTransformacion);
				Transformacion transformacion = transformacionService.BuscarTransformacion(idTransformacion);
				model.addAttribute("atributoTransformacionList", list);
				model.addAttribute("transformacion",transformacion);
			}
			
			// crear atributos
			@ModelAttribute("atributoTransformacion")
			@RequestMapping(value = "/atributoTransformacionFormAdd", method = RequestMethod.GET)
			public AtributoTransformacion formAtributoMapeado(@RequestParam("id") int id, Model model) {
				AtributoTransformacion m = new AtributoTransformacion();
				Transformacion idTransf = transformacionService.BuscarTransformacion(id);
				m.setIdTransformacion(idTransf);
				//OSM
				ConceptoOSM conceptoOSM = conceptoService.BuscarConceptoOSM(idTransf.getIdConceptoOSM().getIdConceptoOSM());
				List<AtributoOSM> atributosOSM = conceptoService.BuscarAtributosOSM(conceptoOSM.getIdConceptoOSM());	
				//BD
				ConceptoBD conceptoBD = conceptoService.BuscarConceptoBD(idTransf.getIdConceptoBD().getIdConceptoBD());
				List<AtributoBD> atributosBD = conceptoService.BuscarAtributosBD(conceptoBD.getIdConceptoBD());
				//añadidos al modelo
				model.addAttribute("atributosBD",atributosBD);
				model.addAttribute("atributosOSM",atributosOSM);
				return m;
			}

			// acción del form crear atributos
			@RequestMapping(value = "/addAtributoTransformacion", method = RequestMethod.POST)
			public String addAtributoTransformacion(@ModelAttribute("atributoTransformacion")  AtributoTransformacion atributo, Model model, BindingResult result
				){
				try {				
					transformacionService.CrearAtributoTransformacion(atributo);	
					return "redirect:/transformaciones/atributoTransformacionList?id="+atributo.getIdTransformacion().getIdTransformacion();
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
					return "redirect:/exceptions/inputValidation";
				}

			}
			
			// borrar un atributo transformacion
			@RequestMapping(value = "/atributoTransformacionDelete", method = RequestMethod.GET)
			public String atributoTransformacionDelete(Model model, @RequestParam("id") int id) {
				AtributoTransformacion tra;
				try {
					tra = transformacionService.BuscarAtributoTransformacion(id);
					transformacionService.EliminarAtributoTransformacion(tra);
					return "redirect:/transformaciones/atributoTransformacionList?id="+tra.getIdTransformacion().getIdTransformacion();
				} catch (Exception e) {
					return "redirect:/trabajos/trabajoList";
				}

			}
			

	
}
