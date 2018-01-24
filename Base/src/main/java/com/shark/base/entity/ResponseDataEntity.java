package com.shark.base.entity;

public class ResponseDataEntity<Data> extends ResponseEntity {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
