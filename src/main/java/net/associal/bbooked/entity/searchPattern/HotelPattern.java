package net.associal.bbooked.entity.searchPattern;

public class HotelPattern implements SearchPattern {

    private int stars;

    public HotelPattern(int stars) {
        this.stars = stars;
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
        return "hotel >= " + stars;
    }
}
