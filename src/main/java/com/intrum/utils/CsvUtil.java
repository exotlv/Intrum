package com.intrum.utils;

import com.intrum.models.UserData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvUtil {

    private static final String CSV_PATH = "src/test/java/userData.csv";

    public static List<UserData> readFromCsv() {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_PATH))) {
            CsvToBean<UserData> csvToBean = new CsvToBeanBuilder<UserData>(reader)
                    .withType(UserData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
