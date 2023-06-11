package com.app.service;

import com.app.exception.custom.AchievementNotFoundException;
import com.app.exception.custom.AchievementNotFoundForGameException;
import com.app.exception.custom.AchievementsExistException;
import com.app.exception.custom.GameNotFoundException;
import com.app.model.Achievement;

import java.io.IOException;
import java.util.List;

public interface AchievementService {

    Achievement createNew(String displayName, String description, Integer displayOrder, String gameId) throws GameNotFoundException, AchievementsExistException, AchievementNotFoundException;

    List<Achievement> getAllAchievemnts(String gameId) throws AchievementNotFoundException, GameNotFoundException, AchievementNotFoundForGameException;

    Achievement findOne(String achievementId) throws AchievementNotFoundException;

    Achievement updateAchievement(String currentDisplayName, String displayName, String description, Integer displayOrder, String gameId) throws GameNotFoundException, AchievementsExistException, AchievementNotFoundException;

    void deleteAchievement(String achievementId) throws IOException, AchievementNotFoundException;



}
