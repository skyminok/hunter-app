package app;

import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Profile("h2")
@Configuration
@Import(EmbeddedDataSourceConfiguration.class)
public class H2DataSourceConfig {

}
