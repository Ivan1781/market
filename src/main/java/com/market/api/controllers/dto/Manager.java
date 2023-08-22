package com.market.api.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Manager implements Serializable {
    @JsonProperty("name")
    private String name;

    public Manager(){}
    public Manager(String name){
        this.name=name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Manager{" +
                "name='" + name + '\'' +
                '}';
    }
}
