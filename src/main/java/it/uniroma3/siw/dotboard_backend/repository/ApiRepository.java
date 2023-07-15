package it.uniroma3.siw.dotboard_backend.repository;

import it.uniroma3.siw.dotboard_backend.model.Api;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApiRepository extends JpaRepository<Api, Long> {

    Api findByIdAndDeletedAtIsNull(Long id);


    List<Api> findAllByDeletedAtIsNull();
}
