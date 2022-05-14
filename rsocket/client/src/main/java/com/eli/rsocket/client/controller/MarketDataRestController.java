package com.eli.rsocket.client.controller;

import com.eli.rsocket.model.MarketData;
import com.eli.rsocket.model.MarketDataRequest;
import org.reactivestreams.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequiredArgsConstructor
public class MarketDataRestController {

	private final RSocketRequester rSocketRequester;
	private final Random random = new Random();

	@GetMapping(value = "/current/{stock}")
	public Publisher<MarketData> current(@PathVariable("stock") String stock) {

		return rSocketRequester.route("currentMarketData")
				.data(new MarketDataRequest(stock))
				.retrieveMono(MarketData.class);
	}

	@GetMapping(value = "/collect")
	public Publisher<Void> collect() {
		return rSocketRequester
				.route("collectMarketData")
				.data(getMarketData())
				.send();
	}

	@GetMapping(value = "/feed/{stock}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Publisher<MarketData> feed(@PathVariable("stock") String stock) {

		return rSocketRequester.route("feedMarketData")
				.data(new MarketDataRequest(stock))
				.retrieveFlux(MarketData.class);
	}

	private MarketData getMarketData() {

		return new MarketData("X", random.nextInt(10));
	}
}
