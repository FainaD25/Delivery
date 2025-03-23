package org.example.delivery.component;

import org.example.delivery.entity.WeatherEntity;
import org.example.delivery.repository.WeatherRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import lombok.extern.slf4j.Slf4j;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class WeatherDataFetcher {
    private static final String WEATHER_API_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private static final List<String> TARGET_STATIONS = List.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu");

    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;

    public WeatherDataFetcher(WeatherRepository weatherDataRepository) {
        this.weatherRepository = weatherDataRepository;
        this.restTemplate = new RestTemplate();
    }

    // Fetch weather info from weather api url every hour at HH:15:00
    @Scheduled(cron = "0 15 * * * *")
    public void fetchWeatherData() {
        log.info("Fetching weather data at {}", System.currentTimeMillis());
        try {
            String xmlResponse = restTemplate.getForObject(WEATHER_API_URL, String.class);
            parseAndSaveWeatherData(xmlResponse);
        } catch (Exception e) {
            log.error("Failed to fetch weather data: " + e.getMessage());
        }
    }

    // Retrieve required weather details from the response and save them to the database
    private void parseAndSaveWeatherData(String xmlResponse) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

        NodeList stationNodes = document.getElementsByTagName("station");

        for (int i = 0; i < stationNodes.getLength(); i++) {
            Element stationElement = (Element) stationNodes.item(i);
            String stationName = getTextValue(stationElement, "name");

            if (TARGET_STATIONS.contains(stationName)) {
                WeatherEntity weatherEntity = new WeatherEntity();
                weatherEntity.setStationName(stationName);
                weatherEntity.setWmoCode(getTextValue(stationElement, "wmocode"));
                weatherEntity.setAirTemperature(Double.parseDouble(getTextValue(stationElement, "airtemperature")));
                weatherEntity.setWindSpeed(Double.parseDouble(getTextValue(stationElement, "windspeed")));
                weatherEntity.setWeatherPhenomenon(getTextValue(stationElement, "phenomenon"));
                weatherEntity.setTimestamp(LocalDateTime.now());

                weatherRepository.save(weatherEntity);
                log.info("Saved weather data for " + stationName);
            }
        }
    }

    // Helper function to retrieve text content from the tag of the element
    private String getTextValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        return nodeList.getLength() > 0 ? nodeList.item(0).getTextContent() : "";
    }
}
