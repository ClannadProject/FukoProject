package de.knoobie.project.fuko.database.bo.enums;
import de.knoobie.project.clannadutils.interfaces.AdvancedEnum;

public enum ClannadSetting implements AdvancedEnum {

    CLANNAD_DATA_LOCATION("Clannad Filesystem Location", "Z:\\Clannad"),
    CLANNAD_DATA_LOCATION_ARTIST("Artist Directory Name", "Artist"),
    CLANNAD_DATA_LOCATION_ALBUM("Album Directory Nam", "Album"),
    CLANNAD_DATA_LOCATION_PRODUCT("Product Directory Nam", "Product"),
    CLANNAD_DATA_LOCATION_EVENT("Event Directory Nam", "Event"),
    CLANNAD_DATA_LOCATION_ORGANIZATION("Organization Directory Name", "Event"),
    CLANNAD_DATA_LOCATION_NEW("New Data Directory Nam", ".new");

    private final String defaultValue;
    private final String name;

    ClannadSetting(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getHumanizedName() {
        return this.name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getEnumconstant() {
        return this.name();
    }

    @Override
    public AdvancedEnum[] getValues() {
        return ClannadSetting.values();
    }
}
