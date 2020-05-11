package cn.huanhu.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (BookStore)实体类
 * @author m
 * @since 2020-05-11
 */
@Data
public class BookStore implements Serializable {
    private static final long serialVersionUID = 738266294681995275L;
    
    private Integer id;
    
    private String bookName;
    
    private String author;
    
    private String translator;
    
    private String bookMan;
    
    private Double douBanScore;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getBookMan() {
        return bookMan;
    }

    public void setBookMan(String bookMan) {
        this.bookMan = bookMan;
    }

    public Double getDouBanScore() {
        return douBanScore;
    }

    public void setDouBanScore(Double douBanScore) {
        this.douBanScore = douBanScore;
    }

}