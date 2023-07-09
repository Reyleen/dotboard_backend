package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.repository.ThemeRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/themes")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "Theme", description = "The Theme API. Contains all the operations that can be performed on a theme.")

public class ThemeController  implements Validator {

    @Autowired
    private ThemeRepository themeRepository;


}
