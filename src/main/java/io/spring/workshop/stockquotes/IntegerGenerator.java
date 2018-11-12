package io.spring.workshop.stockquotes;

import java.time.Duration;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class IntegerGenerator {

	private Integer myInteger = new Integer(0);

	/**
	 * Bootstraps the generator with tickers and initial prices
	 */
	public IntegerGenerator() {
	  myInteger++;
	}


	public Flux<Integer> fetchIntegerStream(Duration period) {

		// We want to emit quotes with a specific period;
		// to do so, we create a Flux.interval
		return Flux.interval(period)
				// In case of back-pressure, drop events
				.onBackpressureDrop()
				// For each tick, generate a list of quotes
				.map(this::increment)
				// "flatten" that List<Quote> into a Flux<Quote>
				.log("io.spring.workshop.stockquotes");
	}

	/*
	 * Create quotes for all tickers at a single instant.
	 */
	private Integer increment(long interval) {
		return myInteger++;
	}

}
