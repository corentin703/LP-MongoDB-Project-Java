package fr.pangolins.leProPlusPlus.responses;

import fr.pangolins.leProPlusPlus.domain.entities.Notice;

public class NoticeResponse {
    private String id;
    private String title;
    private String content;
    private float mark;

    public NoticeResponse() {
        //
    }

    public NoticeResponse(Notice notice) {
        id = notice.getId().toHexString();
        title = notice.getTitle();
        content = notice.getContent();
        mark = notice.getMark();
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
