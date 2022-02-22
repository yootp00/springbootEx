package com.example.chapter6.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FileMapVO {

    private int id;
    //board 테이블의 id
    private int boardId;
    //upload_file 테이블의 id
    private int fileId;
}
