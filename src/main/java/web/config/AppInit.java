package web.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

    //Нужно для работы Thymeleaf с HTML5, методами Patch и Delete
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    //Нужно для работы Thymeleaf с HTML5, методами Patch и Delete
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
}