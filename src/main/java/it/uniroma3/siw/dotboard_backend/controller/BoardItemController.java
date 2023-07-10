package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.*;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/api/boardItems")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "BoardItem", description = "The BoardItem API. Contains all the operations that can be performed on a boardItem.")
public class BoardItemController  implements Validator {

    @Autowired
    private BoardItemRepository boardItemRepository;

    @Autowired
    private BoardRepository boardRepository;

    // GET all boardItems
    @Operation(summary = "Get all boardItems of the system")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<BoardItem> getAll() {
        return this.boardItemRepository.findAll();
    }

    @Operation(summary = "Get boardItem by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BoardItem getById(@PathVariable("id") Long id) {
        return this.boardItemRepository.findById(id).orElse(null);
    }

    //Da modificare ed implementare Api
    @Operation(summary = "Create a new boardItem")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BoardItem create(@RequestBody BoardItem boardItem) {
        //dobbiamo collegarlo ad una api
        return this.boardItemRepository.save(boardItem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public BoardItem update(@PathVariable("id") Long id, @RequestBody BoardItem boardItem) {
        BoardItem oldBoardItem = this.boardItemRepository.findById(id).orElse(null);
        if (oldBoardItem == null) {
            return null;
        }
        //Da aggiungere un service che faccia la creazione delle boardItem con API implementata/collegata
        boardItem.setId(id);
        boardItem.setCreatedAt(oldBoardItem.getCreatedAt());
        boardItem.setUpdatedAt(new Date());
        return this.boardItemRepository.save(boardItem);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        BoardItem boardItem = this.boardItemRepository.findById(id).orElse(null);
        if (boardItem == null) {
            return;
        }
        for(Board b : boardItem.getBoards()) {
            b.getBoardItems().remove(boardItem);
        }
        boardItem.setDeletedAt(new Date());
        this.boardItemRepository.save(boardItem);
    }

    @Operation(summary = "Get API from a boardItem id")
    @RequestMapping(value = "{id}/api", method = RequestMethod.GET)
    public Api getAPI(@PathVariable("id") Long id) {
        BoardItem boardItem = this.boardItemRepository.findById(id).orElse(null);
        if (boardItem == null) {
            return null;
        }
        return boardItem.getApi();
    }

}
