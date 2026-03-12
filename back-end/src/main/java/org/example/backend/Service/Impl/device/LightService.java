package org.example.backend.Service.Impl.device;

/*
 * @Auther:fz
 * @Date:2025/6/24
 * @Description:
 */

import org.springframework.stereotype.Service;

@Service
public class LightService {
    private String data;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
