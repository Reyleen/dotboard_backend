package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardItemRepository extends JpaRepository<BoardItem, Long> {

    BoardItem findByIdAndDeletedAtIsNull(Long id);
}
