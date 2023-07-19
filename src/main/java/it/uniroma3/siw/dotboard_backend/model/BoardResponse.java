package it.uniroma3.siw.dotboard_backend.model;

public class BoardResponse {
    private Board board;
    private String username;

    public BoardResponse(Board board, String username) {
        this.board = board;
        this.username = username;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
