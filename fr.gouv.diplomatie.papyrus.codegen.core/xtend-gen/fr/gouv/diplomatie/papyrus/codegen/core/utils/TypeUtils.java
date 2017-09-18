package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import com.google.common.base.Objects;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Type;

@SuppressWarnings("all")
public class TypeUtils {
  /**
   * retourne le type sequelize
   */
  public static String getSequelizeType(final Type type) {
    String _name = type.getName();
    boolean _equals = Objects.equal(_name, "Boolean");
    if (_equals) {
      return "BOOLEAN";
    } else {
      String _name_1 = type.getName();
      boolean _equals_1 = Objects.equal(_name_1, "Integer");
      if (_equals_1) {
        return "INTEGER";
      } else {
        String _name_2 = type.getName();
        boolean _equals_2 = Objects.equal(_name_2, "BigInteger");
        if (_equals_2) {
          return "BIGINT";
        } else {
          String _name_3 = type.getName();
          boolean _equals_3 = Objects.equal(_name_3, "Real");
          if (_equals_3) {
            return "FLOAT";
          } else {
            String _name_4 = type.getName();
            boolean _equals_4 = Objects.equal(_name_4, "Double");
            if (_equals_4) {
              return "DOUBLE";
            } else {
              String _name_5 = type.getName();
              boolean _equals_5 = Objects.equal(_name_5, "Float");
              if (_equals_5) {
                return "FLOAT";
              } else {
                String _name_6 = type.getName();
                boolean _equals_6 = Objects.equal(_name_6, "Date");
                if (_equals_6) {
                  return "DATE";
                } else {
                  String _name_7 = type.getName();
                  boolean _equals_7 = Objects.equal(_name_7, "ByteArray");
                  if (_equals_7) {
                    return "BLOB";
                  } else {
                    return "STRING";
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * retourne le type  typescript
   */
  public static String getTypescriptType(final Type type) {
    String _name = type.getName();
    boolean _equals = Objects.equal(_name, "Boolean");
    if (_equals) {
      return "boolean";
    } else {
      String _name_1 = type.getName();
      boolean _equals_1 = Objects.equal(_name_1, "Integer");
      if (_equals_1) {
        return "number";
      } else {
        String _name_2 = type.getName();
        boolean _equals_2 = Objects.equal(_name_2, "BigInteger");
        if (_equals_2) {
          return "number";
        } else {
          String _name_3 = type.getName();
          boolean _equals_3 = Objects.equal(_name_3, "Real");
          if (_equals_3) {
            return "number";
          } else {
            String _name_4 = type.getName();
            boolean _equals_4 = Objects.equal(_name_4, "Double");
            if (_equals_4) {
              return "number";
            } else {
              String _name_5 = type.getName();
              boolean _equals_5 = Objects.equal(_name_5, "Float");
              if (_equals_5) {
                return "number";
              } else {
                String _name_6 = type.getName();
                boolean _equals_6 = Objects.equal(_name_6, "Date");
                if (_equals_6) {
                  return "string";
                } else {
                  String _name_7 = type.getName();
                  boolean _equals_7 = Objects.equal(_name_7, "ByteArray");
                  if (_equals_7) {
                    return "Buffer";
                  } else {
                    return "string";
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * retourne le type typescript utilisé dans les classes métier
   */
  public static String getMetierTypescriptType(final Type type) {
    String _name = type.getName();
    boolean _equals = Objects.equal(_name, "Boolean");
    if (_equals) {
      return "boolean";
    } else {
      String _name_1 = type.getName();
      boolean _equals_1 = Objects.equal(_name_1, "Integer");
      if (_equals_1) {
        return "number";
      } else {
        String _name_2 = type.getName();
        boolean _equals_2 = Objects.equal(_name_2, "BigInteger");
        if (_equals_2) {
          return "number";
        } else {
          String _name_3 = type.getName();
          boolean _equals_3 = Objects.equal(_name_3, "Real");
          if (_equals_3) {
            return "number";
          } else {
            String _name_4 = type.getName();
            boolean _equals_4 = Objects.equal(_name_4, "Double");
            if (_equals_4) {
              return "number";
            } else {
              String _name_5 = type.getName();
              boolean _equals_5 = Objects.equal(_name_5, "Float");
              if (_equals_5) {
                return "number";
              } else {
                String _name_6 = type.getName();
                boolean _equals_6 = Objects.equal(_name_6, "Date");
                if (_equals_6) {
                  return "Date";
                } else {
                  String _name_7 = type.getName();
                  boolean _equals_7 = Objects.equal(_name_7, "ByteArray");
                  if (_equals_7) {
                    return "Buffer";
                  } else {
                    return "string";
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * retourne le type dans la base de donnée
   */
  public static String getDatabaseType(final Type type) {
    String _name = type.getName();
    boolean _equals = Objects.equal(_name, "Boolean");
    if (_equals) {
      return "boolean";
    } else {
      String _name_1 = type.getName();
      boolean _equals_1 = Objects.equal(_name_1, "Integer");
      if (_equals_1) {
        return "integer";
      } else {
        String _name_2 = type.getName();
        boolean _equals_2 = Objects.equal(_name_2, "BigInteger");
        if (_equals_2) {
          return "bigint";
        } else {
          String _name_3 = type.getName();
          boolean _equals_3 = Objects.equal(_name_3, "Real");
          if (_equals_3) {
            return "real";
          } else {
            String _name_4 = type.getName();
            boolean _equals_4 = Objects.equal(_name_4, "Double");
            if (_equals_4) {
              return "double precision";
            } else {
              String _name_5 = type.getName();
              boolean _equals_5 = Objects.equal(_name_5, "Float");
              if (_equals_5) {
                return "decimal";
              } else {
                String _name_6 = type.getName();
                boolean _equals_6 = Objects.equal(_name_6, "Date");
                if (_equals_6) {
                  return "timestamp";
                } else {
                  String _name_7 = type.getName();
                  boolean _equals_7 = Objects.equal(_name_7, "ByteArray");
                  if (_equals_7) {
                    return "bytea";
                  } else {
                    return "character";
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * retourne le type du code de l'enum
   */
  public static String getEnumType(final Classifier type) {
    return "integer";
  }
  
  /**
   * retourne le type du code de l'enum pour sequelize
   */
  public static String getEnumSequelizeType(final Classifier type) {
    return "INTEGER";
  }
  
  /**
   * retourne le type du code de l'enum pour typescript
   */
  public static String getEnumTypescriptType(final Classifier type) {
    return "number";
  }
}
