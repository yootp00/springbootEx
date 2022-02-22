package com.example.chapter6.file;

import com.example.chapter6.mapper.FileMapMapper;
import com.example.chapter6.model.FileMapVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FileMapService {
    private FileMapMapper fileMapMapper;

    public FileMapService(FileMapMapper fileMapMapper) {
        this.fileMapMapper = fileMapMapper;
    }

    public void insertFileMap(FileMapVO fileMapVO){
        fileMapMapper.insertFileMap(fileMapVO);
    }
}
