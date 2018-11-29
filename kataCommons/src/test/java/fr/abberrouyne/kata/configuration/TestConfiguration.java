package fr.abberrouyne.kata.configuration;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A configuration bean for tests purposes.
 * 
 * @author abberrouyne
 *
 */
@Configuration
@ComponentScan(basePackages = { "fr.abberrouyne.kata" }, excludeFilters = @Filter(type = FilterType.ANNOTATION, value = Configuration.class))
@EnableTransactionManagement(proxyTargetClass = false)
@EnableJpaRepositories(transactionManagerRef = "myTxManager", basePackages = "fr.abberrouyne.kata.repository", entityManagerFactoryRef = "myEmf")
public class TestConfiguration {

    /**
     * Builds a datasource. VelocityContext
     * 
     * @return the dataSource.
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                                            .build();
    }

    /**
     * Builds the persistence unit manager.
     * 
     * @param dataSource
     *            the datasource.
     * @return the persistence unit manager.
     */
    @Bean
    public DefaultPersistenceUnitManager persistenceUnitManager() {
        DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setPersistenceXmlLocation("classpath*:/META-INF/persistence.xml");
        defaultPersistenceUnitManager.setDefaultDataSource(dataSource());
        return defaultPersistenceUnitManager;
    }

    /**
     * Builds the persistence unit manager.
     * 
     * @param dataSource
     *            the datasource.
     * @return the persistence unit manager.
     */
    @Bean
    public HibernateJpaVendorAdapter jpaAdapter() {
        HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setDatabasePlatform(H2Dialect.class.getName());
        jpaAdapter.setGenerateDdl(true);
        jpaAdapter.setShowSql(true);
        return jpaAdapter;
    }

    /**
     * Builds the persistence unit manager.
     * 
     * @param dataSource
     *            the datasource.
     * @return the persistence unit manager.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean myEmf() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPersistenceUnitManager(persistenceUnitManager());
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaAdapter());
        localContainerEntityManagerFactoryBean.getJpaPropertyMap()
                                              .put(Environment.HBM2DDL_AUTO, "create");
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Builds the persistence unit manager.
     * 
     * @param dataSource
     *            the datasource.
     * @return the persistence unit manager.
     */
    @Bean
    public PlatformTransactionManager myTxManager() {
        return new JpaTransactionManager(myEmf().getObject());
    }
}
