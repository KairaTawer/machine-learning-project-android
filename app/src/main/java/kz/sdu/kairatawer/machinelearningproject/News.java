package kz.sdu.kairatawer.machinelearningproject;

/**
 * Created by асус on 18.12.2017.
 */

public class News {
    String date;
    String title;
    String short_info;
    String description;
    String link;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String label;

    public News(String date, String title, String short_info, String description, String link, String label) {
        this.date = date;
        this.title = title;
        this.short_info = short_info;
        this.description = description;
        this.link = link;
        this.label = label;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_info() {
        return short_info;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
