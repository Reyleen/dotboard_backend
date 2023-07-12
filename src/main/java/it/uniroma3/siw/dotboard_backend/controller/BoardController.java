package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.dto.BoardDTO;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import it.uniroma3.siw.dotboard_backend.repository.ThemeRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.services.security.AuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/api/boards")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "Board", description = "The Board API. Contains all the operations that can be performed on a board.")
public class BoardController implements Validator {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    AuthenticatedUser authUser;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardItemRepository boardItemRepository;

    @Autowired
    private ThemeRepository themeRepository;

    // GET /boards
    @Operation(summary = "Get all boards of authenticated user")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Board> getAll() {
        return this.boardRepository.findByUserIdAndDeletedAtIsNull(authUser.getRequestUser().getId());
    }

    // GET /boards/{id}
    @Operation(summary = "Get board by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Board getById(@PathVariable("id") Long id) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getUser().getId().equals(authUser.getRequestUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
        }
        return board;
    }

    @Operation(summary = "Create a new board")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Board create(@RequestBody BoardDTO board) {
        Board newBoard = modelMapper.map(board, Board.class);
        ApplicationUser user = authUser.getRequestUser();
        newBoard.setUser(user);
        boardRepository.save(newBoard);

        //save the bord in the user list of boards
        user.getBoards().add(newBoard);
        applicationUserRepository.save(user);

        return newBoard;
    }

    //Da scegliere cosa modificare, se descrizione/nome/ecc. o la parte delle boarditems
    @Operation(summary = "Update a board")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Board update(@PathVariable("id") Long id, @RequestBody Board board) {
        board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getUser().getId().equals(authUser.getRequestUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
        }
        return this.boardRepository.save(board);
    }

    @Operation(summary = "Delete a board")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        if (!board.getUser().getId().equals(authUser.getRequestUser().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
        }
        this.boardRepository.delete(board);
    }

    @Operation(summary="Get all boardItems from a board")
    @RequestMapping(value="{id}/boardItems", method=RequestMethod.GET)
    public Iterable<BoardItem> getAllBoardItems(@PathVariable("id") Long id){
        	Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
            if (!board.getUser().getId().equals(authUser.getRequestUser().getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
            }
            return board.getBoardItems();
    }

    @Operation(summary = "Update a boardItem")
    @RequestMapping(value = "{boardId}/boardItem/{itemId}", method = RequestMethod.PUT)
    public BoardItem update(@PathVariable("boardId") Long boardId,@PathVariable("itemId") Long itemId,
                            String caption, int width, int height) {
        BoardItem boardItem = this.boardItemRepository.findById(itemId).orElse(null);

        if (boardItem == null) {
            return null;
        }
        boardItem.setCaption(caption);
        boardItem.setHeight(height);
        boardItem.setWidth(width);
        boardItem.setCreatedAt(boardItem.getCreatedAt());
        boardItem.setUpdatedAt(new Date());
        return this.boardItemRepository.save(boardItem);
    }

    @Operation(summary = "Create a new boardItem in the current Board")
    @RequestMapping(value = "{id}/boardItem", method = RequestMethod.POST)
    public Board create(@PathVariable("id") Long id, @RequestBody BoardItem boardItem) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        this.boardItemRepository.save(boardItem);
        board.getBoardItems().add(boardItem);
        boardItem.setBoard(board);
        return this.boardRepository.save(board);
    }

    @Operation(summary = "Add a theme by color to a board")
    @RequestMapping(value = "{id}/addThemeByColor/{color}", method = RequestMethod.PUT)
    public Board addTheme1ToBoard(@PathVariable("id") Long id, @PathVariable("color") String color) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        Theme theme = this.themeRepository.findByColorAndDeletedAtIsNull(color)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
        if(!board.getUser().getId().equals(authUser.getRequestUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
        }
        board.setTheme(theme);
        theme.getBoards().add(board);
        return this.boardRepository.save(board);
    }

    @Operation(summary = "Add a theme by name to a board")
    @RequestMapping(value = "{id}/AddThemeByName/{name}", method = RequestMethod.PUT)
    public Board addThemeToBoard(@PathVariable("id") Long id, @PathVariable("name") String name) {
        Board board = this.boardRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found"));
        Theme theme = this.themeRepository.findByNameAndDeletedAtIsNull(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Theme not found"));
        if(!board.getUser().getId().equals(authUser.getRequestUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Board not owned by user");
        }
        board.setTheme(theme);
        theme.getBoards().add(board);
        return this.boardRepository.save(board);
    }
}
