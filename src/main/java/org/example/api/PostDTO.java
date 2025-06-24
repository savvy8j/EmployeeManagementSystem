package org.example.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostDTO {

    private Integer userId;

    private Integer id;

    private String title;

    private String body;
}
