package org.example.analysis;

import org.example.entities.Ticket;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Analyzer {
    public double difference (List<Ticket> tickets) {
        if (!tickets.isEmpty()) {
            long count = 0;
            long sum = 0;

            List<Ticket> vvoToTlvTickets = new ArrayList<>();

            for (Ticket ticket : tickets) {
                if ((ticket.getOrigin().equals("VVO") || ticket.getOrigin().equals("TLV")) &&
                        (ticket.getDestination().equals("VVO") || ticket.getDestination().equals("TLV"))) {
                    sum += ticket.getPrice();
                    count++;
                    vvoToTlvTickets.add(ticket);
                }
            }

            double average = sum / (double) count;

            double median;

            vvoToTlvTickets.sort(Comparator.comparingInt(ticket -> (int) ticket.getPrice()));

            if (vvoToTlvTickets.size() % 2 == 0) {
                int position = vvoToTlvTickets.size() / 2;

                median = (double) (vvoToTlvTickets.get(position - 1).getPrice() + vvoToTlvTickets.get(position).getPrice()) / 2;
            } else {
                int position = (int) Math.ceil(vvoToTlvTickets.size() / 2.0);

                median = (double) vvoToTlvTickets.get(position - 1).getPrice();
            }

            return average - median;
        }

        return 0;
    }

    public Map<String, String> minTime (List<Ticket> tickets) {
        long minTK = 0;
        long minSU = 0;
        long minS7 = 0;
        long minBA = 0;

        for (Ticket ticket : tickets) {
            if ((ticket.getOrigin().equals("VVO") || ticket.getOrigin().equals("TLV")) &&
                    (ticket.getDestination().equals("VVO") || ticket.getDestination().equals("TLV"))) {
                switch (ticket.getCarrier()) {
                    case "TK" -> {
                        if (minTK == 0) {
                            minTK = ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime());
                        } else {
                            minTK = Math.min(ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime()),
                                    minTK);
                        }
                    }
                    case "SU" -> {
                        if (minSU == 0) {
                            minSU = ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime());
                        } else {
                            minSU = Math.min(ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime()),
                                    minSU);
                        }
                    }
                    case "S7" -> {
                        if (minS7 == 0) {
                            minS7 = ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime());
                        } else {
                            minS7 = Math.min(ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime()),
                                    minS7);
                        }
                    }
                    case "BA" -> {
                        if (minBA == 0) {
                            minBA = ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime());
                        } else {
                            minBA = Math.min(ChronoUnit.MINUTES.between(ticket.getDepartureTime(), ticket.getArrivalTime()),
                                    minBA);
                        }
                    }
                }
            }
        }

        Map<String, String> minTime = new HashMap<>();
        minTime.put("TK", String.format("%d:%02d", minTK / 60, minTK % 60));
        minTime.put("SU", String.format("%d:%02d", minSU / 60, minSU % 60));
        minTime.put("S7", String.format("%d:%02d", minS7 / 60, minS7 % 60));
        minTime.put("BA", String.format("%d:%02d", minBA / 60, minBA % 60));

        return minTime;
    }
}
