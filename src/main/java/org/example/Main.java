package org.example;

import org.example.analysis.Analyzer;
import org.example.converters.JsonConverter;
import org.example.entities.Ticket;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        JsonConverter jsonConverter = new JsonConverter();
        Analyzer analyzer = new Analyzer();

        List<Ticket> tickets = jsonConverter.convert();

        double difference = analyzer.difference(tickets);
        Map<String, String> minTime = analyzer.minTime(tickets);

        System.out.println(difference);
        System.out.println("TK - " + minTime.get("TK"));
        System.out.println("SU - " + minTime.get("SU"));
        System.out.println("S7 - " + minTime.get("S7"));
        System.out.println("BA - " + minTime.get("BA"));

        File file = new File("result.txt");

        try (FileWriter fw = new FileWriter(file);
             BufferedWriter bf = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bf))
        {
            out.println(difference);
            out.println("TK - " + minTime.get("TK"));
            out.println("SU - " + minTime.get("SU"));
            out.println("S7 - " + minTime.get("S7"));
            out.println("BA - " + minTime.get("BA"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
