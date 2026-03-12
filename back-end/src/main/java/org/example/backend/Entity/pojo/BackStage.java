package org.example.backend.Entity.pojo;

import lombok.Data;

@Data//自动getter setter

public class BackStage {
    private Integer id;
    private String username,role,lastLogin,status;


    public BackStage(){

    }


}
