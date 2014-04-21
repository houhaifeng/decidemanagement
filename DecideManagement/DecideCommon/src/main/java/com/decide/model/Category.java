/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.decide.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

public class Category implements TBase<Category, Category._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("Category");

  private static final TField ID_FIELD_DESC = new TField("id", TType.I32, (short)1);
  private static final TField NAME_FIELD_DESC = new TField("name", TType.STRING, (short)2);
  private static final TField IDENITYNAME_FIELD_DESC = new TField("idenityname", TType.STRING, (short)3);
  private static final TField DESCRIPTION_FIELD_DESC = new TField("description", TType.STRING, (short)4);
  private static final TField PID_FIELD_DESC = new TField("pid", TType.I32, (short)5);
  private static final TField GRADE_FIELD_DESC = new TField("grade", TType.I32, (short)6);
  private static final TField URI_FIELD_DESC = new TField("uri", TType.STRING, (short)7);
  private static final TField ORDERS_FIELD_DESC = new TField("orders", TType.STRING, (short)8);

  private int id;
  private String name;
  private String idenityname;
  private String description;
  private int pid;
  private int grade;
  private String uri;
  private String orders;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    ID((short)1, "id"),
    NAME((short)2, "name"),
    IDENITYNAME((short)3, "idenityname"),
    DESCRIPTION((short)4, "description"),
    PID((short)5, "pid"),
    GRADE((short)6, "grade"),
    URI((short)7, "uri"),
    ORDERS((short)8, "orders");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // NAME
          return NAME;
        case 3: // IDENITYNAME
          return IDENITYNAME;
        case 4: // DESCRIPTION
          return DESCRIPTION;
        case 5: // PID
          return PID;
        case 6: // GRADE
          return GRADE;
        case 7: // URI
          return URI;
        case 8: // ORDERS
          return ORDERS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __PID_ISSET_ID = 1;
  private static final int __GRADE_ISSET_ID = 2;
  private BitSet __isset_bit_vector = new BitSet(3);

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new FieldMetaData("id", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.NAME, new FieldMetaData("name", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.IDENITYNAME, new FieldMetaData("idenityname", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.DESCRIPTION, new FieldMetaData("description", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.PID, new FieldMetaData("pid", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.GRADE, new FieldMetaData("grade", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.I32)));
    tmpMap.put(_Fields.URI, new FieldMetaData("uri", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    tmpMap.put(_Fields.ORDERS, new FieldMetaData("orders", TFieldRequirementType.DEFAULT, 
        new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(Category.class, metaDataMap);
  }

  public Category() {
  }

  public Category(
    int id,
    String name,
    String idenityname,
    String description,
    int pid,
    int grade,
    String uri,
    String orders)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.name = name;
    this.idenityname = idenityname;
    this.description = description;
    this.pid = pid;
    setPidIsSet(true);
    this.grade = grade;
    setGradeIsSet(true);
    this.uri = uri;
    this.orders = orders;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Category(Category other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.id = other.id;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetIdenityname()) {
      this.idenityname = other.idenityname;
    }
    if (other.isSetDescription()) {
      this.description = other.description;
    }
    this.pid = other.pid;
    this.grade = other.grade;
    if (other.isSetUri()) {
      this.uri = other.uri;
    }
    if (other.isSetOrders()) {
      this.orders = other.orders;
    }
  }

  public Category deepCopy() {
    return new Category(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.name = null;
    this.idenityname = null;
    this.description = null;
    setPidIsSet(false);
    this.pid = 0;
    setGradeIsSet(false);
    this.grade = 0;
    this.uri = null;
    this.orders = null;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
    setIdIsSet(true);
  }

  public void unsetId() {
    __isset_bit_vector.clear(__ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been asigned a value) and false otherwise */
  public boolean isSetId() {
    return __isset_bit_vector.get(__ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bit_vector.set(__ID_ISSET_ID, value);
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been asigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getIdenityname() {
    return this.idenityname;
  }

  public void setIdenityname(String idenityname) {
    this.idenityname = idenityname;
  }

  public void unsetIdenityname() {
    this.idenityname = null;
  }

  /** Returns true if field idenityname is set (has been asigned a value) and false otherwise */
  public boolean isSetIdenityname() {
    return this.idenityname != null;
  }

  public void setIdenitynameIsSet(boolean value) {
    if (!value) {
      this.idenityname = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void unsetDescription() {
    this.description = null;
  }

  /** Returns true if field description is set (has been asigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.description = null;
    }
  }

  public int getPid() {
    return this.pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
    setPidIsSet(true);
  }

  public void unsetPid() {
    __isset_bit_vector.clear(__PID_ISSET_ID);
  }

  /** Returns true if field pid is set (has been asigned a value) and false otherwise */
  public boolean isSetPid() {
    return __isset_bit_vector.get(__PID_ISSET_ID);
  }

  public void setPidIsSet(boolean value) {
    __isset_bit_vector.set(__PID_ISSET_ID, value);
  }

  public int getGrade() {
    return this.grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
    setGradeIsSet(true);
  }

  public void unsetGrade() {
    __isset_bit_vector.clear(__GRADE_ISSET_ID);
  }

  /** Returns true if field grade is set (has been asigned a value) and false otherwise */
  public boolean isSetGrade() {
    return __isset_bit_vector.get(__GRADE_ISSET_ID);
  }

  public void setGradeIsSet(boolean value) {
    __isset_bit_vector.set(__GRADE_ISSET_ID, value);
  }

  public String getUri() {
    return this.uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void unsetUri() {
    this.uri = null;
  }

  /** Returns true if field uri is set (has been asigned a value) and false otherwise */
  public boolean isSetUri() {
    return this.uri != null;
  }

  public void setUriIsSet(boolean value) {
    if (!value) {
      this.uri = null;
    }
  }

  public String getOrders() {
    return this.orders;
  }

  public void setOrders(String orders) {
    this.orders = orders;
  }

  public void unsetOrders() {
    this.orders = null;
  }

  /** Returns true if field orders is set (has been asigned a value) and false otherwise */
  public boolean isSetOrders() {
    return this.orders != null;
  }

  public void setOrdersIsSet(boolean value) {
    if (!value) {
      this.orders = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Integer)value);
      }
      break;

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case IDENITYNAME:
      if (value == null) {
        unsetIdenityname();
      } else {
        setIdenityname((String)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
      }
      break;

    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((Integer)value);
      }
      break;

    case GRADE:
      if (value == null) {
        unsetGrade();
      } else {
        setGrade((Integer)value);
      }
      break;

    case URI:
      if (value == null) {
        unsetUri();
      } else {
        setUri((String)value);
      }
      break;

    case ORDERS:
      if (value == null) {
        unsetOrders();
      } else {
        setOrders((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return new Integer(getId());

    case NAME:
      return getName();

    case IDENITYNAME:
      return getIdenityname();

    case DESCRIPTION:
      return getDescription();

    case PID:
      return new Integer(getPid());

    case GRADE:
      return new Integer(getGrade());

    case URI:
      return getUri();

    case ORDERS:
      return getOrders();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case NAME:
      return isSetName();
    case IDENITYNAME:
      return isSetIdenityname();
    case DESCRIPTION:
      return isSetDescription();
    case PID:
      return isSetPid();
    case GRADE:
      return isSetGrade();
    case URI:
      return isSetUri();
    case ORDERS:
      return isSetOrders();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Category)
      return this.equals((Category)that);
    return false;
  }

  public boolean equals(Category that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_idenityname = true && this.isSetIdenityname();
    boolean that_present_idenityname = true && that.isSetIdenityname();
    if (this_present_idenityname || that_present_idenityname) {
      if (!(this_present_idenityname && that_present_idenityname))
        return false;
      if (!this.idenityname.equals(that.idenityname))
        return false;
    }

    boolean this_present_description = true && this.isSetDescription();
    boolean that_present_description = true && that.isSetDescription();
    if (this_present_description || that_present_description) {
      if (!(this_present_description && that_present_description))
        return false;
      if (!this.description.equals(that.description))
        return false;
    }

    boolean this_present_pid = true;
    boolean that_present_pid = true;
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (this.pid != that.pid)
        return false;
    }

    boolean this_present_grade = true;
    boolean that_present_grade = true;
    if (this_present_grade || that_present_grade) {
      if (!(this_present_grade && that_present_grade))
        return false;
      if (this.grade != that.grade)
        return false;
    }

    boolean this_present_uri = true && this.isSetUri();
    boolean that_present_uri = true && that.isSetUri();
    if (this_present_uri || that_present_uri) {
      if (!(this_present_uri && that_present_uri))
        return false;
      if (!this.uri.equals(that.uri))
        return false;
    }

    boolean this_present_orders = true && this.isSetOrders();
    boolean that_present_orders = true && that.isSetOrders();
    if (this_present_orders || that_present_orders) {
      if (!(this_present_orders && that_present_orders))
        return false;
      if (!this.orders.equals(that.orders))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Category other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Category typedOther = (Category)other;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(typedOther.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = TBaseHelper.compareTo(this.id, typedOther.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetName()).compareTo(typedOther.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = TBaseHelper.compareTo(this.name, typedOther.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIdenityname()).compareTo(typedOther.isSetIdenityname());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIdenityname()) {
      lastComparison = TBaseHelper.compareTo(this.idenityname, typedOther.idenityname);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(typedOther.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = TBaseHelper.compareTo(this.description, typedOther.description);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPid()).compareTo(typedOther.isSetPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPid()) {
      lastComparison = TBaseHelper.compareTo(this.pid, typedOther.pid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGrade()).compareTo(typedOther.isSetGrade());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGrade()) {
      lastComparison = TBaseHelper.compareTo(this.grade, typedOther.grade);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUri()).compareTo(typedOther.isSetUri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUri()) {
      lastComparison = TBaseHelper.compareTo(this.uri, typedOther.uri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOrders()).compareTo(typedOther.isSetOrders());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrders()) {
      lastComparison = TBaseHelper.compareTo(this.orders, typedOther.orders);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // ID
          if (field.type == TType.I32) {
            this.id = iprot.readI32();
            setIdIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // NAME
          if (field.type == TType.STRING) {
            this.name = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // IDENITYNAME
          if (field.type == TType.STRING) {
            this.idenityname = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // DESCRIPTION
          if (field.type == TType.STRING) {
            this.description = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // PID
          if (field.type == TType.I32) {
            this.pid = iprot.readI32();
            setPidIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 6: // GRADE
          if (field.type == TType.I32) {
            this.grade = iprot.readI32();
            setGradeIsSet(true);
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 7: // URI
          if (field.type == TType.STRING) {
            this.uri = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 8: // ORDERS
          if (field.type == TType.STRING) {
            this.orders = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    oprot.writeFieldBegin(ID_FIELD_DESC);
    oprot.writeI32(this.id);
    oprot.writeFieldEnd();
    if (this.name != null) {
      oprot.writeFieldBegin(NAME_FIELD_DESC);
      oprot.writeString(this.name);
      oprot.writeFieldEnd();
    }
    if (this.idenityname != null) {
      oprot.writeFieldBegin(IDENITYNAME_FIELD_DESC);
      oprot.writeString(this.idenityname);
      oprot.writeFieldEnd();
    }
    if (this.description != null) {
      oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
      oprot.writeString(this.description);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(PID_FIELD_DESC);
    oprot.writeI32(this.pid);
    oprot.writeFieldEnd();
    oprot.writeFieldBegin(GRADE_FIELD_DESC);
    oprot.writeI32(this.grade);
    oprot.writeFieldEnd();
    if (this.uri != null) {
      oprot.writeFieldBegin(URI_FIELD_DESC);
      oprot.writeString(this.uri);
      oprot.writeFieldEnd();
    }
    if (this.orders != null) {
      oprot.writeFieldBegin(ORDERS_FIELD_DESC);
      oprot.writeString(this.orders);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Category(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("idenityname:");
    if (this.idenityname == null) {
      sb.append("null");
    } else {
      sb.append(this.idenityname);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("description:");
    if (this.description == null) {
      sb.append("null");
    } else {
      sb.append(this.description);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("pid:");
    sb.append(this.pid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("grade:");
    sb.append(this.grade);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uri:");
    if (this.uri == null) {
      sb.append("null");
    } else {
      sb.append(this.uri);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("orders:");
    if (this.orders == null) {
      sb.append("null");
    } else {
      sb.append(this.orders);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}
