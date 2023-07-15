package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardItemRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Date;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    BoardItemRepository boardItemRepository;

    public boolean isOwner(Principal principal, Board board){
        if(board.getUser().getUsername().equals(principal.getName())){
            return true;
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to access this resource");
        }
    }

    @Transactional
    public Board getById(Long id) {
        return this.boardRepository.findByIdAndDeletedAtIsNull(id);
    }

    @Transactional
    public Board createBoard(Board board, ApplicationUser user){
        board.setUser(user);
        this.boardRepository.save(board);
        user.getBoards().add(board);
        applicationUserRepository.save(user);
        return board;
    }

    @Transactional
    public Board update(Long id,Board updatedBoard, Principal principal){
        Board board = this.getById(id);
        if (this.isOwner(principal, board)) {
            board.setName(updatedBoard.getName());
            board.setDescription(updatedBoard.getDescription());
            board.setPublic(updatedBoard.isPublic());
            board.setX(updatedBoard.getX());
            board.setY(updatedBoard.getY());
            board.setH(updatedBoard.getH());
            board.setW(updatedBoard.getW());
            board.setPublicLink(updatedBoard.getPublicLink());
        }

        return this.boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Board board){
        board.setDeletedAt(new Date());
        board.getUser().getBoards().remove(board);
        if(board.getTheme() != null)
            board.getTheme().getBoards().remove(board);
        this.boardRepository.save(board);
    }

    @Transactional
    public Board addTheme(Board board, Theme theme){
        if(board.getTheme() != null)
            board.getTheme().getBoards().remove(board);
        board.setTheme(theme);
        theme.getBoards().add(board);
        return this.boardRepository.save(board);
    }

    @Transactional
    public void removeThemeFromBoard(Board board){
        if(board.getTheme() != null)
            board.getTheme().getBoards().remove(board);
        board.setTheme(null);
        this.boardRepository.save(board);
    }

    @Transactional
    public Board createItemToBoard(Board board, BoardItem boardItem){
       boardItemRepository.save(boardItem);
       board.getBoardItems().add(boardItem);
       boardItem.setBoard(board);
         return this.boardRepository.save(board);
    }
}
