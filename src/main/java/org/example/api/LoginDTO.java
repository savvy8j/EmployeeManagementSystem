package org.example.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    String username;
    String password;

//    public static void main(String[] args) {
//        MockitoExtension me = new MockitoExtension();
//    }
}
