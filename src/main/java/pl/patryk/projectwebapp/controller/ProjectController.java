package pl.patryk.projectwebapp.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import pl.patryk.projectwebapp.model.Projekt;
import pl.patryk.projectwebapp.service.ProjektService;

import javax.validation.Valid;

@Controller
public class ProjectController {
    private ProjektService projektService;

    //@Autowired – przy jednym konstruktorze wstrzykiwanie jest zadaniem domyślnym, adnotacja nie jest potrzebna
    public ProjectController(ProjektService projektService) {
        this.projektService = projektService;
    }

    // metodę wywołamy wpisując w przeglądarce np. http://localhost:8081/projektList lub
    @GetMapping("/projektList")
    public String projektList(Model model, Pageable pageable) {
        // za pomocą zmiennej model i metody addAttribute przekazywane są do widoku obiekty
        // zawierające dane projektów
        model.addAttribute("projekty", projektService.getProjekty(pageable).getContent());
        return "projektList"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektList.html

    @GetMapping("/projektEdit")
    public String projektEdit(@RequestParam(required = false) Integer projektId, Model model) {
        if (projektId != null) {
            model.addAttribute("projekt", projektService.getProjekt(projektId).get());
        } else {
            Projekt projekt = new Projekt();
            model.addAttribute("projekt", projekt);
        }
        return "projektEdit"; // metoda zwraca nazwę logiczną widoku, Spring dopasuje nazwę do odpowiedniego
    } // szablonu tj. project-web-app\src\main\resources\templates\projektEdit.html

    @PostMapping(path = "/projektEdit")
    public String projektEditSave(@ModelAttribute @Valid Projekt projekt, BindingResult bindingResult) {
        // parametr BindingResult powinien wystąpić zaraz za parametrem opatrzonym adnotacją @Valid
        if (bindingResult.hasErrors()) {
            return "projektEdit"; // wracamy do okna edycji, jeżeli przesłane dane formularza zawierają błędy
        }
        try {
            projekt = projektService.setProjekt(projekt);
        } catch (HttpStatusCodeException e) {
            bindingResult.rejectValue(null, String.valueOf(e.getStatusCode().value()),
                    e.getStatusCode().getReasonPhrase());
            return "projektEdit";
        }
        return "redirect:/projektList"; // przekierowanie do listy projektów, po utworzeniu lub modyfikacji projektu
    }

    @PostMapping(params = "cancel", path = "/projektEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditCancel() { // żądanie będzie zawierało parametr 'cancel'
        return "redirect:/projektList";
    }

    @PostMapping(params = "delete", path = "/projektEdit") // metoda zostanie wywołana, jeżeli przesłane
    public String projektEditDelete(@ModelAttribute Projekt projekt) {// żądanie będzie zawierało parametr 'delete'
        projektService.deleteProjekt(projekt.getProjektId());
        return "redirect:/projektList";
    }
}