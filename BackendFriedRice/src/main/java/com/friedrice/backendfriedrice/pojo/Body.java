package com.friedrice.backendfriedrice.pojo;

import lombok.Data;

@Data
public class Body {
    private Integer id;
    private Integer parent;
    private String body;
    private Short isLatest;
}
