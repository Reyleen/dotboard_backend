package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import it.uniroma3.siw.dotboard_backend.repository.ThemeRepository;
import it.uniroma3.siw.dotboard_backend.services.BoardService;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.services.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;


@RestController
@RequestMapping("/api/boards")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "Board", description = "The Board API. Contains all the operations that can be performed on a board.")
public class BoardController implements Validator {

    @Autowired
    AuthenticatedUser authUser;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private BoardService boardService;

    // GET /boards
    @Operation(summary = "Get all boards of authenticated user")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Board> getAll() {
        return this.boardRepository.findByUserIdAndDeletedAtIsNull(authUser.getRequestUser().getId());
    }

    // GET /boards/{id}
    @Operation(summary = "Get board by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Board getById(@PathVariable("id") Long id, Principal principal) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        boardService.isOwner(principal, board);
        return board;
    }

    //Get board se publica
    @Operation(summary = "Get board by id if  board is public")
    @RequestMapping(value = "{id}/public", method = RequestMethod.GET)
    public Board getByIdPublic(@PathVariable("id") Long id) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        if(!board.isPublic())
            	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not public");
        return board;
    }

    @Operation(summary="Get all boardItems from a board if board is public")
    @RequestMapping(value="{id}/boardItems", method=RequestMethod.GET)
    public Iterable<BoardItem> getAllBoardItemsPublic(@PathVariable("id") Long id){
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        if(!board.isPublic())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not public");
        return board.getBoardItems();
    }

    //Get boardItems if board is public
    @Operation(summary = "Create a new board")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Board create(@RequestBody Board board) {
        ApplicationUser user = authUser.getRequestUser();
        return this.boardService.createBoard(board, user);
    }

    //Da scegliere cosa modificare, se descrizione/nome/ecc. o la parte delle boarditems
    @Operation(summary = "Update a board")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Board update(@PathVariable("id") Long id, @RequestBody Board updatedBoard, Principal principal) {
        return this.boardService.update(id, updatedBoard, principal);
    }

    @Operation(summary = "Delete a board")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id, Principal principal) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        this.boardService.isOwner(principal, board);
        this.boardService.deleteBoard(board);
    }

    @Operation(summary="Get all boardItems from a board")
    @RequestMapping(value="{id}/boardItems", method=RequestMethod.GET)
    public Iterable<BoardItem> getAllBoardItems(@PathVariable("id") Long id, Principal principal){
        	Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
            this.boardService.isOwner(principal, board);
            return board.getBoardItems();
    }

    @Operation(summary = "Create a new boardItem in the current Board")
    @RequestMapping(value = "{id}/boardItems", method = RequestMethod.POST)
    public Board create(@PathVariable("id") Long id, @RequestBody BoardItem boardItem, Principal principal){
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        this.boardService.isOwner(principal, board);
        return this.boardService.createItemToBoard(board, boardItem);
    }

    @Operation(summary = "Add a theme by name/color to a board")
    @RequestMapping(value = "{id}/addThemeToBoard/{colorOrName}", method = RequestMethod.PUT)
    public Board addThemeToBoard(@PathVariable("id") Long id, @PathVariable("colorOrName") String name, Principal principal) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        Theme theme = this.themeRepository.findByNameOrColorAndDeletedAtIsNull(name, name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
        this.boardService.isOwner(principal, board);
        return  this.boardService.addTheme(board, theme);
    }

    @Operation(summary = "Remove board's theme")
    @RequestMapping(value = "{id}/theme", method = RequestMethod.DELETE)
    public void removeThemeByBoardId(@PathVariable("id") Long id, Principal principal){
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id);
        this.boardService.isOwner(principal, board);
        boardService.removeThemeFromBoard(board);
    }

}
