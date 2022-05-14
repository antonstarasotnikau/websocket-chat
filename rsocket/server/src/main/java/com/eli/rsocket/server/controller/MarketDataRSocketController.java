package com.eli.rsocket.server.controller;

import com.eli.rsocket.model.MarketData;
import com.eli.rsocket.model.MarketDataRequest;
import com.eli.rsocket.server.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class MarketDataRSocketController {

	private final MarketDataRepository marketDataRepository;

	@MessageMapping("currentMarketData")
	public Mono<MarketData> currentMarketData(MarketDataRequest marketDataRequest) {

		return marketDataRepository.getOne(marketDataRequest.getStock());
	}

	@MessageMapping("collectMarketData")
	public Mono<Void> collectMarketData(MarketData marketData) {

		marketDataRepository.add(marketData);
		return Mono.empty();
	}

	@MessageMapping("feedMarketData")
	public Flux<MarketData> feedMarketData(MarketDataRequest marketDataRequest) {

		return marketDataRepository.getAll(marketDataRequest.getStock());
	}
}
