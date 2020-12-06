package com.skillmapshare.api.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.core.env.Environment
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.thymeleaf.templateresolver.ITemplateResolver
import org.thymeleaf.templateresolver.StringTemplateResolver
import java.util.*

@Configuration
/**
 * Thymeleafのテンプレートパスを利用して、メール送信するための設定クラス
 * Beanを生成する
 */
class ThymeleafMailConfig : ApplicationContextAware, EnvironmentAware {
    private val EMAIL_TEMPLATE_ENCODING = "utf-8"
    private lateinit var applicationContext : ApplicationContext
    private lateinit var environment : Environment

    @Bean
    fun emailMessageSource() : ResourceBundleMessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("mail/MailMessages")
        return messageSource
    }
    @Bean(name = ["emailTemplateEngine"])
    fun emailTemplateEngine() : TemplateEngine {
        val templateEngine = SpringTemplateEngine()
        // add resolver for text email
        templateEngine.addTemplateResolver(textTemplateResolver())
        // add resolver for html email
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        // message source,
        templateEngine.setTemplateEngineMessageSource(emailMessageSource())
        return templateEngine
    }

    private fun textTemplateResolver() : ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.order = Integer.valueOf(1)
        templateResolver.resolvablePatterns = Collections.singleton("text/*")
        templateResolver.prefix = "/mail/"
        templateResolver.suffix = ".txt"
        templateResolver.templateMode = TemplateMode.TEXT
        templateResolver.characterEncoding = EMAIL_TEMPLATE_ENCODING
        templateResolver.isCacheable = false
        return templateResolver
    }

    private fun htmlTemplateResolver() : ITemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.order = Integer.valueOf(2)
        templateResolver.resolvablePatterns = Collections.singleton("html/*")
        templateResolver.prefix = "/mail/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = EMAIL_TEMPLATE_ENCODING
        templateResolver.isCacheable = false
        return templateResolver
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    override fun setEnvironment(environment: Environment){
        this.environment = environment
    }
}