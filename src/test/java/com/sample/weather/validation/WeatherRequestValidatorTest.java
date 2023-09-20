package com.sample.weather.validation;

import com.sample.weather.exception.FieldValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WeatherRequestValidatorTest {

    @InjectMocks
    WeatherRequestValidator weatherRequestValidator;

    @BeforeEach
    public void beforeEach() {
        weatherRequestValidator.loadKeys();
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("", "uk", "123456789"),
                Arguments.of("London", "", "123456789"),
                Arguments.of("London", "uk", ""),
                Arguments.of(null, "uk", "123456789"),
                Arguments.of("Colombo", null, "123456789"),
                Arguments.of("Colombo", "Sri lanka", null)
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void validate(String city, String country, String apiKey) {
        assertThrows(FieldValidationException.class, () -> weatherRequestValidator.validate(city, country, apiKey));
    }

    private static Stream<Arguments> apiKeyDataProvider() {
        return Stream.of(
                Arguments.of("London", "uk", ""),
                Arguments.of("London", "uk", null),
                Arguments.of("London", "uk", "  "),
                Arguments.of("London", "uk", "1234")
        );
    }

    @ParameterizedTest
    @MethodSource("apiKeyDataProvider")
    void validateApiKey(String city, String country, String apiKey) {
        assertEquals(weatherRequestValidator.getKeys().keySet().size(), 5);
        assertThrows(FieldValidationException.class, () -> weatherRequestValidator.validate(city, country, apiKey));
    }
}