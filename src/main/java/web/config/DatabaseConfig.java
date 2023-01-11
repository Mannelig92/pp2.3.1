package web.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Configuration //��������� ���� ����� ��� ����� ������������
@PropertySource("classpath:db.properties") //��������� � ����� ���������
//��������� ������������ ����������. ���� ������, �� ���������� ������ ����� �������� �� ����������� ������.
@EnableTransactionManagement
@ComponentScan(value = "java") //�������� ������� ��� ������������
public class DatabaseConfig { //������������ ����������� � ���� ������

    private Environment env; //��������� ������� � properties ����� � ��������� �� ���� ��������

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() { //+
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver"))); //����� ������� ����� ������������
        dataSource.setUrl(env.getProperty("db.url")); //����� url ����� ������������
        dataSource.setUsername(env.getProperty("db.username")); //����� ��� ����� ������������
        dataSource.setPassword(env.getProperty("db.password")); //����� ������ ����� ������������

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() { //+
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        manager.setDataSource(getDataSource());

        return manager;
    }

    /*
    entityManagerFactory ������������� ����������� ��������������� �������� ����� ����� � �������� ������ �� ������
    ����� ����� ������. ��� �� �� ������������ ����� ������������������� ������ �������� ��������������
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { //+
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); //������ ������
        em.setDataSource(getDataSource());  //�������� dataSource
        em.setPackagesToScan(env.getRequiredProperty("db.java.package")); //����� ��� ������������
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //� �������� Jpa-���������� ���������� ���������
        em.setJpaProperties(getHibernateProperties()); //������������� �������������� ����� ��������� �����

        return em;
    }

    private Properties getHibernateProperties() { //+-
        try {
            Properties properties = new Properties();
            //�������� ������� hibernate.properties
            InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(is); //���������� ��

            return properties; //���������� ������������ �������
        } catch (IOException e) {
            System.out.println("�� ����� ���� hibernate.properties");
            throw new RuntimeException(e);
        }
    }
}
