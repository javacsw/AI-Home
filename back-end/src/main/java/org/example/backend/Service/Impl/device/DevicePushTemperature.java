package org.example.backend.Service.Impl.device;

import com.alibaba.fastjson2.JSON;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.backend.Entity.pojo.DeviceInfo;
import org.example.backend.Entity.pojo.ResultData;

import org.example.backend.utils.ReqJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @author shenxianfang
 * @Description:DevicePushTemperature
 * @date 2025/5/26
 * 接收温度数据接口
 */
@WebServlet("/push_temperature")
@Service
public class DevicePushTemperature extends HttpServlet {
    @Autowired
    private TemperatureDataService temperatureDataService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求编码
        req.setCharacterEncoding("UTF-8");
        //设置返回编码
        resp.setContentType("application/json;charset=UTF-8");
        //获取前端传来的json数据
        DeviceInfo deviceInfo = ReqJsonUtils.parseJsonRequest(req, DeviceInfo.class);
//        System.out.println("当前时间："+new Date()+"；接收设备温度数据："+deviceInfo.toString());
        temperatureDataService.setData("当前时间："+new Date()+"；接收设备温度数据："+deviceInfo.toString());
//        System.out.println(data);
        //处理业务逻辑

        //设置返回数据
        ResultData resultData = new ResultData("ok", 200, "reserved");
        //将返回数据转换为json数据并返回json数据
        resp.getWriter().write(JSON.toJSONString(resultData));
    }

}
