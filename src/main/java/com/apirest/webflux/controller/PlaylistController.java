package com.apirest.webflux.controller;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.MediaType;
import reactor.util.function.Tuple2;
import java.time.Duration;

//@RestController
public class PlaylistController<value, produces> {

    @Autowired
    PlaylistService service;

    @GetMapping (value="/playlist")
    public Flux<Playlist> getPlaylist(){
        return service.findAll();
    }

    @GetMapping (value="/playlist/{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping(value="/playlist")
    public Mono<Playlist> save(@RequestBody Playlist playlist){
        return service.save(playlist);
    }

    @GetMapping(value="/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents(){

        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<Playlist> events = service.findAll();
        System.out.print("Passou aqui events");
        return Flux.zip(interval, events);
    }
}
