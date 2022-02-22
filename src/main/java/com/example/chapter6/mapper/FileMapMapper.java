package com.example.chapter6.mapper;

import com.example.chapter6.model.FileMapVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapMapper {

    void insertFileMap(FileMapVO fileMapVO);
}
