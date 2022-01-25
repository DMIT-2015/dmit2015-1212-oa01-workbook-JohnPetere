package ca.nait.dmit.service;

import ca.nait.dmit.newPackage.AlbertaCovid19SummaryData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class AlbertaCovid19SummaryDataService {

    private List<AlbertaCovid19SummaryData> dataList = new ArrayList<>();

    public AlbertaCovid19SummaryDataService() throws IOException {
        try(var reader = new BufferedReader( new InputStreamReader(
                getClass().getResourceAsStream("/data/covid-19-alberta-statistics-summary-data.csv")))){

                final var delimeter = ",";

                String line;
                // skip the first line as it contains column nheadings
                reader.readLine();
                var dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                while((line = reader.readLine()) != null){
                    String[] values = line.split(delimeter, -1); // the -1 limit allows for any limit of fields and not discard trailing empty fields

                   //"","Date reported to Alberta Health" 0 ID
                    // ,"Number of lab tests","Cumulative number of lab tests", 2
                    // "Number of cases","Cumulative number of cases"
                    // ,"Active cases","Currently hospitalized","Currently in ICU",
                    // "Cumulative number of deaths",
                    // "Number of deaths","Number of variants of concern",
                    // "Percent positivity"
                    AlbertaCovid19SummaryData = new AlbertaCovid19SummaryData();
                }
        }
    }
}
