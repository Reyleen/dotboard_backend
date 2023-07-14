package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Api;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ApiRepository extends JpaRepository<Api, Long> {

    Api findByIdAndDeletedAtIsNull(Long id);

    List<Api> findAll();

    Optional<Api> findById(Long id);
}
