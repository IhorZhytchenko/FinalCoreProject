package Entity;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Snippet {
    private String title;
    private Calendar publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Calendar publishedAt) {
        this.publishedAt = publishedAt;
    }
}
