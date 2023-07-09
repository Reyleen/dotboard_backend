package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.dotboard_backend.model.*;

import java.util.Date;

public class BoardItemService {

    @Autowired
    private BoardItemRepository boardItemRepository;

    public BoardItem createBoardItem(BoardItem boardItem, Api api){
    boardItem.setCreatedAt(new Date());
    boardItem.setHeight(1); //misure default
    boardItem.setWidth(1);
    if(api != null){
        boardItem.setApi(api);
    }
    this.boardItemRepository.save(boardItem);
    return boardItem;
    }
}
