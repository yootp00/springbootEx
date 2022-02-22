package com.example.chapter6.mapper;

import com.example.chapter6.model.UploadFileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    void insertUploadFile(UploadFileVO uploadFileVO);

    List<UploadFileVO> selectFileByBoardId(int id);

    UploadFileVO selectFileById(int fileId);

    void deleteFileById(int id);
}
