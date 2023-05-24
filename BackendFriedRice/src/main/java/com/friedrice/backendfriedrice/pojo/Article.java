package com.friedrice.backendfriedrice.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Integer id;
    private String title;
    private Integer project;
    private Integer writer;
    private Short state;
    private String requirement;
    private Integer orderNumber;
    private Date lastChange;
}
