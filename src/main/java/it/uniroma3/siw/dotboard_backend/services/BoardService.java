package it.uniroma3.siw.dotboard_backend.services;

import it.uniroma3.siw.dotboard_backend.model.ApplicationUser;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.Theme;
import it.uniroma3.siw.dotboard_backend.repository.ApplicationUserRepository;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    public Board createBoard(Board board, ApplicationUser user){
        board.setCreatedAt(new Date());
        board.setUser(user);
        this.boardRepository.save(board);
        user.getBoards().add(board);
        applicationUserRepository.save(user);
        return board;
    }

    public void deleteBoard(Board board){
        board.setDeletedAt(new Date());
        board.getUser().getBoards().remove(board);
        if(board.getTheme() != null)
            board.getTheme().getBoards().remove(board);
        this.boardRepository.save(board);
    }

    public Board addTheme(Board board, Theme theme){
        if(board.getTheme() != null)
            board.getTheme().getBoards().remove(board);
        board.setTheme(theme);
        theme.getBoards().add(board);
        return this.boardRepository.save(board);
    }
}
