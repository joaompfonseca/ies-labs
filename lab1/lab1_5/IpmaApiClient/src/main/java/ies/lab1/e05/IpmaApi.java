package ies.lab1.e05;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class IpmaApi {

    private Logger logger;
    private IpmaService service;

    public IpmaApi() {
        logger = LogManager.getLogger(IpmaApi.class);
        // get a retrofit instance, loaded with the GSon lib to convert JSON into objects
        logger.debug("Getting retrofit instance");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // create a typed interface to use the remote API (a client)
        logger.debug("Creating typed interface");
        this.service = retrofit.create(IpmaService.class);
    }

    public IpmaCityForecast getWeatherForecastByCity(int cityId) {
        // prepare the call to remote endpoint
        logger.debug("Preparing call to API");
        Call<IpmaCityForecast> callSync = service.getForecastForACity(cityId);

        IpmaCityForecast forecast = null;
        try {
            logger.info("API call execute");
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            logger.info("API call success");
            forecast = apiResponse.body();
        } catch (Exception ex) {
            logger.error("API call failed");
            ex.printStackTrace();
        }
        return forecast;
    }

    public IpmaCity getCity() {
        // prepare the call to remote endpoint
        logger.debug("Preparing call to API");
        Call<IpmaCity> callSync = service.getCity();

        IpmaCity cities = null;
        try {
            logger.info("API call execute");
            Response<IpmaCity> apiResponse = callSync.execute();
            logger.info("API call success");
            cities = apiResponse.body();
        } catch (Exception ex) {
            logger.error("API call failed");
            ex.printStackTrace();
        }
        return cities;
    }
}
