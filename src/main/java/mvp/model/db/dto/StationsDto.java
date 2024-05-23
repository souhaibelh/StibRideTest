package mvp.model.db.dto;
import mvp.model.db.tablepk.StationsPK;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StationsDto extends StationsPK {
    private final String name;
    private List<Integer> lines;

    public StationsDto() {
        super(-1);
        name = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StationsDto that = (StationsDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    public StationsDto(String name, Integer key) {
        super(key);
        this.lines = new ArrayList<>();
        this.name = name;
    }

    public void setLines(List<Integer> lines) {
        this.lines = lines;
    }

    public String getName() {
        return this.name;
    }

    public List<Integer> getLines() {
        return this.lines;
    }

    @Override
    public String toString() {
        return name;
    }
}
