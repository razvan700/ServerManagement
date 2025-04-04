package com.jetbrains.servermanager.enumeration;



public enum Status {
    STATUS_UP("SERVER_UP"),
    STATUS_DOWN("SERVER_DOWN");
    private String status;

    Status(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
}
