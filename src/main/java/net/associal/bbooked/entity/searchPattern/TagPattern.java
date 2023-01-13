package net.associal.bbooked.entity.searchPattern;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagPattern implements SearchPattern {
    private String[] tags;

    public TagPattern(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String getColumns() {
        return null;
    }

    @Override
    public String getJoins() {
        return null;
    }

    @Override
    public String getWhere() {
        String where = "Tour.id in (select tour from TourTag inner join Tag on Tag.id = TourTag.tag where Tag.tag in (";
        List<String> list = Arrays.asList(this.tags);
        String tags = "'" + list.stream().collect(Collectors.joining("', '")) + "')";
        return where + tags + ")";
    }
}
