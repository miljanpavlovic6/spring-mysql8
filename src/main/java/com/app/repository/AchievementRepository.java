package com.app.repository;
import com.app.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(value = "select * from achievement where displayname = :displayName",nativeQuery = true)
    Achievement findAchievementByDisplayName(@Param("displayName") String displayName);

    @Query(value = "select * from achievement where achievementid = :achievementId",nativeQuery = true)
    Achievement findAchievementByAchievementId(@Param("achievementId")String  achievementId);

    @Query(value = "select a.* from game g, achievement a where g.id = a.game_id and g.gameid = :gameId order by a.displayorder;",nativeQuery = true)
    List<Achievement> findAllAchievementOrderByDisplayOrder(@Param("gameId") String gameId);
}
