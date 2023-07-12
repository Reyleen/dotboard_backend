package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    public Optional<Theme> findByNameAndDeletedAtIsNull(String name);

    public Optional<Theme> findByColorAndDeletedAtIsNull(String color);

    public Iterable<Theme> findAllByDeletedAtIsNull();

    public Optional<Theme> findByIdAndDeletedAtIsNull(Long id);
}
