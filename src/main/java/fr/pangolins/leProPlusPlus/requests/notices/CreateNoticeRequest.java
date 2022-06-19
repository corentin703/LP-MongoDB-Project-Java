package fr.pangolins.leProPlusPlus.requests.notices;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateNoticeRequest {
    @NotBlank(message = "notice.title.required")
    private String title;

    @NotBlank(message = "notice.content.required")
    private String content;

    @NotNull(message = "notice.mark.required")
    private Float mark;

    @NotBlank(message = "notice.author.required")
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
