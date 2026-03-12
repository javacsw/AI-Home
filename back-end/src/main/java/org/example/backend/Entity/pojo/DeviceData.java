package org.example.backend.Entity.pojo;

/**
 * @author shenxianfang
 * @Description:Data
 * @date 2025/5/26
 * 设备内部相关参数数据
 */

public class DeviceData {
    private double value;//湿度百分比，范围 0-100%RH
    private String unit;//单位 (%RH)
    private String timestamp;//数据采集时间戳

    private String operator;//操作对象，buzzer为蜂鸣器

    private String feature;//对象特性，10000为10KHz

    private String action;//动作，on为打开，off为关闭

    private String duration;//持续时间，单位毫秒(ms)



    public DeviceData(){}

    public DeviceData(double value, String unit, String timestamp, String operator, String feature, String action, String duration) {
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.operator = operator;
        this.feature = feature;
        this.action = action;
        this.duration = duration;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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
        return "DeviceData{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", operator='" + operator + '\'' +
                ", feature='" + feature + '\'' +
                ", action='" + action + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
