// Generated by http://code.google.com/p/protostuff/ ... DO NOT EDIT!
// Generated from table.proto

package org.yamcs.protobuf;


public final class SchemaTable
{

    public static final class ColumnInfo
    {
        public static final org.yamcs.protobuf.SchemaTable.ColumnInfo.MessageSchema WRITE =
            new org.yamcs.protobuf.SchemaTable.ColumnInfo.MessageSchema();
        public static final org.yamcs.protobuf.SchemaTable.ColumnInfo.BuilderSchema MERGE =
            new org.yamcs.protobuf.SchemaTable.ColumnInfo.BuilderSchema();
        
        public static class MessageSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.ColumnInfo>
        {
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.ColumnInfo message) throws java.io.IOException
            {
                if(message.hasId())
                    output.writeUInt32(1, message.getId(), false);
                if(message.hasName())
                    output.writeString(2, message.getName(), false);
                if(message.hasType())
                    output.writeString(3, message.getType(), false);
                if(message.hasProtoClass())
                    output.writeString(4, message.getProtoClass(), false);
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.ColumnInfo message)
            {
                return message.isInitialized();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.ColumnInfo.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.ColumnInfo.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.ColumnInfo> typeClass()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.class.getName();
            }
            //unused
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.ColumnInfo message) throws java.io.IOException {}
            public org.yamcs.protobuf.Table.ColumnInfo newMessage() { return null; }
        }
        public static class BuilderSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.ColumnInfo.Builder>
        {
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.ColumnInfo.Builder builder) throws java.io.IOException
            {
                for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
                {
                    switch(number)
                    {
                        case 0:
                            return;
                        case 1:
                            builder.setId(input.readUInt32());
                            break;
                        case 2:
                            builder.setName(input.readString());
                            break;
                        case 3:
                            builder.setType(input.readString());
                            break;
                        case 4:
                            builder.setProtoClass(input.readString());
                            break;
                        default:
                            input.handleUnknownField(number, this);
                    }
                }
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.ColumnInfo.Builder builder)
            {
                return builder.isInitialized();
            }
            public org.yamcs.protobuf.Table.ColumnInfo.Builder newMessage()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.newBuilder();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.ColumnInfo.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.ColumnInfo.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.ColumnInfo.Builder> typeClass()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.Builder.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.ColumnInfo.class.getName();
            }
            //unused
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.ColumnInfo.Builder builder) throws java.io.IOException {}
        }
        public static java.lang.String getFieldName(int number)
        {
            switch(number)
            {
                case 1: return "id";
                case 2: return "name";
                case 3: return "type";
                case 4: return "protoClass";
                default: return null;
            }
        }
        public static int getFieldNumber(java.lang.String name)
        {
            java.lang.Integer number = fieldMap.get(name);
            return number == null ? 0 : number.intValue();
        }
        private static final java.util.HashMap<java.lang.String,java.lang.Integer> fieldMap = new java.util.HashMap<java.lang.String,java.lang.Integer>();
        static
        {
            fieldMap.put("id", 1);
            fieldMap.put("name", 2);
            fieldMap.put("type", 3);
            fieldMap.put("protoClass", 4);
        }
    }

    public static final class Cell
    {
        public static final org.yamcs.protobuf.SchemaTable.Cell.MessageSchema WRITE =
            new org.yamcs.protobuf.SchemaTable.Cell.MessageSchema();
        public static final org.yamcs.protobuf.SchemaTable.Cell.BuilderSchema MERGE =
            new org.yamcs.protobuf.SchemaTable.Cell.BuilderSchema();
        
        public static class MessageSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.Cell>
        {
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.Cell message) throws java.io.IOException
            {
                if(message.hasColumnId())
                    output.writeUInt32(1, message.getColumnId(), false);
                if(message.hasData())
                    output.writeByteArray(2, message.getData().toByteArray(), false);

            }
            public boolean isInitialized(org.yamcs.protobuf.Table.Cell message)
            {
                return message.isInitialized();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.Cell.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.Cell.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.Cell> typeClass()
            {
                return org.yamcs.protobuf.Table.Cell.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.Cell.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.Cell.class.getName();
            }
            //unused
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.Cell message) throws java.io.IOException {}
            public org.yamcs.protobuf.Table.Cell newMessage() { return null; }
        }
        public static class BuilderSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.Cell.Builder>
        {
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.Cell.Builder builder) throws java.io.IOException
            {
                for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
                {
                    switch(number)
                    {
                        case 0:
                            return;
                        case 1:
                            builder.setColumnId(input.readUInt32());
                            break;
                        case 2:
                            builder.setData(com.google.protobuf.ByteString.copyFrom(input.readByteArray()));
                            break;
                        default:
                            input.handleUnknownField(number, this);
                    }
                }
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.Cell.Builder builder)
            {
                return builder.isInitialized();
            }
            public org.yamcs.protobuf.Table.Cell.Builder newMessage()
            {
                return org.yamcs.protobuf.Table.Cell.newBuilder();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.Cell.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.Cell.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.Cell.Builder> typeClass()
            {
                return org.yamcs.protobuf.Table.Cell.Builder.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.Cell.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.Cell.class.getName();
            }
            //unused
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.Cell.Builder builder) throws java.io.IOException {}
        }
        public static java.lang.String getFieldName(int number)
        {
            switch(number)
            {
                case 1: return "columnId";
                case 2: return "data";
                default: return null;
            }
        }
        public static int getFieldNumber(java.lang.String name)
        {
            java.lang.Integer number = fieldMap.get(name);
            return number == null ? 0 : number.intValue();
        }
        private static final java.util.HashMap<java.lang.String,java.lang.Integer> fieldMap = new java.util.HashMap<java.lang.String,java.lang.Integer>();
        static
        {
            fieldMap.put("columnId", 1);
            fieldMap.put("data", 2);
        }
    }

    public static final class Row
    {
        public static final org.yamcs.protobuf.SchemaTable.Row.MessageSchema WRITE =
            new org.yamcs.protobuf.SchemaTable.Row.MessageSchema();
        public static final org.yamcs.protobuf.SchemaTable.Row.BuilderSchema MERGE =
            new org.yamcs.protobuf.SchemaTable.Row.BuilderSchema();
        
        public static class MessageSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.Row>
        {
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.Row message) throws java.io.IOException
            {
                for(org.yamcs.protobuf.Table.ColumnInfo column : message.getColumnList())
                    output.writeObject(1, column, org.yamcs.protobuf.SchemaTable.ColumnInfo.WRITE, true);

                for(org.yamcs.protobuf.Table.Cell cell : message.getCellList())
                    output.writeObject(2, cell, org.yamcs.protobuf.SchemaTable.Cell.WRITE, true);

            }
            public boolean isInitialized(org.yamcs.protobuf.Table.Row message)
            {
                return message.isInitialized();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.Row.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.Row.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.Row> typeClass()
            {
                return org.yamcs.protobuf.Table.Row.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.Row.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.Row.class.getName();
            }
            //unused
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.Row message) throws java.io.IOException {}
            public org.yamcs.protobuf.Table.Row newMessage() { return null; }
        }
        public static class BuilderSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.Row.Builder>
        {
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.Row.Builder builder) throws java.io.IOException
            {
                for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
                {
                    switch(number)
                    {
                        case 0:
                            return;
                        case 1:
                            builder.addColumn(input.mergeObject(org.yamcs.protobuf.Table.ColumnInfo.newBuilder(), org.yamcs.protobuf.SchemaTable.ColumnInfo.MERGE));

                            break;
                        case 2:
                            builder.addCell(input.mergeObject(org.yamcs.protobuf.Table.Cell.newBuilder(), org.yamcs.protobuf.SchemaTable.Cell.MERGE));

                            break;
                        default:
                            input.handleUnknownField(number, this);
                    }
                }
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.Row.Builder builder)
            {
                return builder.isInitialized();
            }
            public org.yamcs.protobuf.Table.Row.Builder newMessage()
            {
                return org.yamcs.protobuf.Table.Row.newBuilder();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.Row.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.Row.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.Row.Builder> typeClass()
            {
                return org.yamcs.protobuf.Table.Row.Builder.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.Row.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.Row.class.getName();
            }
            //unused
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.Row.Builder builder) throws java.io.IOException {}
        }
        public static java.lang.String getFieldName(int number)
        {
            switch(number)
            {
                case 1: return "column";
                case 2: return "cell";
                default: return null;
            }
        }
        public static int getFieldNumber(java.lang.String name)
        {
            java.lang.Integer number = fieldMap.get(name);
            return number == null ? 0 : number.intValue();
        }
        private static final java.util.HashMap<java.lang.String,java.lang.Integer> fieldMap = new java.util.HashMap<java.lang.String,java.lang.Integer>();
        static
        {
            fieldMap.put("column", 1);
            fieldMap.put("cell", 2);
        }
    }

    public static final class TableLoadResponse
    {
        public static final org.yamcs.protobuf.SchemaTable.TableLoadResponse.MessageSchema WRITE =
            new org.yamcs.protobuf.SchemaTable.TableLoadResponse.MessageSchema();
        public static final org.yamcs.protobuf.SchemaTable.TableLoadResponse.BuilderSchema MERGE =
            new org.yamcs.protobuf.SchemaTable.TableLoadResponse.BuilderSchema();
        
        public static class MessageSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.TableLoadResponse>
        {
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.TableLoadResponse message) throws java.io.IOException
            {
                if(message.hasRowsLoaded())
                    output.writeUInt32(1, message.getRowsLoaded(), false);
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.TableLoadResponse message)
            {
                return message.isInitialized();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.TableLoadResponse.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.TableLoadResponse.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.TableLoadResponse> typeClass()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.class.getName();
            }
            //unused
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.TableLoadResponse message) throws java.io.IOException {}
            public org.yamcs.protobuf.Table.TableLoadResponse newMessage() { return null; }
        }
        public static class BuilderSchema implements io.protostuff.Schema<org.yamcs.protobuf.Table.TableLoadResponse.Builder>
        {
            public void mergeFrom(io.protostuff.Input input, org.yamcs.protobuf.Table.TableLoadResponse.Builder builder) throws java.io.IOException
            {
                for(int number = input.readFieldNumber(this);; number = input.readFieldNumber(this))
                {
                    switch(number)
                    {
                        case 0:
                            return;
                        case 1:
                            builder.setRowsLoaded(input.readUInt32());
                            break;
                        default:
                            input.handleUnknownField(number, this);
                    }
                }
            }
            public boolean isInitialized(org.yamcs.protobuf.Table.TableLoadResponse.Builder builder)
            {
                return builder.isInitialized();
            }
            public org.yamcs.protobuf.Table.TableLoadResponse.Builder newMessage()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.newBuilder();
            }
            public java.lang.String getFieldName(int number)
            {
                return org.yamcs.protobuf.SchemaTable.TableLoadResponse.getFieldName(number);
            }
            public int getFieldNumber(java.lang.String name)
            {
                return org.yamcs.protobuf.SchemaTable.TableLoadResponse.getFieldNumber(name);
            }
            public java.lang.Class<org.yamcs.protobuf.Table.TableLoadResponse.Builder> typeClass()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.Builder.class;
            }
            public java.lang.String messageName()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.class.getSimpleName();
            }
            public java.lang.String messageFullName()
            {
                return org.yamcs.protobuf.Table.TableLoadResponse.class.getName();
            }
            //unused
            public void writeTo(io.protostuff.Output output, org.yamcs.protobuf.Table.TableLoadResponse.Builder builder) throws java.io.IOException {}
        }
        public static java.lang.String getFieldName(int number)
        {
            switch(number)
            {
                case 1: return "rowsLoaded";
                default: return null;
            }
        }
        public static int getFieldNumber(java.lang.String name)
        {
            java.lang.Integer number = fieldMap.get(name);
            return number == null ? 0 : number.intValue();
        }
        private static final java.util.HashMap<java.lang.String,java.lang.Integer> fieldMap = new java.util.HashMap<java.lang.String,java.lang.Integer>();
        static
        {
            fieldMap.put("rowsLoaded", 1);
        }
    }

}
