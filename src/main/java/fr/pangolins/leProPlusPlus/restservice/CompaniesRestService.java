package fr.pangolins.leProPlusPlus.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Controller
@RequestMapping("/companies")
public class CompaniesRestService {
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<String> getAll() {
        return new ResponseEntity<>("Bonjour", HttpStatus.OK);
    }
}
