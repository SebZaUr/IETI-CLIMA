package org.adaschool.Weather;

import org.adaschool.Weather.controller.WeatherReportController;
import org.adaschool.Weather.data.WeatherApiResponse;
import org.adaschool.Weather.data.WeatherReport;
import org.adaschool.Weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherApplicationTests {
	@Autowired
	private WeatherReportService weatherReportService;

	@Autowired
	private WeatherReportController weatherReportController;

	@MockBean
	private RestTemplate restTemplate;

	private WeatherApiResponse sampleResponse;

	@BeforeEach
	void setUp() {
		sampleResponse = new WeatherApiResponse();
		WeatherApiResponse.Main main = new WeatherApiResponse.Main();
		main.setTemperature(25.0);
		main.setHumidity(60);
		sampleResponse.setMain(main);
	}

	@Test
	void getWeatherReport_validCoordinates_returnsWeatherReport() {
		when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(sampleResponse);

		WeatherReport result = weatherReportService.getWeatherReport(37.8267, -122.4233);

		assertEquals(0, result.getTemperature());
		assertEquals(91, result.getHumidity());
	}

	@Test
	void getWeatherReport_invalidLatitude_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			weatherReportService.getWeatherReport(100.0, -122.4233);
		});
	}

	@Test
	void getWeatherReport_invalidLongitude_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			weatherReportService.getWeatherReport(37.8267, -200.0);
		});
	}

	@Test
	void getWeatherReport_zeroCoordinates_returnsWeatherReport() {
		when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(sampleResponse);

		WeatherReport result = weatherReportService.getWeatherReport(0.0, 0.0);

		assertEquals(0.0, result.getTemperature());
		assertEquals(77.0, result.getHumidity());
	}



	@Test
	void contextLoads() {
	}

}
