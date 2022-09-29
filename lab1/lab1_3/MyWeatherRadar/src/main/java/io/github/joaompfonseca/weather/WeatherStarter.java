package io.github.joaompfonseca.weather;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.github.joaompfonseca.weather.ipma_client.IpmaCityForecast; //may need to adapt package name
import io.github.joaompfonseca.weather.ipma_client.IpmaService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    //todo: should generalize for a city passed as argument
    //private static final int CITY_ID_AVEIRO = 1010500;

    // create a logger
    private static Logger logger = LogManager.getLogger(WeatherStarter.class);

    public static void  main(String[] args ) {

        if (args.length < 1) {
            logger.info("Missing command line argument CITY_ID");
            System.out.println("USAGE: FILE.jar CITY_ID");
            System.out.println("    CITY_ID - int");
        }

        int cityId = Integer.parseInt(args[0]);
        logger.info("Requested weather forecast for city id " + cityId);

        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        logger.debug("Getting retrofit instance");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        logger.debug("Creating typed interface");
        IpmaService service = retrofit.create(IpmaService.class);
        // prepare the call to remote endpoint
        logger.debug("Preparing call to API");
        Call<IpmaCityForecast> callSync = service.getForecastForACity(cityId);

        try {
            logger.info("API call execute");
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            logger.info("API call success");
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                var firstDay = forecast.getData().listIterator().next();

                logger.info("Showing weather forecast");
                System.out.printf( "Weather forecast for %s %n" +
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
                logger.info("No results for this request");
                System.out.println( "No results for this request!");
            }
        } catch (Exception ex) {
            logger.error("API call failed");
            ex.printStackTrace();
        }

    }
}
