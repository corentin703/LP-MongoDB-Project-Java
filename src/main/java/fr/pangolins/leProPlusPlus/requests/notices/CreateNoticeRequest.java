package fr.pangolins.leProPlusPlus.requests.notices;

public class CreateNoticeRequest {
    private String title;
    private String content;
    private Float mark;
    private String authorId;

    public CreateNoticeRequest() {
        //
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

    public Float getMark() {
        return mark;
    }

    public void setMark(Float mark) {
        this.mark = mark;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
