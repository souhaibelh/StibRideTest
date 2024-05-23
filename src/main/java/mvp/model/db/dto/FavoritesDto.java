package mvp.model.db.dto;
import mvp.model.db.tablepk.FavoritesPK;

import java.util.Objects;

public class FavoritesDto extends FavoritesPK {
    private final StationsDto origin;
    private final StationsDto destination;
    private final String name;

    public FavoritesDto(String name, StationsDto origin, StationsDto destination) {
        super(-1);
        this.name = name;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritesDto that = (FavoritesDto) o;
        return Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination);
    }

    public FavoritesDto() {
        super(-1);
        name = "";
        origin = new StationsDto();
        destination = new StationsDto();
    }

    public StationsDto getOrigin() {
        return origin;
    }

    public StationsDto getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return name + "\n" + origin + "\n" + destination + "\n" + getId();
    }

    public String getName() {
        return name;
    }
}
