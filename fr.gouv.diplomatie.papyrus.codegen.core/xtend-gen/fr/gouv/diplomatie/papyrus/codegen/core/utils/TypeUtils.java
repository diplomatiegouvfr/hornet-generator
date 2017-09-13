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
    if ((Objects.equal(type.getName(), "Boolean") || Objects.equal(type.getName(), "EBoolean"))) {
      return "BOOLEAN";
    } else {
      String _name = type.getName();
      boolean _equals = Objects.equal(_name, "Integer");
      if (_equals) {
        return "INTEGER";
      } else {
        if ((Objects.equal(type.getName(), "BigInteger") || Objects.equal(type.getName(), "EBigInteger"))) {
          return "BIGINT";
        } else {
          String _name_1 = type.getName();
          boolean _equals_1 = Objects.equal(_name_1, "Real");
          if (_equals_1) {
            return "FLOAT";
          } else {
            String _name_2 = type.getName();
            boolean _equals_2 = Objects.equal(_name_2, "EDouble");
            if (_equals_2) {
              return "DOUBLE";
            } else {
              String _name_3 = type.getName();
              boolean _equals_3 = Objects.equal(_name_3, "EFloat");
              if (_equals_3) {
                return "FLOAT";
              } else {
                if ((Objects.equal(type.getName(), "EDate") || Objects.equal(type.getName(), "Date"))) {
                  return "DATE";
                } else {
                  String _name_4 = type.getName();
                  boolean _equals_4 = Objects.equal(_name_4, "EByteArray");
                  if (_equals_4) {
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
    if ((Objects.equal(type.getName(), "Boolean") || Objects.equal(type.getName(), "EBoolean"))) {
      return "boolean";
    } else {
      String _name = type.getName();
      boolean _equals = Objects.equal(_name, "Integer");
      if (_equals) {
        return "number";
      } else {
        if ((Objects.equal(type.getName(), "BigInteger") || Objects.equal(type.getName(), "EBigInteger"))) {
          return "number";
        } else {
          String _name_1 = type.getName();
          boolean _equals_1 = Objects.equal(_name_1, "Real");
          if (_equals_1) {
            return "number";
          } else {
            String _name_2 = type.getName();
            boolean _equals_2 = Objects.equal(_name_2, "EDouble");
            if (_equals_2) {
              return "number";
            } else {
              String _name_3 = type.getName();
              boolean _equals_3 = Objects.equal(_name_3, "EFloat");
              if (_equals_3) {
                return "number";
              } else {
                if ((Objects.equal(type.getName(), "EDate") || Objects.equal(type.getName(), "Date"))) {
                  return "string";
                } else {
                  String _name_4 = type.getName();
                  boolean _equals_4 = Objects.equal(_name_4, "EByteArray");
                  if (_equals_4) {
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
    if ((Objects.equal(type.getName(), "Boolean") || Objects.equal(type.getName(), "EBoolean"))) {
      return "boolean";
    } else {
      String _name = type.getName();
      boolean _equals = Objects.equal(_name, "Integer");
      if (_equals) {
        return "number";
      } else {
        if ((Objects.equal(type.getName(), "BigInteger") || Objects.equal(type.getName(), "EBigInteger"))) {
          return "number";
        } else {
          String _name_1 = type.getName();
          boolean _equals_1 = Objects.equal(_name_1, "Real");
          if (_equals_1) {
            return "number";
          } else {
            String _name_2 = type.getName();
            boolean _equals_2 = Objects.equal(_name_2, "EDouble");
            if (_equals_2) {
              return "number";
            } else {
              String _name_3 = type.getName();
              boolean _equals_3 = Objects.equal(_name_3, "EFloat");
              if (_equals_3) {
                return "number";
              } else {
                if ((Objects.equal(type.getName(), "EDate") || Objects.equal(type.getName(), "Date"))) {
                  return "Date";
                } else {
                  String _name_4 = type.getName();
                  boolean _equals_4 = Objects.equal(_name_4, "EByteArray");
                  if (_equals_4) {
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
    if ((Objects.equal(type.getName(), "Boolean") || Objects.equal(type.getName(), "EBoolean"))) {
      return "boolean";
    } else {
      String _name = type.getName();
      boolean _equals = Objects.equal(_name, "Integer");
      if (_equals) {
        return "integer";
      } else {
        if ((Objects.equal(type.getName(), "BigInteger") || Objects.equal(type.getName(), "EBigInteger"))) {
          return "bigint";
        } else {
          String _name_1 = type.getName();
          boolean _equals_1 = Objects.equal(_name_1, "Real");
          if (_equals_1) {
            return "real";
          } else {
            String _name_2 = type.getName();
            boolean _equals_2 = Objects.equal(_name_2, "EDouble");
            if (_equals_2) {
              return "double precision";
            } else {
              String _name_3 = type.getName();
              boolean _equals_3 = Objects.equal(_name_3, "EFloat");
              if (_equals_3) {
                return "decimal";
              } else {
                if ((Objects.equal(type.getName(), "EDate") || Objects.equal(type.getName(), "Date"))) {
                  return "timestamp";
                } else {
                  String _name_4 = type.getName();
                  boolean _equals_4 = Objects.equal(_name_4, "EByteArray");
                  if (_equals_4) {
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
