package controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dto.FavoriteDTO;
import jakarta.validation.Valid;
import service.FavoriteService;

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
        logger.debug("Requisição recebida para adicionar favorito: {}", favoriteDTO);
        FavoriteDTO createdFavorite = favoriteService.addFavorite(favoriteDTO);
        logger.info("Favorito criado com sucesso: {}", createdFavorite.getIdFavorite());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFavorite);
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam UUID userId, @RequestParam UUID eventId) {
        logger.debug("Requisição para remover favorito: userId={}, eventId={}", userId, eventId);
        favoriteService.removeFavorite(userId, eventId);
        logger.info("Favorito removido com sucesso: userId={}, eventId={}", userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable UUID userId) {
        logger.debug("Requisição para buscar favoritos do usuário: {}", userId);
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByUser(userId);
        logger.info("Favoritos retornados para usuário {}: total={}", userId, favorites.size());
        return ResponseEntity.ok(favorites);
    }
}
