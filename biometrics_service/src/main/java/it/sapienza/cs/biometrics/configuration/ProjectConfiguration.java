package it.sapienza.cs.biometrics.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration

@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "it.sapienza.cs.biometrics" })
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"it.sapienza.cs.biometrics" })

public class ProjectConfiguration implements EnvironmentAware{
	
	private Environment env;
	
	@Primary
	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create().driverClassName(env.getProperty("spring.datasource.driver-class-name"))
				.url(env.getProperty("spring.datasource.url")).username(env.getProperty("spring.datasource.username"))
				.password(env.getProperty("spring.datasource.password")).build();
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource())//
				.packages("it.sapienza.cs.biometrics.model") //$NON-NLS-1$
				.persistenceUnit("biometric") //$NON-NLS-1$
				.build();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(final EntityManagerFactory customerEntityManager) {
		return new JpaTransactionManager(customerEntityManager);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env=environment;
		
	}

}