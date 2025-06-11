package org.example.resources;


import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.example.api.TestDTO;
import org.example.core.TestService;

@Slf4j
@Path("/test")
public class TestResource {

    private final TestService testService;

    public TestResource(TestService testService) {
        this.testService = testService;
    }

    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public String getData(@PathParam("name") String name, String data) {
       log.info(data);
        return "Hello" + name;
    }

//    @Timed
    @Metered
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TestDTO postData(@Valid TestDTO dto) {
        log.info(dto.toString());
        testService.printInfo(dto);
        return dto;
    }


    public static void main(String[] args) throws JsonProcessingException {
        String json = """
                {
                "full_name": "John"
                }
                """;

        ObjectMapper mapper = new ObjectMapper();
        //deserialisation
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        TestDTO testDTO = mapper.readValue(json, TestDTO.class);
        System.out.println(testDTO);

        //serialisation
        TestDTO abc = TestDTO.builder()
                .name("ABC")
                .build();
        String s = mapper.writeValueAsString(abc);
        System.out.println(s);


    }
}
