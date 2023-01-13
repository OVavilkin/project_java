package net.associal.bbooked.entity.searchPattern;

public class PricePattern implements SearchPattern {

    private int min;
    private int max;

    public PricePattern(int min, int max) {
        this.min = min;
        this.max = max;
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
        return "price >= " + this.min + " and price <= " + this.max;
    }
}
