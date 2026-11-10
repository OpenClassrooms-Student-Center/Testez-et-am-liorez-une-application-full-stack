package com.openclassrooms.starterjwt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

/**
 * Configuration Spring pour charger les variables du fichier {@code .env}.
 * <p>
 * Permet d'utiliser des placeholders du type {@code ${DB_USER}}, {@code ${TOKEN_SECRET}}
 * dans {@code application.yml}. Le fichier {@code .env} est lu depuis le répertoire
 * de travail de l'application (en pratique le dossier {@code back/}).
 */
@Configuration
public class AppConfig {

    /**
     * Enregistre un resolveur de propriétés pointant vers {@code .env}.
     * <p>
     * Méthode {@code static} : Spring la traite tôt au démarrage, avant l'injection
     * des autres beans qui dépendent de ces propriétés.
     *
     * @return le configurer qui lit le fichier {@code .env}
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new FileSystemResource(".env"));
        return configurer;
    }
}
