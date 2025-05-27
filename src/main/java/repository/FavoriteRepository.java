package repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    // Buscar favoritos por usuário
    List<Favorite> findByUserId(UUID userId);

    // Verificar se um evento já está favoritado por um usuário (evitar duplicidade)
    boolean existsByUserIdAndEventId(UUID userId, UUID eventId);

    // Buscar favorito específico (para delete por exemplo)
    Favorite findByUserIdAndEventId(UUID userId, UUID eventId);
}
