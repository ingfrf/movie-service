package com.enmivida.service.controller;

import com.enmivida.service.client.MoviesInfoRestClient;
import com.enmivida.service.client.ReviewsRestClient;
import com.enmivida.service.domain.Movie;
import com.enmivida.service.domain.MovieInfo;
import com.enmivida.service.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
public class MoviesController {

    private final MoviesInfoRestClient moviesInfoRestClient;
    private final ReviewsRestClient reviewsRestClient;

    @GetMapping("/{id}")
    public Mono<Movie> retrieveMovieById(@PathVariable("id") String movieId) {
        return moviesInfoRestClient.retrieveMovieInfo(movieId)
                .flatMap(movieInfo -> {
                    Mono<List<Review>> reviewListMono = reviewsRestClient
                            .retrieveReviews(movieId)
                            .collectList()
                            .log()
                            ;
                    return reviewListMono.map(reviews -> new Movie(movieInfo, reviews));
                });
    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> retrieveMovieInfos() {
        return moviesInfoRestClient.retrieveMovieInfoStream()
                ;
    }
}
