package vttp.batch5.ssf.noticeboard.models;

import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {

    @NotNull(message="need a title")
    @Size(min = 3, max = 128, message = "min 3, max 128 characters")
    private String title;

    @NotEmpty(message="need a email")
    @Email(message="should be valid email")
    private String poster; //email

    @NotNull(message="cannot be empty")
    @FutureOrPresent(message="future or present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    @NotNull(message="at least 1")
    private String[] categories;

    @NotEmpty(message="cannot be empty")
    private String text;

    private String id;

    public String getTitle() {    return title;}
    public void setTitle(String title) {    this.title = title;}
    
    public String getPoster() {    return poster;}
    public void setPoster(String poster) {    this.poster = poster;}
    
    public Date getPostDate() {    return postDate;}
    public void setPostDate(Date postDate) {    this.postDate = postDate;}
    
    public String[] getCategories() {    return categories;}
    public void setCategories(String[] categories) {    this.categories = categories;}
    
    public String getText() {    return text;}
    public void setText(String text) {    this.text = text;}

    public String getId() {    return id;}
    public void setId(String id) {    this.id = id;}

    @Override
    public String toString() {
        return "title: %s\n".formatted(title) +
                "poster: %s\n".formatted(poster) +
                "postDate: %s\n".formatted(postDate) +
                "categories:" + Arrays.toString(categories) + "\n"+
                "text: %s\n".formatted(text)+
                "id: %s\n".formatted(id);

    }


    
}
