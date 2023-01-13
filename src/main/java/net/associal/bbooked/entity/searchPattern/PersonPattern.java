package net.associal.bbooked.entity.searchPattern;

public class PersonPattern implements SearchPattern {

    private int person;

    public PersonPattern(int person) {
        this.person = person;
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
        return "person >= " + this.person;
    }
}
