package com.app.repository;

import com.app.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(value = "select * from game where gameid = :gameId",nativeQuery = true)
    Game findGameByGameId(@Param("gameId") String gameId);

}
