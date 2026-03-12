package org.example.backend.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.backend.Entity.pojo.AccessRecord;
import org.example.backend.Entity.pojo.BackStage;
import org.example.backend.Entity.pojo.RestBean;
import org.example.backend.Entity.vo.BlogVO;

import java.util.List;

@Mapper
public interface BackStageMapper {
    @Select("SELECT * FROM  tb_backstage")
    List<BackStage> getback();

    @Select("select * from tb_backstage2")
    List<AccessRecord> getaccess();
}
