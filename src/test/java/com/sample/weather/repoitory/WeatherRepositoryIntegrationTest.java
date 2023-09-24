package com.sample.weather.repoitory;

import com.sample.weather.exception.ServerException;
import com.weather.model.InlineResponse200Weather;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application.test.yml" })
public class WeatherRepositoryIntegrationTest {

    @Autowired
    WeatherRepository weatherRepository;

    private static ClientAndServer mockServer;

    @BeforeEach
    public void startServer() {
        mockServer = startClientAndServer(9005);
    }

    @AfterEach
    public void stopServer() {
        mockServer.stop();
    }

    public void resetState() {
        mockServer.reset();
    }

    @Test
    public void testSuccessPath200(){
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/data/2.5/weather")
        ).respond(
                response()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getSampleSuccessResponse())
        );
        var resp = weatherRepository.getWeather("sample", "sample2");
        assertEquals("moderate rain", resp.getWeather().stream()
                .map(InlineResponse200Weather::getDescription)
                .findAny().get());
    }

    private String getSampleSuccessResponse(){
        return "{\n" +
                "   \"coord\":{\n" +
                "      \n" +
                "   },\n" +
                "   \"weather\":[\n" +
                "      {\n" +
                "         \"id\":501,\n" +
                "         \"main\":\"Rain\",\n" +
                "         \"description\":\"moderate rain\",\n" +
                "         \"icon\":\"10d\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"base\":\"stations\",\n" +
                "   \"main\":{\n" +
                "      \n" +
                "   },\n" +
                "   \"visibility\":10000,\n" +
                "   \"wind\":{\n" +
                "      \n" +
                "   },\n" +
                "   \"rain\":{\n" +
                "      \"1h\":3.16\n" +
                "   },\n" +
                "   \"clouds\":{\n" +
                "      \"all\":100\n" +
                "   },\n" +
                "   \"dt\":1661870592,\n" +
                "   \"sys\":{\n" +
                "      \n" +
                "   }\n" +
                "}";
    }

    @Test()
    public void testInternalError500(){
        mockServer.when(
                request()
                        .withMethod("GET")
                        .withPath("/data/2.5/weather")
        ).respond(
                response()
                        .withStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\":\"sample\"}")
        );
        assertThrows(ServerException.class, () ->
                weatherRepository.getWeather( "sample", "sample2"));
    }
}
