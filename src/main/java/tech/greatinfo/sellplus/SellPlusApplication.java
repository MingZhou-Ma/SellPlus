package tech.greatinfo.sellplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class SellPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SellPlusApplication.class, args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {

                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");

                container.addErrorPages(error404Page);
            }
        };
    }

}
