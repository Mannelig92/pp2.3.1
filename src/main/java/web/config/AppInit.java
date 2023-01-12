package web.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

// ������������ �� ������ AbstractAnnotationConfig... ������������� ������ ���������� � 3 ������ ��� ���������������
// ������ ��� ������ xml ����� ������������
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // �����, ����������� �� ����� ������������
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }


    // ���������� ������������, � ������� �������������� ViewResolver, ��� ����������� ����������� jsp.
    // ����������� ���� ����� ������������
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    /* ������ ����� ��������� url, �� ������� ����� ������������ ����������
       ��� ����������� / - ��� http ������� �� ������������ �������� �� ��������� �������
      */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    //����� ��� ������ Thymeleaf � HTML5, �������� Patch � Delete
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    //����� ��� ������ Thymeleaf � HTML5, �������� Patch � Delete
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, true, "/*");
    }
}