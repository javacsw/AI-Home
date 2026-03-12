package org.example.backend.Entity.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class GroupMember {
    private Integer id,groupId, userId;
    private Date time;
}