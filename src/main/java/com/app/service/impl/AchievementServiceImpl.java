package com.app.service.impl;
import com.app.exception.custom.AchievementNotFoundException;
import com.app.exception.custom.AchievementNotFoundForGameException;
import com.app.exception.custom.AchievementsExistException;
import com.app.exception.custom.GameNotFoundException;
import com.app.model.Achievement;
import com.app.model.Game;
import com.app.repository.AchievementRepository;
import com.app.repository.GameRepository;
import com.app.service.AchievementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import static com.app.constant.Constant.*;
import static org.apache.commons.lang3.StringUtils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    private AchievementRepository achievementRepository;
    private GameRepository gameRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    public AchievementServiceImpl(AchievementRepository achievementRepository, GameRepository gameRepository) {
        this.achievementRepository = achievementRepository;
        this.gameRepository = gameRepository;
    }

    public Achievement createNew(String displayName, String description, Integer displayOrder, String gameId) throws GameNotFoundException, AchievementsExistException, AchievementNotFoundException {
       validateNewAchievementAndGame(EMPTY, displayName, gameId);
        Achievement achievement = new Achievement();
        Game game = gameRepository.findGameByGameId(gameId);
        achievement.setAchievementId(generateAchievementId());
        achievement.setDisplayName(displayName);
        achievement.setDescription(description);
        achievement.setDisplayOrder(displayOrder);
        achievement.setGame(game);
        achievement.setCreated(new Date());
        achievement.setUpdated(new Date());
        achievement.setIcon(getIconUrl(displayName));
        achievementRepository.save(achievement);
        return achievement;
    }
    @Override
    public Achievement updateAchievement(String currentDisplayName, String newDisplayName, String newDescription, Integer newDisplayOrder, String gameId ) throws GameNotFoundException, AchievementsExistException, AchievementNotFoundException {
        Achievement currentAchievement = validateNewAchievementAndGame(currentDisplayName, newDisplayName, gameId);
        currentAchievement.setDisplayName(newDisplayName);
        currentAchievement.setDescription(newDescription);
        currentAchievement.setDisplayOrder(newDisplayOrder);
//        achievement.setGame(game);
        currentAchievement.setUpdated(new Date());
//        achievement.setIcon(getIconUrl(displayName));
        achievementRepository.save(currentAchievement);
        return currentAchievement;
    }

    @Override
    public List<Achievement> getAllAchievemnts(String gameId) throws AchievementNotFoundException, GameNotFoundException, AchievementNotFoundForGameException {
        Game checkGameExists = validateGame(gameId);

        List<Achievement> achievements = achievementRepository.findAllAchievementOrderByDisplayOrder(gameId);
        boolean ans = achievements.isEmpty();
        if (ans == true){
            LOGGER.info(NO_ACHIEVEMENT_FOUND_FOR_GAME + checkGameExists.getGameId());
           throw new AchievementNotFoundForGameException(NO_ACHIEVEMENT_FOUND_FOR_GAME + checkGameExists.getGameId());//success 204-no content//there will be nothing in body
        }else {
            return achievements;
        }
    }

    private Game validateGame(String gameId) throws GameNotFoundException {
        Game gameExists = gameRepository.findGameByGameId(gameId);
        if (gameExists == null){
            LOGGER.info(GAME_NOT_FOUND + " " +  gameId);
            throw new GameNotFoundException(GAME_NOT_FOUND + " " +  gameId);
        }else{
            return gameExists;
        }
    }

    @Override
    public Achievement findOne(String achievementId) throws AchievementNotFoundException {
        Achievement achievement = achievementRepository.findAchievementByAchievementId(achievementId);
        if(achievement == null){
            LOGGER.info(ACHIEVEMENT_NOT_FOUND + achievementId);
            throw new AchievementNotFoundException(ACHIEVEMENT_NOT_FOUND + achievementId);
        }else{
        return achievement;
        }
    }


    @Override
    public void deleteAchievement(String achievementId) throws IOException, AchievementNotFoundException {
        Achievement achievement = achievementRepository.findAchievementByAchievementId(achievementId);
        if(achievement == null){
            LOGGER.info(ACHIEVEMENT_NOT_FOUND +  achievementId);
            throw new AchievementNotFoundException(ACHIEVEMENT_NOT_FOUND +  achievementId);
        }else {
            achievementRepository.delete(achievement);
            LOGGER.info(ACHIEVEMENT_HAS_DELETED_SUCCESSFULLY);
        }
    }

//    private methods-----------------------------------------------------------------------------------------------------------------
    private String getIconUrl(String displayName) {
        return ServletUriComponentsBuilder.fromPath(TEMP_PROFILE_IMAGE_BASE_URL + displayName + DOT + PNG_EXTENSION).toUriString();
    }

    private String generateAchievementId() {
//        String newId = RandomStringUtils.randomAscii(17); //delete dont work with Ascii ID
        String newId = RandomStringUtils.randomAlphanumeric(17);
        Achievement achievement = achievementRepository.findAchievementByAchievementId(newId);
        if(achievement != null){
            LOGGER.info(ID_EXISTS_CREATING_NEW_ID);
            newId = RandomStringUtils.randomAlphanumeric(5);
        }else{
        return newId;
        }
        return null;
    }

    //validate achievements and games
    private Achievement validateNewAchievementAndGame(String currentDisplayName, String displayName, String gameId) throws GameNotFoundException, AchievementsExistException, AchievementNotFoundException {
        Achievement achievementByNewDisplayName = findAchievementByDisplayName(displayName);
        Game game = findGameByGameId(gameId);

        if(game == null){
            throw new GameNotFoundException(GAME_NOT_FOUND + " " +  gameId);
        }else {
            LOGGER.info("Game exists !");
        }

        if(StringUtils.isNotBlank(currentDisplayName) && StringUtils.isNotBlank(gameId)) {
            Achievement currentAchievement = findAchievementByDisplayName(currentDisplayName);
            Game currentGame = findGameByGameId(gameId);

            if(currentAchievement == null) {
                LOGGER.info(ACHIEVEMENT_NOT_FOUND +  currentDisplayName);
                throw new AchievementNotFoundException(ACHIEVEMENT_NOT_FOUND + currentDisplayName);
            }

            if(currentGame == null) {
                LOGGER.info(GAME_NOT_FOUND + gameId);
                throw new GameNotFoundException(GAME_NOT_FOUND + gameId);
            }

            if(achievementByNewDisplayName != null && !currentAchievement.getAchievementId().equals(achievementByNewDisplayName.getAchievementId())) {
                LOGGER.info(ACHIEVEMENT_EXISTS);
                throw new AchievementsExistException(ACHIEVEMENT_EXISTS);
            }

            return currentAchievement;
        } else {
            if(achievementByNewDisplayName != null) {
                LOGGER.info(ACHIEVEMENT_EXISTS);
                throw new AchievementsExistException(ACHIEVEMENT_EXISTS);
            }
            return null;
        }
    }

    private Game findGameByGameId(String gameId) {
        return gameRepository.findGameByGameId(gameId);
    }

    private Achievement findAchievementByDisplayName(String displayName) {
        return achievementRepository.findAchievementByDisplayName(displayName);
    }


}
