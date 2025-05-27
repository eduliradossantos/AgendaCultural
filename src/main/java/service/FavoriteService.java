package service;

import java.util.List;
import java.util.UUID;

import dto.FavoriteDTO;

public interface FavoriteService {

    FavoriteDTO addFavorite(FavoriteDTO favoriteDTO);

    void removeFavorite(UUID userId, UUID eventId);

    List<FavoriteDTO> getFavoritesByUser(UUID userId);
}
