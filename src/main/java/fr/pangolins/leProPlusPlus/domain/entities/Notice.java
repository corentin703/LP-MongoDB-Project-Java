package fr.pangolins.leProPlusPlus.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notices")
public class Notice {
    @Id
    private String id;

    private String title;
    private String content;
    private float mark;

    public Notice() {
        //
    }

    public Notice(String title, String content, float mark) {
        this.title = title;
        this.content = content;
        this.mark = mark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
