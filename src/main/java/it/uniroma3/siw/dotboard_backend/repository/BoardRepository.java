package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Board;
import it.uniroma3.siw.dotboard_backend.model.BoardItem;
import it.uniroma3.siw.dotboard_backend.utils.MergeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, MergeUpdate {


  Optional<Board> findByIdAndDeletedAtIsNull(Long id);

  // find all boards owned by user with id = userId
  List<Board> findByUserIdAndDeletedAtIsNull(Long userId);
}