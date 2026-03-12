package org.example.backend.Service.Impl;

import jakarta.annotation.Resource;
import org.example.backend.Entity.pojo.AccessRecord;
import org.example.backend.Entity.pojo.BackStage;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.vo.BlogVO;
import org.example.backend.Mapper.BackStageMapper;
import org.example.backend.Mapper.UserMapper;
import org.example.backend.Service.BackStageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackStageImpl implements BackStageService {

    @Resource
    BackStageMapper backStageMapper;

    @Override
    public List<BackStage> getback() {
        return backStageMapper.getback();
    }

    @Override
    public List<AccessRecord> getaccess() {
        return backStageMapper.getaccess();
    }

}
