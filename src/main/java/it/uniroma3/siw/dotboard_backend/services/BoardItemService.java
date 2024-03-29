package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.repository.ApiRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;
import it.uniroma3.siw.dotboard_backend.utils.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.dotboard_backend.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@Service
public class BoardItemService {

    @Autowired
    private BoardItemRepository boardItemRepository;

    @Autowired
    private ApiRepository apiRepository;

    public boolean isOwner(Principal principal, BoardItem item){
        if(item.getBoard().getUser().getUsername().equals(principal.getName())){
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }
    }

    @Transactional
    public BoardItem getById(Long id){
        return this.boardItemRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Transactional
    public void delete(Long id, Principal principal){
        BoardItem boardItem = this.getById(id);
        if(boardItem == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardItem not found");
        }
        if(this.isOwner(principal, boardItem)){
        boardItem.getBoard().getBoardItems().remove(boardItem);
        if(boardItem.getApi() != null) {
            boardItem.getApi().getBoarditems().remove(boardItem);
        }
        boardItem.setDeletedAt(new Date());
        }
        this.boardItemRepository.save(boardItem);
    }

    @Transactional
    public Api getApi(Long id){
        BoardItem boardItem = this.getById(id);
        if (boardItem == null) {
            return null;
        }
        return boardItem.getApi();
    }

    @Transactional
    public BoardItem setApi(Long itemId, Long apiId,Principal principal){
        BoardItem boardItem = this.getById(itemId);
        Api api = this.apiRepository.findByIdAndDeletedAtIsNull(apiId);
        if(this.isOwner(principal, boardItem)) {
            if(boardItem.getApi() != null){
                boardItem.getApi().getBoarditems().remove(boardItem);
                boardItem.setApi(null);
            }
            boardItem.setApi(api);
            api.getBoarditems().add(boardItem);
        }
        return boardItemRepository.save(boardItem);
    }

    @Transactional
    public BoardItem update(Long id,BoardItem updatedBoardItem, Principal principal) {
        BoardItem boardItem = this.getById(id);
        if (this.isOwner(principal, boardItem)) {
            if (boardItem.getCategory() == ItemType.TEXT) {
                boardItem.setTitle(updatedBoardItem.getTitle());
                boardItem.setSubtitle(updatedBoardItem.getSubtitle());
            } else if (boardItem.getCategory() == ItemType.IMAGE || boardItem.getCategory() == ItemType.IFRAME) {
                boardItem.setTitle(updatedBoardItem.getTitle());
                boardItem.setUrl(updatedBoardItem.getUrl());
            } else if (boardItem.getCategory() == ItemType.WEATHER) {
                boardItem.setTitle(updatedBoardItem.getTitle());
            } else {
                boardItem.setUrl(updatedBoardItem.getUrl());
            }
            boardItem.setX(updatedBoardItem.getX());
            boardItem.setY(updatedBoardItem.getY());
            boardItem.setH(updatedBoardItem.getH());
            boardItem.setW(updatedBoardItem.getW());
        }

        return this.boardItemRepository.save(boardItem);
    }
}
