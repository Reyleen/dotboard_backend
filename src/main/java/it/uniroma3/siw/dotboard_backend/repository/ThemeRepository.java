package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Optional<Theme> findByNameOrColorAndDeletedAtIsNull(String name, String color);

    Iterable<Theme> findAllByDeletedAtIsNull();

    Optional<Theme> findByIdAndDeletedAtIsNull(Long id);

}
