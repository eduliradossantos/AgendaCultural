package com.agenda.agendacultural.service;

import java.util.List;
import java.util.UUID;

import com.agenda.agendacultural.dto.FavoriteDTO;

public interface FavoriteService {

    FavoriteDTO addFavorite(FavoriteDTO favoriteDTO);

    void removeFavorite(UUID userId, UUID eventId);

    List<FavoriteDTO> getFavoritesByUser(UUID userId);
}
