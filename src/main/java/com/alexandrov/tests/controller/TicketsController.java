package com.alexandrov.tests.controller;

import com.alexandrov.tests.entity.City;
import com.alexandrov.tests.entity.RouteData;
import com.alexandrov.tests.entity.TicketInfo;
import com.alexandrov.tests.util.Data;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TicketsController {

    @GetMapping("tickets/getAll")
    @ApiOperation("Список всех билетов")
    public HashMap<RouteData, List<TicketInfo>> showAllTicket() {
        Data data = new Data();
        return data.createData();
    }

    @GetMapping("tickets/getDepartures")
    @ApiOperation("Информация о всех городах и отправлений")
    public List<List<City>> showAllDepartureCities() {

        Data data = new Data();

        HashMap<RouteData, List<TicketInfo>> allData = data.createData();

        return allData.values()
                .stream()
                .flatMap(List::stream)
                .map(TicketInfo::getDeparture)
                .collect(Collectors.toList());
    }

    @GetMapping("tickets/getRailway")
    @ApiOperation("Список городов и станций")
    public List<List<City>> showAllRailway() {

        Data data = new Data();

        HashMap<RouteData, List<TicketInfo>> allData = data.createData();

        return allData.values()
                .stream()
                .flatMap(List::stream)
                .map(TicketInfo::getArrival)
                .collect(Collectors.toList());
    }

    @GetMapping("/tickets/filterPrice")
    @ApiOperation("Получение билетов по фильтру")
    public List<TicketInfo> getFilteredTicketsByPrice(@RequestParam int minPrice, int maxPrice) {
        return getTicketInfos(minPrice, maxPrice);
    }

    private List<TicketInfo> getTicketInfos(@RequestParam int minPrice, int maxPrice) {
        Data data = new Data();

        HashMap<RouteData, List<TicketInfo>> allData = data.createData();

        return allData.values()
                .stream()
                .flatMap(List::stream)
                .filter(p -> Integer.parseInt(p.getPrice()) >= minPrice && Integer.parseInt(p.getPrice()) <= maxPrice)
                .collect(Collectors.toList());
    }

    @PostMapping("/tickets/addTicket")
    @ApiOperation("Добавление билета")
    private HashMap<RouteData, List<TicketInfo>> addInBasket(@RequestBody @Validated Data.CityTicketData cityTicketData) {
        Data data = new Data();
        List<TicketInfo> ticketInfoList = data.createTicket(cityTicketData);
        HashMap<RouteData, List<TicketInfo>> listOdTickets = data.createData();
        listOdTickets.put(cityTicketData.getRouteData(), ticketInfoList);
        return listOdTickets;
    }
}