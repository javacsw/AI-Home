package org.example.backend.Controller;

import jakarta.annotation.Resource;
import org.example.backend.Entity.pojo.AccessRecord;
import org.example.backend.Entity.pojo.BackStage;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.vo.BlogVO;
import org.example.backend.Service.BackStageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/back")
public class BackStageController {
    @Resource
    BackStageService backStageService;

    @GetMapping("getback")
    public RestBean<List<BackStage>> getback()
    {
        return RestBean.success("获取成果",backStageService.getback());
    }

    @GetMapping("getaccess")
    public RestBean<List<AccessRecord>> getaccess()
    {
        return RestBean.success("获取成功",backStageService.getaccess());
    }

}
