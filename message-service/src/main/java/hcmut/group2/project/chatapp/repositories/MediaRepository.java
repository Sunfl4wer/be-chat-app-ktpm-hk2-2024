package hcmut.group2.project.chatapp.repositories;


import hcmut.group2.project.chatapp.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findById(Long id);
}