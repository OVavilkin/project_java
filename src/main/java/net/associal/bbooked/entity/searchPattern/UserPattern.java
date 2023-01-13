package net.associal.bbooked.entity.searchPattern;

public class UserPattern implements SearchPattern {

    private int id;

    public UserPattern(int id) {
        this.id = id;
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
        return "Tour.user = " + this.id;
    }
}
