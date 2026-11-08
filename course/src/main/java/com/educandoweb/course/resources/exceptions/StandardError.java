package com.educandoweb.course.resources.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;


    private Instant timetamp;
    private Integer status;
    private String error;
    private String path;
    private String message;

    public StandardError() {
    }

    public StandardError(Instant timetamp, Integer status, String error, String message, String path) {
        this.timetamp = timetamp;
        this.path = path;
        this.error = error;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimetamp() {
        return timetamp;
    }

    public void setTimetamp(Instant timetamp) {
        this.timetamp = timetamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /*
    Nesse caso não se usa Hascode e Equals porque em momento nenhum vai se fazer nescessario realizar
    a comparação enntre erros de encotrar um ID
     */


}
