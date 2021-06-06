package fastvagas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


@SpringBootApplication(scanBasePackages = "fastvagas")
//@EnableScheduling
public class PostgressApplication {

    @Autowired
    ThymeleafProperties thymeleafProperties;

    @Value("${spring.thymeleaf.templates_root:}")
    private String templatesRoot;

    @Value("${spring.profiles.active}")
    private String profileActive;

    public static void main(String[] args) {
        //System.out.println("123456: " + new BCryptPasswordEncoder().encode("123456"));
        SpringApplication.run(PostgressApplication.class, args);
    }

    @Bean
    public ITemplateResolver defaultTemplateResolver() {
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setSuffix(thymeleafProperties.getSuffix());
        if ("dev".equals(profileActive)) {
            resolver.setPrefix(templatesRoot);
        }
        resolver.setTemplateMode(thymeleafProperties.getMode());
        resolver.setCacheable(thymeleafProperties.isCache());
        return resolver;
    }
}
