package net.associal.bbooked.entity;

import java.util.Arrays;

/**
 * Tour class to use with DB
 */
public class
Tour {
    private int id;
    private String name;
    private String description;
    private User user;
    private String[] tags;
    private String status;
    private boolean hot;
    private int hotel;
    private int person;
    private double price;
    private double percent;

    public Tour(int id, String name, String description, User user, String[] tags, String status, boolean hot, int hotel, int person, double price, double percent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.tags = tags;
        this.status = status;
        this.hot = hot;
        this.hotel = hotel;
        this.person = person;
        this.price = price;
        this.percent = percent;
    }

  public Tour() {

  }

  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public double getPercent() {
    return percent;
  }

  public void setPercent(double percent) {
    this.percent = percent;
  }

  @Override
  public String toString() {
    return "Tour{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", user=" + user +
      ", tags=" + Arrays.toString(tags) +
      ", status='" + status + '\'' +
      ", hot=" + hot +
      ", hotel=" + hotel +
      ", person=" + person +
      ", percent=" + percent +
      '}';
  }
}
