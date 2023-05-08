package com.example.demo.model;


import com.example.demo.crud.GenericCrud;
import com.example.demo.util.Column;

public class BaseModel extends GenericCrud {
    @Column(name = "id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
