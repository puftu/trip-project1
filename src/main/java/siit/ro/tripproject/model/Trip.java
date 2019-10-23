package siit.ro.tripproject.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String tripStart;
    private String tripEnd;
    private String Impressions;
    private String photo1;
    private String photo2;
    private String photo1Description;
    private String photo2Description;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public String getTripStart() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tripStart=" + tripStart +
                ", tripEnd=" + tripEnd +
                ", Impressions='" + Impressions + '\'' +
                ", photo1='" + photo1 + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", photo1Description='" + photo1Description + '\'' +
                ", photo2Description='" + photo2Description + '\'' +
                '}';
    }

    public String getPhoto1Description() {
        return photo1Description;
    }

    public void setPhoto1Description(String photo1Description) {
        this.photo1Description = photo1Description;
    }

    public String getPhoto2Description() {
        return photo2Description;
    }

    public void setPhoto2Description(String photo2Description) {
        this.photo2Description = photo2Description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImpressions() {
        return Impressions;
    }

    public void setImpressions(String impressions) {
        Impressions = impressions;
    }

}
