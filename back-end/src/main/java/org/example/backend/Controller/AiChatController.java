package org.example.backend.Controller;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import org.example.backend.Entity.pojo.DeviceInfo;
import org.example.backend.Entity.pojo.RestBean;
//import org.example.backend.Service.Analyse;
//import org.example.backend.Service.Analyse;
import org.example.backend.Entity.pojo.ResultData;
import org.example.backend.Service.Analyse;
import org.example.backend.Service.Impl.device.HumidityService;
import org.example.backend.Service.Impl.device.LightService;
import org.example.backend.Service.Impl.device.TemperatureDataService;
import org.example.backend.Service.StorageService;
import org.example.backend.utils.ReqJsonUtils;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/ai")
public class AiChatController {

    /**
     * spring ai 自动装配的 可以直接注入使用
     */

    @Resource
    StorageService storageService;
    @Resource
    Analyse analyse;
    @Resource
    private OpenAiChatModel openAiChatModel;
    @Resource
    private OpenAiImageModel openAiImageModel;
    @Resource
    HumidityService humidityService;
    @Resource
    LightService lightService;
    @Resource
    TemperatureDataService temperatureDataService;
    @Resource
    private HttpServletRequest req;
    @Resource
    HttpServletResponse resp;

    @GetMapping("/chat")
    public RestBean<String> chat(@RequestParam(value = "message") String message) {
        String called=openAiChatModel.call(message);
//        System.out.println(called);
        return RestBean.success("cg",called);
    }

    @RequestMapping(value = "/image")
    public RestBean<String> image(@RequestParam(value = "message") String image){
        //chat是在文件里面配置的，二者同时配置优先使用代码里面的
        ImageResponse imageResponse=openAiImageModel.call(new ImagePrompt(image, OpenAiImageOptions.builder().
                withQuality("hd")//高清图片
                .withN(1)//1张图片(这个模型只能画一个)
                .withHeight(1024)//
                .withWidth(1024).build()));
        System.out.println(imageResponse.getResult().getOutput().getUrl());
        String url=imageResponse.getResult().getOutput().getUrl();

        return  RestBean.success("cg",url);
    }

    @PostMapping("/analyse")
    public RestBean<String> analyse(
                                    @RequestParam MultipartFile file) throws IOException {
       String fileType = file.getContentType()!=null &&file.getContentType().startsWith("image/")?"image" :"video";
       String url = storageService.uploadFile(file,fileType);
        String result = analyse.AnalyseImag(url);
       return RestBean.success("cg",result);
    }

    @PostMapping("/push_humidity")
    public RestBean<String> getData1()throws ServletException, IOException{
        String Data1= humidityService.getData();
        System.out.println(Data1);
        return RestBean.success("cg",Data1);
    }
    @PostMapping("/push_light_intensity")
    public RestBean<String> getData2()throws ServletException, IOException{
        String Data2= lightService.getData();
        System.out.println(Data2);
        return RestBean.success("cg",Data2);
    }
    @PostMapping("/push_temperature")
    public RestBean<String> getData3()throws ServletException, IOException{
        String Data3=temperatureDataService.getData();
        System.out.println(Data3);
        return RestBean.success("cg",Data3);
    }

//    @PostMapping("/push_humidity")
//    public  void getHumidityData() {
//        System.out.println(this.humidityService.getData());
//
////        //设置请求编码
////        req.setCharacterEncoding("UTF-8");
////        //设置返回编码
////        resp.setContentType("application/json;charset=UTF-8");
////        //获取前端传来的json数据
////        DeviceInfo deviceInfo = ReqJsonUtils.parseJsonRequest(req,DeviceInfo.class);
////        if (deviceInfo == null) {
////            ResultData resultData = new ResultData("error", 400, "deviceInfo is null");
////            resp.getWriter().write(JSON.toJSONString(resultData));
////            return resultData;
////        }
//////        System.out.println("当前时间："+new Date()+"；接收环境湿度数据："+deviceInfo.toString());
////        humidityService.setData("当前时间："+new Date()+"；接收环境湿度数据："+deviceInfo.toString());
////
////        //处理业务逻辑
////
////        //设置返回数据
////        ResultData resultData = new ResultData("ok",200,"reserved",deviceInfo);
////        System.out.println(resultData);
////        //将返回数据转换为json数据并返回json数据
////         resp.getWriter().write(JSON.toJSONString(resultData));
////
////        return resultData;
//
//    }




}
