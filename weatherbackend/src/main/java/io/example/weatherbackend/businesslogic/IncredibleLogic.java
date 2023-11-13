package io.example.weatherbackend.businesslogic;

import java.time.Instant;
import java.util.Date;

import io.example.weatherbackend.model.GeneralOutlook;

public class IncredibleLogic {

    public static GeneralOutlook generateGeneralOutlook() {
        GeneralOutlook generalOutlook = new GeneralOutlook();
        generalOutlook.setCity("Weimar");
        generalOutlook.setDate(Date.from(Instant.now()));
        generalOutlook.setState("Germany");
        generalOutlook.setWeatherStation("BestStationInTown");
        return generalOutlook;
    }
}
