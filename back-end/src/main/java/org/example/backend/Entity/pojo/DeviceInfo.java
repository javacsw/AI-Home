package org.example.backend.Entity.pojo;

/**
 * @author shenxianfang
 * @Description:DeviceInfo
 * @date 2025/5/26
 * 设备信息类
 */
public class DeviceInfo {
    private String device;//设备号/设备名称，全局唯一
    private String type;//设备类型，digital为数字设备
    private DeviceData data;//设备内部相关数据

    public DeviceInfo() {}

    public DeviceInfo(String device, String type, DeviceData data) {
        this.device = device;
        this.type = type;
        this.data = data;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DeviceData getData() {
        return data;
    }

    public void setData(DeviceData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "device='" + device + '\'' +
                ", type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
