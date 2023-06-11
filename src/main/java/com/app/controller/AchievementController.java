package com.app.controller;

import com.app.exception.custom.AchievementNotFoundForGameException;
import com.app.support.HttpResponse;
import com.app.exception.ExceptionHandling;
import com.app.exception.custom.AchievementNotFoundException;
import com.app.exception.custom.AchievementsExistException;
import com.app.exception.custom.GameNotFoundException;
import com.app.model.Achievement;
import com.app.service.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.app.constant.Constant.ACHIEVEMENT_HAS_DELETED_GAME_APP;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/achievement"})
public class AchievementController extends ExceptionHandling {


    private AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    //Api Specification
    //Create Achievement
    @PostMapping("/add")
    public ResponseEntity<Achievement> addNewAchievement(
            @RequestParam(name = "displayName") String displayName,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "displayOrder") Integer displayOrder,
            @RequestParam(name = "gameId") String gameId) throws AchievementsExistException, AchievementNotFoundException, GameNotFoundException {
        Achievement addNewAchievement = achievementService.createNew(displayName, description, displayOrder, gameId);
        return new ResponseEntity<>(addNewAchievement, OK);
    }

    //Get all game Achievements
    @GetMapping("/list/{gameId}")
    public ResponseEntity<List<Achievement>> getAll(@PathVariable("gameId") String gameId) throws AchievementNotFoundException, AchievementNotFoundForGameException, GameNotFoundException {
        List<Achievement> achievements = achievementService.getAllAchievemnts(gameId);
        return new ResponseEntity<>(achievements, OK);
    }

    //Get Achievement
    @GetMapping("/find/{achievementId}")
    public ResponseEntity<Achievement> getOne(@PathVariable("achievementId") String achievementId) throws AchievementNotFoundException {
        Achievement achievement = achievementService.findOne(achievementId);
        return new ResponseEntity<>(achievement, OK);
    }

    //Update Achievement
    @PostMapping("/update")
    public ResponseEntity<Achievement> updateAchievement(
            @RequestParam(name = "currentDisplayName") String currentDisplayName,
            @RequestParam(name = "displayName") String displayName,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "displayOrder") Integer displayOrder,
            @RequestParam(name = "gameId") String gameId) throws AchievementsExistException, AchievementNotFoundException, GameNotFoundException {

        Achievement updateAchievement = achievementService.updateAchievement(currentDisplayName, displayName, description, displayOrder, gameId);
        return new ResponseEntity<>(updateAchievement, OK);
    }

    //Delete Achievement
    @DeleteMapping("/delete/{achievementId}")
    public ResponseEntity<HttpResponse> deleteAchievement(@PathVariable("achievementId") String achievementId) throws AchievementNotFoundException, IOException {
        achievementService.deleteAchievement(achievementId);
        return response(OK, ACHIEVEMENT_HAS_DELETED_GAME_APP);
    }

    //private methods-------------------------------------------------------------------------------------------------------------
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
