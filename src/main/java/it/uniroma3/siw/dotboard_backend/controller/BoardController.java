package it.uniroma3.siw.dotboard_backend.controller;

import io.swagger.annotations.Api;
import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@Api(tags = "Board")
public class BoardController {
@Autowired
    private BoardRepository boardRepository;

}
