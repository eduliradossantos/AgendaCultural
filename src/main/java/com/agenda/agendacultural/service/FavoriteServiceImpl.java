package com.agenda.agendacultural.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agenda.agendacultural.dto.FavoriteDTO;
import com.agenda.agendacultural.exception.ResourceNotFoundException;
import com.agenda.agendacultural.model.Event;
import com.agenda.agendacultural.model.Favorite;
import com.agenda.agendacultural.model.User;
import com.agenda.agendacultural.repository.EventRepository;
import com.agenda.agendacultural.repository.FavoriteRepository;
import com.agenda.agendacultural.repository.UserRepository;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
            UserRepository userRepository, EventRepository eventRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public FavoriteDTO addFavorite(FavoriteDTO favoriteDTO) {
        logger.info("Iniciando adição de favorito: userId={}, eventId={}",
                favoriteDTO.getUserId(), favoriteDTO.getEventId());

        User user = userRepository.findById(favoriteDTO.getUserId())
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado: {}", favoriteDTO.getUserId());
                    return new ResourceNotFoundException("User not found");
                });

        Event event = eventRepository.findById(favoriteDTO.getEventId())
                .orElseThrow(() -> {
                    logger.warn("Evento não encontrado: {}", favoriteDTO.getEventId());
                    return new ResourceNotFoundException("Event not found");
                });

        if (favoriteRepository.existsByUser_IdUserAndEvent_IdEvent(user.getIdUser(), event.getIdEvent())) {
            logger.warn("Evento já favoritado: userId={}, eventId={}", user.getIdUser(), event.getIdEvent());
            throw new IllegalStateException("Event already favorited by user");
        }

        Favorite favorite = new Favorite();
        favorite.setIdFavorite(UUID.randomUUID());
        favorite.setUser(user);
        favorite.setEvent(event);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        logger.info("Favorito adicionado com sucesso: {}", savedFavorite.getIdFavorite());

        return convertToDto(savedFavorite);
    }

    @Override
    public void removeFavorite(UUID userId, UUID eventId) {
        logger.info("Removendo favorito: userId={}, eventId={}", userId, eventId);

        Favorite favorite = favoriteRepository.findByUser_IdUserAndEvent_IdEvent(userId, eventId);
        if (favorite == null) {
            logger.warn("Favorito não encontrado para remoção: userId={}, eventId={}", userId, eventId);
            throw new ResourceNotFoundException("Favorite not found for user and event");
        }

        favoriteRepository.delete(favorite);
        logger.info("Favorito removido com sucesso: userId={}, eventId={}", userId, eventId);
    }

    @Override
    public List<FavoriteDTO> getFavoritesByUser(UUID userId) {
        logger.info("Buscando favoritos do usuário: {}", userId);
        List<Favorite> favorites = favoriteRepository.findByUser_IdUser(userId);
        logger.info("Total de favoritos encontrados: {}", favorites.size());
        return favorites.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private FavoriteDTO convertToDto(Favorite favorite) {
        if (favorite == null) {
            return null;
        }
        FavoriteDTO dto = new FavoriteDTO();
        dto.setIdFavorite(favorite.getIdFavorite());
        if (favorite.getUser() != null) {
            dto.setUserId(favorite.getUser().getIdUser());
        }
        if (favorite.getEvent() != null) {
            dto.setEventId(favorite.getEvent().getIdEvent());
        }
        dto.setFavoritedDate(favorite.getFavoritedDate());
        return dto;
    }
}
