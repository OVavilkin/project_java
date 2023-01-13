package net.associal.bbooked.entity.searchPattern;

public class TourPattern implements SearchPattern{

    @Override
    public String getColumns() {
        return "Tour.id, Tour.name, Tour.description, Tour.user, Status.status, Tour.hot, Tour.hotel, Tour.person, Tour.price, Tour.percent";
    }

    @Override
    public String getJoins() {
        return "inner join Status on Tour.status = Status.id";
    }

    @Override
    public String getWhere() {
        return null;
    }

}
