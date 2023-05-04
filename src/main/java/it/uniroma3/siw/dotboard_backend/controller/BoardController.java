package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@Api(tags = "Board")
public class BoardController {
@Autowired
    private BoardRepository boardRepository;

@RequestMapping (value="/", method= RequestMethod.GET )
    public List<Board> getAll(){
    return boardRepository.findAll();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Board getOne(@PathVariable("id") Long id) {
        return this.boardRepository.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Board create(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Board update(@PathVariable("id") Long id, @RequestBody Board board) {
        return boardRepository.update(id, board); 
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        boardRepository.delete(id);
    }
}
