package org.playwrightji.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {

    private static final String CONFIGURATION_FILE = "./src/main/resources/config.yaml";
    private static final String PASSWORD = "password";
    private Logger log = LogManager.getLogger();
    private Configuration configuration;
    /**
     * Constructor - Load the config properties for Test
     */
    public ConfigurationFactory() {
        try (InputStream inputStream = new FileInputStream(CONFIGURATION_FILE)) {
            Yaml yaml = new Yaml(new Constructor(Configuration.class));
            this.configuration = yaml.load(inputStream);
            System.out.println(configuration);
        } catch (IOException e) {
            log.error("Error either pre/post parsing config.yaml file. Refer to exception stacktrace ", e);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
