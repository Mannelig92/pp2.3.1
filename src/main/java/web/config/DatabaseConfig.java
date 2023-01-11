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

@Configuration //Указываем этот класс как класс конфигурации
@PropertySource("classpath:db.properties") //Аннотация к файлу настройки
//Позволяет использовать транзакции. Если ошибка, то происходит полный откат действий не допускающий ошибки.
@EnableTransactionManagement
@ComponentScan(value = "java") //Указание пакетов для сканирования
public class DatabaseConfig { //Конфигурация подключения к базе данных

    private Environment env; //Получение доступа к properties файлу и получение из него значений

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource getDataSource() { //+
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("db.driver"))); //Какой драйвер нужно использовать
        dataSource.setUrl(env.getProperty("db.url")); //Какой url нужно использовать
        dataSource.setUsername(env.getProperty("db.username")); //Какой ник нужно использовать
        dataSource.setPassword(env.getProperty("db.password")); //Какой пароль нужно использовать

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
    entityManagerFactory предоставляет возможность автоматического создания наших бинов и создания таблиц на основе
    наших бинов энтити. Что бы их поддерживать нужно проинизцилизировать спринг контекст энтитимэнэджер
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { //+
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean(); //Создаём объект
        em.setDataSource(getDataSource());  //Вызываем dataSource
        em.setPackagesToScan(env.getRequiredProperty("db.java.package")); //Пакет для сканирования
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //В качестве Jpa-провайдера используем Хибернэйт
        em.setJpaProperties(getHibernateProperties()); //Устанавливаем характеристики через отдельный метод

        return em;
    }

    private Properties getHibernateProperties() { //+-
        try {
            Properties properties = new Properties();
            //Получаем ресурсы hibernate.properties
            InputStream is = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(is); //Подгружаем их

            return properties; //Возвращаем подгруженные ресурсы
        } catch (IOException e) {
            System.out.println("Не найти файл hibernate.properties");
            throw new RuntimeException(e);
        }
    }
}
