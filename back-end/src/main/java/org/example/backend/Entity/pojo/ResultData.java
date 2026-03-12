package org.example.backend.Entity.pojo;

/**
 * @author shenxianfang
 * @Description:ResultData
 * @date 2025/5/26
 * 封装接口的响应数据
 */

public class ResultData {
    private String status;
    private Integer code;
    private String message;
    private String action;//操作类型（on-开，off-关）
    private String duration;//蜂鸣声持续时间
    
    private Object data;//数据

    public ResultData(){}

    //只有状态、代码、消息的构造函数
    public ResultData(String status, Integer code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    public ResultData(String status, Integer code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    //全参数的构造函数
    public ResultData(String status, Integer code, String message, String action, String duration) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.action = action;
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "status='" + status + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", action='" + action + '\'' +
                ", duration='" + duration + '\'' +
                ", data=" + data +
                '}';
    }
}
