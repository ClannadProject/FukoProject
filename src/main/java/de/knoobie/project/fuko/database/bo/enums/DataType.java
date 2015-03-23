package de.knoobie.project.fuko.database.bo.enums;

import de.knoobie.project.clannadutils.interfaces.AdvancedEnum;

public enum DataType implements AdvancedEnum{

  ALBUM("Album","album"), 
  ARTIST("Artist","artist"), 
  EVENT("Event","event"),
  ORGANISATION("Organisation","org"),
  PRODUCT("Product","product"),
  UNKNOWN("Unknown","unknown");

  private final String type;
  private final String name;
  
  DataType(String name, String type){
    this.name = name;
    this.type = type;
  }
  
  @Override
  public String getHumanizedName() {
    return this.name;
  }

  public String getType() {
    return type;
  }

  @Override
  public String getEnumconstant() {
    return this.name();
  }

  @Override
  public AdvancedEnum[] getValues() {
    return DataType.values();
  }
  
}
