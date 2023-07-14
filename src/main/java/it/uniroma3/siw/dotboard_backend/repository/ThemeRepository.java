package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    public Optional<Theme> findByNameOrColorAndDeletedAtIsNull(String name, String color);

    public Iterable<Theme> findAllByDeletedAtIsNull();

    public Optional<Theme> findByIdAndDeletedAtIsNull(Long id);

}
