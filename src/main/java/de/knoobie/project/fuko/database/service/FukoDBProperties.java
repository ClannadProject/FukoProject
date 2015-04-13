package de.knoobie.project.fuko.database.service;

import de.knoobie.project.clannadutils.common.IOUtils;
import de.knoobie.project.clannadutils.common.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.config.PersistenceUnitProperties;

public class FukoDBProperties {

    private static final String PROPERTIES_LOCATION = "/fuko.properties";

    public static final Map databaseProperties;
    public static final Properties fukoProperties;

    static {
        fukoProperties = new Properties();
        try {

            File jarPath = new File(FukoDBProperties.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            FileInputStream file = new FileInputStream(jarPath.getParent() + PROPERTIES_LOCATION);
            fukoProperties.load(file);
            IOUtils.close(file);
        } catch (IOException ex) {
            Logger.getLogger(FukoDBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        databaseProperties = new HashMap<>();
        if (!StringUtils.isEmpty(fukoProperties.getProperty(PersistenceUnitProperties.JDBC_USER, StringUtils.EMPTY))) {
            databaseProperties.put(PersistenceUnitProperties.JDBC_USER,
                    fukoProperties.getProperty(PersistenceUnitProperties.JDBC_USER, StringUtils.EMPTY));
        }
        if (!StringUtils.isEmpty(fukoProperties.getProperty(PersistenceUnitProperties.JDBC_PASSWORD, StringUtils.EMPTY))) {
            databaseProperties.put(PersistenceUnitProperties.JDBC_PASSWORD,
                    fukoProperties.getProperty(PersistenceUnitProperties.JDBC_PASSWORD, StringUtils.EMPTY));
        }
        if (!StringUtils.isEmpty(fukoProperties.getProperty(PersistenceUnitProperties.JDBC_URL, StringUtils.EMPTY))) {
            databaseProperties.put(PersistenceUnitProperties.JDBC_URL,
                    fukoProperties.getProperty(PersistenceUnitProperties.JDBC_URL, StringUtils.EMPTY));
        }

//        databaseProperties.put(PersistenceUnitProperties.JDBC_PASSWORD, 
//                fukoProperties.getProperty(PersistenceUnitProperties.JDBC_USER, ""));
    }
}
