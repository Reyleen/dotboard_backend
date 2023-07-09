package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.dto.BoardDTO;
import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.services.security.AuthenticatedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
}
