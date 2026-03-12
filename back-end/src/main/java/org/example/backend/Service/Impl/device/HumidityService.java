package org.example.backend.Service.Impl.device;

import org.springframework.stereotype.Service;

/*
 * @Auther:fz
 * @Date:2025/6/24
 * @Description:
 */
@Service
public class HumidityService {
    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
