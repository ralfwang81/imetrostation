package com.ralf.iautobiography;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class DataBaseConfiguration {
	@ConfigurationProperties(prefix = "c3p0.datasource")
	@Primary
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
	}
}
