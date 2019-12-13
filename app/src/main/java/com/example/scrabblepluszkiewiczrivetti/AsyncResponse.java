package com.example.scrabblepluszkiewiczrivetti;

public interface AsyncResponse<T> {
    void processFinish(T result);
}
