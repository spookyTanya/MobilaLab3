package com.example.test3;

public class TaskModel {
    private String name;
    private String type;

    public TaskModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
