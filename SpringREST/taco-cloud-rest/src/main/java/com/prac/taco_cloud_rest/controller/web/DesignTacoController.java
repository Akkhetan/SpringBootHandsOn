package com.prac.taco_cloud_rest.controller.web;


import com.prac.taco_cloud_rest.data.IngredientRepository;
import com.prac.taco_cloud_rest.data.TacoRepository;
import com.prac.taco_cloud_rest.entity.Ingredient;
import com.prac.taco_cloud_rest.entity.Taco;
import com.prac.taco_cloud_rest.entity.TacoOrder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j /*
is a Lombok-provided annotation that, at compilation time, will automatically generate an SLF4J Logger static property in the class.
This modest annotation has the same effect as if you were to explicitly add the following lines within the class:
private static final Logger log = LoggerFactory.getLogger(DesignTacoController.class);
*/
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder") /*
This indicates that the TacoOrder object that is put into the model a little later in the class should be maintained in session.
This is important because the creation of a taco is also the first step in creating an order, and the order we create will need to be carried in the
session so that it can span multiple requests.
*/
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
        this.ingredientRepo = ingredientRepo;
        this.tacoRepo = tacoRepo;
    }

@ModelAttribute
/*
This method will also be invoked when a request is handled and will construct a list of Ingredient objects to be put into the model.
* */
public void addIngredientsToModel(Model model) {
    Iterable<Ingredient> ingredients = ingredientRepo.findAll();

    /*
    Once the list of ingredients is ready, the next filters the list by ingredient type using a helper method named filterByType().
    A list of ingredient types is then added as an attribute to the Model object that will be passed into showDesignForm().
    Model is an object that ferries data between a controller and whatever view is charged with rendering that data.
    Ultimately, data that’s placed in Model attributes is copied into the servlet request attributes, where the view can find them and use them to
    render a page in the user’s browser.
     */
	Ingredient.Type[] types = Ingredient.Type.values();
	for (Ingredient.Type type : types) {
	  model.addAttribute(type.toString().toLowerCase(),
	      filterByType(ingredients, type));
	}
  }

  /*
  The TacoOrder object, referred to earlier in the @SessionAttributes annotation, holds state for the order being built as the user creates tacos
  across multiple requests.
   */
  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  /*
  The Taco object is placed into the model so that the view rendered in response to the GET request for /design will have a non-null object to display.
   */
  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  /*
    Handle HTTP GET requests where the request path is /design.
    Build a list of ingredients
    Hand off the request and the ingredient data to a view template to be rendered as HTML and sent to the requesting web browser
   */
  @GetMapping
  public String showDesignForm() {
    return "design";
  }

/*
  When the form(design.html) is submitted, the fields in the form are bound to properties of a Taco object that’s passed as a parameter into processTaco().
  The @Valid annotation tells Spring MVC to perform validation on the submitted Taco object after it’s bound to the submitted form data and
  before the processTaco() method is called. If there are any validation errors, the details of those errors will be captured in an Errors object
  that’s passed into processTaco(). he method will be allowed to process the submitted data if there are no validation errors.
  If there are validation errors, the request will be forwarded to the form view to give the user a chance to correct their mistakes.
  But how will the user know what mistakes require correction? Unless you call out the errors on the form, the user will be left guessing about
  how to successfully submit the form.
 */
  @PostMapping
  public String processTaco(
		  @Valid Taco taco, Errors errors,
		  @ModelAttribute TacoOrder tacoOrder) {

    if (errors.hasErrors()) {
      return "design";
    }

    tacoRepo.save(taco);
    tacoOrder.addTaco(taco);
    log.info("Processing taco: {}", taco);

    return "redirect:/orders/current";
  }

    private Iterable<Ingredient> filterByType(
            Iterable<Ingredient> ingredients, Ingredient.Type type) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

}