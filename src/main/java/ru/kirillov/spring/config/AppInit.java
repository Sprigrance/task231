package ru.kirillov.spring.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//Этот класс используем как замену реализации через web.xml

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    /* Метод, указывающий на класс конфигурации Hibernate */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
//        return new Class<?>[]{
//                DataBaseConfig.class
//        };
    }

    /* Метод, указывающий на класс инициализации ViewResolver, для корректного отображения views */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    /* Данный метод указывает url, на котором будет базироваться приложение */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /* Метод #1 для фильтра в Spring (обрабатываем POST со скрытым полем PATCH(ищем "_method")) */
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);

        /* Решение проблемы с кириллицей */
        FilterRegistration.Dynamic encodingFilter = aServletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");

        registerHiddenFieldFilter(aServletContext);
    }

    /* Метод #2 для фильтра в Spring (обрабатываем POST со скрытым полем PATCH(ищем "_method")) */
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

    /* Решение проблемы с кириллицей */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }
}
