package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.*;
import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import it.uniroma3.siw.dotboard_backend.services.BoardItemService;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import it.uniroma3.siw.dotboard_backend.utils.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@RestController
@RequestMapping("/api/boardItems")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "BoardItem", description = "The BoardItem API. Contains all the operations that can be performed on a boardItem.")
public class BoardItemController  implements Validator {

    @Autowired
    private BoardItemRepository boardItemRepository;

    @Autowired
    private BoardItemService boardItemService;

    @Autowired
    private ApiRepository apiRepository;

    @Operation(summary = "Get boardItem by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BoardItem getById(@PathVariable("id") Long id) {
        return this.boardItemService.getById(id);
    }


    @Operation(summary = "Delete a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id, Principal principal) {
        BoardItem boardItem = this.boardItemService.delete(id, principal);
        this.boardItemRepository.save(boardItem);
    }

    @Operation(summary = "Get API from a boardItem id")
    @RequestMapping(value = "{id}/api", method = RequestMethod.GET)
    public Api getAPI(@PathVariable("id") Long id) {
        return this.boardItemService.getApi(id);
    }

    @Operation(summary = "Get API from a boardItem id")
    @RequestMapping(value = "{itemId}/api/{apiId}", method = RequestMethod.POST)
    public BoardItem setAPI(@PathVariable("itemId") Long itemId,
                            @PathVariable("apiId") Long apiId,
                            Principal principal) {
        BoardItem boardItem = this.boardItemService.setApi(itemId,apiId,principal);
        return this.boardItemRepository.save(boardItem);
    }

    @Operation(summary = "Update a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public BoardItem update(@PathVariable("id") Long id, @RequestParam @Nullable String title,
                            @RequestParam @Nullable String subtitle,
                            @RequestParam @Nullable String url,
                            @RequestParam Integer x, @RequestParam Integer y,
                            Principal principal) {

        BoardItem boardItem = this.boardItemService.update(id,title,subtitle,url,x,y,principal);
        return this.boardItemRepository.save(boardItem);
    }
}
