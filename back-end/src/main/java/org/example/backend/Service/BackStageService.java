package org.example.backend.Service;

import org.example.backend.Entity.pojo.AccessRecord;
import org.example.backend.Entity.pojo.BackStage;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.vo.BlogVO;

import java.util.List;


public interface BackStageService {
    List<BackStage> getback();

    List<AccessRecord> getaccess();
}
