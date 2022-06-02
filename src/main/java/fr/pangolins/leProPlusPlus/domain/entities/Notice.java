package fr.pangolins.leProPlusPlus.domain.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notices")
public class Notice {
    @Id
    private ObjectId id;

    private String title;
    private String content;
    private float mark;
    @DBRef()
    private Company author;

    public Notice() {
        //
    }

    public Notice(String title, String content, float mark, Company author) {
        this.title = title;
        this.content = content;
        this.mark = mark;
        this.author = author;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public Company getAuthor() {
        return author;
    }

    public void setAuthor(Company author) {
        this.author = author;
    }
}
