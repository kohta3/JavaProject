package com.example.animeTitle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.AnimeTitle;

@Repository
public interface AnimeTitleRepository extends JpaRepository<AnimeTitle, Long>{

	AnimeTitle findByName(String name);
}
