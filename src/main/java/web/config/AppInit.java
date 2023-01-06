package web.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// наследование от класса AbstractAnnotationConfig... предоставляет запуск приложения и 3 метода для переопределения
// служит как замена xml файлу конфигурации
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод, указывающий на класс конфигурации
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }


    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения jsp.
    // Подставляем свой класс конфигурации
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }


    /* Данный метод указывает url, на котором будет базироваться приложение
       Тут подставляем / - все http запросы от пользователя посылаем на Диспетчер сервлет
      */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}