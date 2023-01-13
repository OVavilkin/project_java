package net.associal.bbooked.entity.searchPattern;

public class ManagerPattern implements SearchPattern {
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
        return "Status.status in ('registered', 'paid', 'cancelled')";
    }
}
