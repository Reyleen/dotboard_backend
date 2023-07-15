package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import it.uniroma3.siw.dotboard_backend.repository.ThemeRepository;
import it.uniroma3.siw.dotboard_backend.services.BoardService;
import it.uniroma3.siw.dotboard_backend.services.ThemeService;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.services.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RestController
@RequestMapping("/api/themes")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "Theme", description = "The Theme API. Contains all the operations that can be performed on a theme.")
public class ThemeController  implements Validator {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    AuthenticatedUser authUser;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ThemeService themeService;

    @Operation(summary = "Get all themes")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Theme> getAll() {
        return this.themeRepository.findAllByDeletedAtIsNull();
    }

    @Operation(summary = "Get board's theme")
    @RequestMapping(value = "{id}/theme", method = RequestMethod.GET)
    public Theme getThemeByBoardId(@PathVariable("id") Long id, Principal principal){
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        this.boardService.isOwner(principal, board);
        return board.getTheme();
    }

    @Operation(summary = "Get theme by name/color")
    @RequestMapping(value = "{name}", method = RequestMethod.GET)
    public Theme getByName(@PathVariable("name") String name) {
        return this.themeRepository.findByNameOrColorAndDeletedAtIsNull(name, name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a theme")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Theme create(Theme theme) {
        return this.themeService.createTheme(theme);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a theme by id")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        Theme theme = this.themeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
        this.themeService.deleteTheme(theme);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all boards with a theme")
    @RequestMapping(value="findBoards/{name}" , method = RequestMethod.GET)
    public Iterable<Board> getBoardsByTheme(@PathVariable("name") String name){
    	Theme theme = this.themeRepository.findByNameOrColorAndDeletedAtIsNull(name, name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
    	return theme.getBoards();
    }

}

