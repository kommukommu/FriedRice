package com.friedrice.backendfriedrice.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Project {
    private Integer id;
    private String name;
    private String description;
    private Integer owner;
    private Date createDate;
}
