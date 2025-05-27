package com.agenda.agendacultural.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agenda.agendacultural.dto.FavoriteDTO;
import jakarta.validation.Valid;
import com.agenda.agendacultural.service.FavoriteService;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteDTO> addFavorite(@Valid @RequestBody FavoriteDTO favoriteDTO) {
        logger.info("POST /api/favorites - Adding favorite");
        FavoriteDTO createdFavorite = favoriteService.addFavorite(favoriteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFavorite);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable UUID userId) {
        logger.info("GET /api/favorites/user/{} - Getting favorites for user", userId);
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByUser(userId);
        return ResponseEntity.ok(favorites);
    }
    
    @DeleteMapping("/user/{userId}/event/{eventId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable UUID userId, @PathVariable UUID eventId) {
        logger.info("DELETE /api/favorites/user/{}/event/{} - Removing favorite", userId, eventId);
        favoriteService.removeFavorite(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
