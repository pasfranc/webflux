package io.spring.workshop.stockquotes;

import static java.time.Duration.ofMillis;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class IntegerHandler {

	private final Flux<Integer> integerStream;

	public IntegerHandler(IntegerGenerator integerGenerator) {
		this.integerStream = integerGenerator.fetchIntegerStream(ofMillis(1000)).share();
	}
    
	public Mono<ServerResponse> streamQuotes(ServerRequest request) {
      return ok()
              .contentType(APPLICATION_STREAM_JSON)
              .body(this.integerStream, Integer.class);
    }
}
