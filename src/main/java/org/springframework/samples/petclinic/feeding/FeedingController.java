package org.springframework.samples.petclinic.feeding;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feeding")
public class FeedingController {
    
private FeedingService feedingService;
private PetService petService;
	
	@Autowired
	public FeedingController(FeedingService feedingService, PetService petService) {
		this.feedingService=feedingService;
		this.petService=petService;
	}
	
	@GetMapping(path="/create")
	public String getFeedingForm(ModelMap modelMap) 
	{
		String view="feedings/createOrUpdateFeedingForm";
		Feeding feeding= new Feeding();
		modelMap.addAttribute("feeding",feeding);
		return view;
	}
	@PostMapping(path="/create")
	public String postFeedingForm(@Valid Feeding feeding, BindingResult result,ModelMap modelMap) throws DataAccessException {
		String view="redirect:/welcome";
		if(result.hasErrors()) {
			modelMap.addAttribute("feeding",feeding);
			view= "feedings/createOrUpdateFeedingForm";
			
		}else {
			try {
			feedingService.save(feeding);}
			catch (UnfeasibleFeedingException e) {
				result.rejectValue("pet", "excepción", "La mascota seleccionada no se le puede asignar el plan de alimentación especificado.");
				view= "feedings/createOrUpdateFeedingForm";
			}
		}
		return view;
	}
	
	
	
	
	
	@ModelAttribute("feedingTypes")
	public List<FeedingType> populateFeedingTypes() {
		return feedingService.getAllFeedingTypes();
	}
	
	@ModelAttribute("pets")
	public List<Pet> populatePet() {
		return petService.getAllPets();
	}
	

	
}
