package org.example.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.entities.Ticket;
import org.example.entities.Tickets;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonConverter {
    public List<Ticket> convert() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File("D://ABC//tickets.json");
        Tickets ticketsObj = objectMapper.readValue(file, new TypeReference<>(){});

        return ticketsObj.tickets;
    }
}
