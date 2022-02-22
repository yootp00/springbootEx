package com.example.chapter6.file;

import com.example.chapter6.mapper.FileMapMapper;
import com.example.chapter6.mapper.UploadFileMapper;
import com.example.chapter6.model.BoardVO;
import com.example.chapter6.model.UploadFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class UploadFileService {

    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Autowired
    private FileMapMapper fileMapMapper;

    private final Path rootLocation;

    public UploadFileService(String uploadPath) {
        this.rootLocation = Paths.get(uploadPath);
    }

    public Path loadPath(String fileName){
        return rootLocation.resolve(fileName);
    }

    /**
     * 파일 읽기
     * @param fileName
     * @return
     * @throws Exception
     */
    public Resource loadAsResource(String fileName) throws Exception {
        try{
            if(fileName.toCharArray()[0] == '/') fileName = fileName.substring(1);
            Path file = loadPath(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()|| resource.isReadable()) {
                return resource;
            } else{
                throw new Exception("파일 없읍");
            }
        }catch(Exception e){
            throw new Exception("파일 없음");
        }
    }

    /**
     * 파일 목록 조회
     * @param id
     * @return
     */
    public List<UploadFileVO> selectFileByBoardId(int id){
        return uploadFileMapper.selectFileByBoardId(id);
    }
    /**
     * 파일 저장
     * @param file
     * @return
     * @throws Exception
     */
    public UploadFileVO saveFile(MultipartFile file) throws Exception {

        if(file.isEmpty()) throw new Exception("파일 저장 실패");

        String saveFileName = UploadFile.fileSave(rootLocation.toString(),file);
        log.info("saveFileName - {}",saveFileName);

        Resource resource = loadAsResource(saveFileName);

        UploadFileVO uploadFileVO = new UploadFileVO();
        uploadFileVO.setSaveFileName(saveFileName);
        uploadFileVO.setFileName(file.getOriginalFilename());
        uploadFileVO.setContentType(file.getContentType());
        uploadFileVO.setSize((int) resource.contentLength());
        uploadFileVO.setFilePath(rootLocation.toString().replace(File.separatorChar,'/')+File.separator + saveFileName);

        uploadFileMapper.insertUploadFile(uploadFileVO);

        return uploadFileVO;
    }

    public UploadFileVO load(int fileId) {
        return uploadFileMapper.selectFileById(fileId);
    }

    public Boolean deleteFileById(int id){
        UploadFileVO uploadFileVO = uploadFileMapper.selectFileById(id);
        if(uploadFileVO != null){
            File deleteFile = new File(uploadFileVO.getFilePath());
            if(deleteFile.exists()) deleteFile.delete();
            uploadFileMapper.deleteFileById(id);
            return true;
        } else{
            return false;
        }

    }
}
