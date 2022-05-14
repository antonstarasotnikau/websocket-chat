package com.eli.rsocket.client.configuration;

import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.protobuf.ProtobufEncoder;
import org.springframework.boot.rsocket.messaging.RSocketStrategiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.protobuf.ProtobufDecoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class ClientConfiguration {

	@Bean
	public RSocketRequester getRSocketRequester() {
		RSocketRequester.Builder builder = RSocketRequester.builder();
		return builder.rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.tcp("localhost", 7000);
	}

	@Bean
	public RSocketStrategies protobufRSocketStrategy() {

		return RSocketStrategies.builder()
				.encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
				.decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
				.build();
	}
}
