package mx.com.prosa.nabhi.misc.domain;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) {
        this.context = applicationContext;
    }

    public static Object getBean (String beanId) {
        return context.getBean(beanId);
    }

}