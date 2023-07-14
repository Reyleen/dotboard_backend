package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import it.uniroma3.siw.dotboard_backend.model.Api;
import it.uniroma3.siw.dotboard_backend.model.*;
import it.uniroma3.siw.dotboard_backend.services.BoardItemService;
import it.uniroma3.siw.dotboard_backend.services.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;


@RestController
@RequestMapping("/api/boardItems")
@SecurityRequirement(name = "Bearer_JWT")
@Tag(name = "BoardItem", description = "The BoardItem API. Contains all the operations that can be performed on a boardItem.")
public class BoardItemController  implements Validator {

    @Autowired
    private BoardItemService boardItemService;

    @Operation(summary = "Get boardItem by id")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public BoardItem getById(@PathVariable("id") Long id) {
        return this.boardItemService.getById(id);
    }


    @Operation(summary = "Delete a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id, Principal principal) {
        this.boardItemService.delete(id, principal);
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
        return this.boardItemService.setApi(itemId,apiId,principal);
    }

    @Operation(summary = "Update a boardItem")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public BoardItem update(@PathVariable("id") Long id, @RequestBody BoardItem updatedBoardItem,
                            Principal principal) {
        return this.boardItemService.update(id,updatedBoardItem,principal);
    }
}
