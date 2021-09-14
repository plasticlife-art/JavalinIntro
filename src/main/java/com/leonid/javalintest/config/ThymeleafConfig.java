package com.leonid.javalintest.config;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Leonid Cheremshantsev
 */
public class ThymeleafConfig {


    public static TemplateEngine templateEngine(ITemplateResolver templateResolver) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    public static ITemplateResolver templateResolver(TemplateMode mode, String prefix, String suffix) {
        ClassLoaderTemplateResolver templateResolver =
                new ClassLoaderTemplateResolver(Thread.currentThread().getContextClassLoader());
        templateResolver.setTemplateMode(mode);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }


}
