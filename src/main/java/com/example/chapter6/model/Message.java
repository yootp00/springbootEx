package com.example.chapter6.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {

    private String message = "";
    private String href = "/";

    public Message(String message, String href) {
        this.message = message;
        this.href = href;
    }

}
