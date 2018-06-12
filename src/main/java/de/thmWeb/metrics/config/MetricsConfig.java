package de.thmWeb.metrics.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.readytalk.metrics.StatsDReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsConfig.class);


    private static final int INTERVAL_FOR_LOG_REPORTER = 60;
    private static final int INTERVAL_FOR_STATSD_REPORTER = 10;

    @Value("${aggregator.metrics.statsDEndpoint:#{null}}")
    private String statsDEndpoint;
    @Value("${aggregator.metrics.statsDPort:#{null}}")
    private Integer statsDPort;


    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        final Slf4jReporter slf4jReporter = Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger(MetricsConfig.class))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .withLoggingLevel(Slf4jReporter.LoggingLevel.INFO)
                .build();
        slf4jReporter.start(INTERVAL_FOR_LOG_REPORTER, TimeUnit.SECONDS);

        if (statsDEndpoint != null && statsDPort != null) {
            LOG.info("Send metrics to {}:{}", statsDEndpoint, String.valueOf(statsDPort));
            StatsDReporter.forRegistry(metricRegistry)
                    .build(statsDEndpoint, statsDPort)
                    .start(INTERVAL_FOR_STATSD_REPORTER, TimeUnit.SECONDS);
        }
    }
}
