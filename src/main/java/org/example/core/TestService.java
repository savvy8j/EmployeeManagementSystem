package org.example.core;

import lombok.extern.slf4j.Slf4j;
import org.example.api.TestDTO;

@Slf4j
public class TestService {

    public void printInfo(TestDTO testDTO) {

        log.info(testDTO.toString());
    }


}
