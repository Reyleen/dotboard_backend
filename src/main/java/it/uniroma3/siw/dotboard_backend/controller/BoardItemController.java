package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.*;
import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;

import java.util.Date;

@RestController
@RequestMapping("/api/boardItems")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "BoardItem", description = "The BoardItem API. Contains all the operations that can be performed on a boardItem.")
public class BoardItemController  implements Validator {

    @Autowired
    private BoardItemRepository boardItemRepository;

    @Autowired
    private ApiRepository apiRepository;


    @Operation(summary = "Get boardItem by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BoardItem getById(@PathVariable("id") Long id) {
        return this.boardItemRepository.findById(id).orElse(null);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        BoardItem boardItem = this.boardItemRepository.findById(id).orElse(null);
        if (boardItem == null) {
            return;
        }
        boardItem.getBoard().getBoardItems().remove(boardItem);
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

    @Operation(summary = "Get API from a boardItem id")
    @RequestMapping(value = "{id}/api", method = RequestMethod.POST)
    public BoardItem setAPI(@PathVariable("itemId") Long itemId, @PathVariable("apiId") Long apiId) {
        BoardItem boardItem = this.boardItemRepository.findById(itemId).orElse(null);
        Api api = this.apiRepository.findById(apiId).orElse(null);
        if (boardItem == null || api == null) {
            return null;
        }
        boardItem.setApi(api);
        return this.boardItemRepository.save(boardItem);
    }
}
