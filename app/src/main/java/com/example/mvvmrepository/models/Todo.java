package com.example.mvvmrepository.models;

public class Todo {

    private int id;
    private String title;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted( boolean completed ) {
        this.completed = completed;
    }
}
