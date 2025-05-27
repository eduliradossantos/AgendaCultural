package service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import dto.FavoriteDTO;
import exception.ResourceNotFoundException;
import mapper.FavoriteMapper;
import model.Event;
import model.Favorite;
import model.User;
import repository.EventRepository;
import repository.FavoriteRepository;
import repository.UserRepository;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, FavoriteMapper favoriteMapper,
            UserRepository userRepository, EventRepository eventRepository) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteMapper = favoriteMapper;
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

        if (favoriteRepository.existsByUserIdAndEventId(user.getIdUser(), event.getIdEvent())) {
            logger.warn("Evento já favoritado: userId={}, eventId={}", user.getIdUser(), event.getIdEvent());
            throw new IllegalStateException("Event already favorited by user");
        }

        Favorite favorite = Favorite.builder()
                .idFavorite(UUID.randomUUID())
                .user(user)
                .event(event)
                .build();

        Favorite savedFavorite = favoriteRepository.save(favorite);
        logger.info("Favorito adicionado com sucesso: {}", savedFavorite.getIdFavorite());

        return favoriteMapper.toDTO(savedFavorite);
    }

    @Override
    public void removeFavorite(UUID userId, UUID eventId) {
        logger.info("Removendo favorito: userId={}, eventId={}", userId, eventId);

        Favorite favorite = favoriteRepository.findByUserIdAndEventId(userId, eventId);
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
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        logger.info("Total de favoritos encontrados: {}", favorites.size());
        return favorites.stream()
                .map(favoriteMapper::toDTO)
                .collect(Collectors.toList());
    }
}
