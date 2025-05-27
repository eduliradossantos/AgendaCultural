package mapper;

import java.util.List;
import java.util.stream.Collectors;

import dto.FavoriteDTO;
import model.Favorite;

public class FavoriteMapper {

    public FavoriteDTO toDTO(Favorite favorite) {
        if (favorite == null) {
            return null;
        }
        FavoriteDTO dto = new FavoriteDTO();
        dto.setIdFavorite(favorite.getIdFavorite());
        dto.setUserId(favorite.getUser().getIdUser());
        dto.setEventId(favorite.getEvent().getIdEvent());
        dto.setFavoritedDate(favorite.getFavoritedDate());
        return dto;
    }

    public List<FavoriteDTO> toDTOList(List<Favorite> favorites) {
        return favorites.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
