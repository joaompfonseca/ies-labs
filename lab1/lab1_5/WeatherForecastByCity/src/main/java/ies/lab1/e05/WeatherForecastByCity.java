package ies.lab1.e05;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherForecastByCity {
    private static Logger logger = LogManager.getLogger(WeatherForecastByCity.class);

    public static void main(String[] args) {

        if (args.length < 1) {
            logger.info("Missing command line argument CITY_NAME");
            System.out.println("USAGE: FILE.jar CITY_NAME");
            System.out.println("    CITY_NAME - string");
        }

        String city = args[0];
        logger.info("Selected city " + city);

        IpmaApi ipma = new IpmaApi();

        IpmaCity cities = ipma.getCity();


        try {
            // Get city id that corresponds to city name
            Integer cityId = cities.getData().stream().filter(c -> c.getLocal().equals(city)).findFirst().get().getGlobalIdLocal();

            IpmaCityForecast forecast = ipma.getWeatherForecastByCity(cityId);
            if (forecast != null) {
                CityForecast firstDay = forecast.getData().listIterator().next();

                logger.info("Showing weather forecast");
                System.out.printf("Weather forecast for %s %n" +
                                "Max temp: %4.1fºC %n" +
                                "Min temp: %4.1fºC %n" +
                                "Precipitation: %4.1f%% %n" +
                                "Wind direction: %s %n",
                        firstDay.getForecastDate(),
                        Double.parseDouble(firstDay.getTMax()),
                        Double.parseDouble(firstDay.getTMin()),
                        Double.parseDouble(firstDay.getPrecipitaProb()),
                        firstDay.getPredWindDir());
            } else {
                logger.info("No forecasts found for " + city);
                System.out.println("No forecasts found for " + city);
            }
        }
        catch (NoSuchElementException e) {
            logger.info("No cities found matching " + city);
            System.out.println("No cities found matching " + city);
        }
    }
}
