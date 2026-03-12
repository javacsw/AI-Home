package org.example.backend.Entity.pojo;

import lombok.Data;

@Data

public class AccessRecord {
    private Integer id;
    private String username,ip,operation,time,status;

    public AccessRecord(){

    }

}
