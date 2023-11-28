// Code generated by protocol buffer compiler. Do not edit!
package emu.lunarcore.proto;

import java.io.IOException;
import us.hebi.quickbuf.FieldName;
import us.hebi.quickbuf.InvalidProtocolBufferException;
import us.hebi.quickbuf.JsonSink;
import us.hebi.quickbuf.JsonSource;
import us.hebi.quickbuf.MessageFactory;
import us.hebi.quickbuf.ProtoMessage;
import us.hebi.quickbuf.ProtoSink;
import us.hebi.quickbuf.ProtoSource;

public final class SelectPhoneThemeScRspOuterClass {
  /**
   * Protobuf type {@code SelectPhoneThemeScRsp}
   */
  public static final class SelectPhoneThemeScRsp extends ProtoMessage<SelectPhoneThemeScRsp> implements Cloneable {
    private static final long serialVersionUID = 0L;

    /**
     * <code>optional uint32 cur_phone_theme = 6;</code>
     */
    private int curPhoneTheme;

    /**
     * <code>optional uint32 retcode = 8;</code>
     */
    private int retcode;

    private SelectPhoneThemeScRsp() {
    }

    /**
     * @return a new empty instance of {@code SelectPhoneThemeScRsp}
     */
    public static SelectPhoneThemeScRsp newInstance() {
      return new SelectPhoneThemeScRsp();
    }

    /**
     * <code>optional uint32 cur_phone_theme = 6;</code>
     * @return whether the curPhoneTheme field is set
     */
    public boolean hasCurPhoneTheme() {
      return (bitField0_ & 0x00000001) != 0;
    }

    /**
     * <code>optional uint32 cur_phone_theme = 6;</code>
     * @return this
     */
    public SelectPhoneThemeScRsp clearCurPhoneTheme() {
      bitField0_ &= ~0x00000001;
      curPhoneTheme = 0;
      return this;
    }

    /**
     * <code>optional uint32 cur_phone_theme = 6;</code>
     * @return the curPhoneTheme
     */
    public int getCurPhoneTheme() {
      return curPhoneTheme;
    }

    /**
     * <code>optional uint32 cur_phone_theme = 6;</code>
     * @param value the curPhoneTheme to set
     * @return this
     */
    public SelectPhoneThemeScRsp setCurPhoneTheme(final int value) {
      bitField0_ |= 0x00000001;
      curPhoneTheme = value;
      return this;
    }

    /**
     * <code>optional uint32 retcode = 8;</code>
     * @return whether the retcode field is set
     */
    public boolean hasRetcode() {
      return (bitField0_ & 0x00000002) != 0;
    }

    /**
     * <code>optional uint32 retcode = 8;</code>
     * @return this
     */
    public SelectPhoneThemeScRsp clearRetcode() {
      bitField0_ &= ~0x00000002;
      retcode = 0;
      return this;
    }

    /**
     * <code>optional uint32 retcode = 8;</code>
     * @return the retcode
     */
    public int getRetcode() {
      return retcode;
    }

    /**
     * <code>optional uint32 retcode = 8;</code>
     * @param value the retcode to set
     * @return this
     */
    public SelectPhoneThemeScRsp setRetcode(final int value) {
      bitField0_ |= 0x00000002;
      retcode = value;
      return this;
    }

    @Override
    public SelectPhoneThemeScRsp copyFrom(final SelectPhoneThemeScRsp other) {
      cachedSize = other.cachedSize;
      if ((bitField0_ | other.bitField0_) != 0) {
        bitField0_ = other.bitField0_;
        curPhoneTheme = other.curPhoneTheme;
        retcode = other.retcode;
      }
      return this;
    }

    @Override
    public SelectPhoneThemeScRsp mergeFrom(final SelectPhoneThemeScRsp other) {
      if (other.isEmpty()) {
        return this;
      }
      cachedSize = -1;
      if (other.hasCurPhoneTheme()) {
        setCurPhoneTheme(other.curPhoneTheme);
      }
      if (other.hasRetcode()) {
        setRetcode(other.retcode);
      }
      return this;
    }

    @Override
    public SelectPhoneThemeScRsp clear() {
      if (isEmpty()) {
        return this;
      }
      cachedSize = -1;
      bitField0_ = 0;
      curPhoneTheme = 0;
      retcode = 0;
      return this;
    }

    @Override
    public SelectPhoneThemeScRsp clearQuick() {
      if (isEmpty()) {
        return this;
      }
      cachedSize = -1;
      bitField0_ = 0;
      return this;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof SelectPhoneThemeScRsp)) {
        return false;
      }
      SelectPhoneThemeScRsp other = (SelectPhoneThemeScRsp) o;
      return bitField0_ == other.bitField0_
        && (!hasCurPhoneTheme() || curPhoneTheme == other.curPhoneTheme)
        && (!hasRetcode() || retcode == other.retcode);
    }

    @Override
    public void writeTo(final ProtoSink output) throws IOException {
      if ((bitField0_ & 0x00000001) != 0) {
        output.writeRawByte((byte) 48);
        output.writeUInt32NoTag(curPhoneTheme);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        output.writeRawByte((byte) 64);
        output.writeUInt32NoTag(retcode);
      }
    }

    @Override
    protected int computeSerializedSize() {
      int size = 0;
      if ((bitField0_ & 0x00000001) != 0) {
        size += 1 + ProtoSink.computeUInt32SizeNoTag(curPhoneTheme);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        size += 1 + ProtoSink.computeUInt32SizeNoTag(retcode);
      }
      return size;
    }

    @Override
    @SuppressWarnings("fallthrough")
    public SelectPhoneThemeScRsp mergeFrom(final ProtoSource input) throws IOException {
      // Enabled Fall-Through Optimization (QuickBuffers)
      int tag = input.readTag();
      while (true) {
        switch (tag) {
          case 48: {
            // curPhoneTheme
            curPhoneTheme = input.readUInt32();
            bitField0_ |= 0x00000001;
            tag = input.readTag();
            if (tag != 64) {
              break;
            }
          }
          case 64: {
            // retcode
            retcode = input.readUInt32();
            bitField0_ |= 0x00000002;
            tag = input.readTag();
            if (tag != 0) {
              break;
            }
          }
          case 0: {
            return this;
          }
          default: {
            if (!input.skipField(tag)) {
              return this;
            }
            tag = input.readTag();
            break;
          }
        }
      }
    }

    @Override
    public void writeTo(final JsonSink output) throws IOException {
      output.beginObject();
      if ((bitField0_ & 0x00000001) != 0) {
        output.writeUInt32(FieldNames.curPhoneTheme, curPhoneTheme);
      }
      if ((bitField0_ & 0x00000002) != 0) {
        output.writeUInt32(FieldNames.retcode, retcode);
      }
      output.endObject();
    }

    @Override
    public SelectPhoneThemeScRsp mergeFrom(final JsonSource input) throws IOException {
      if (!input.beginObject()) {
        return this;
      }
      while (!input.isAtEnd()) {
        switch (input.readFieldHash()) {
          case 405670747:
          case -514634695: {
            if (input.isAtField(FieldNames.curPhoneTheme)) {
              if (!input.trySkipNullValue()) {
                curPhoneTheme = input.readUInt32();
                bitField0_ |= 0x00000001;
              }
            } else {
              input.skipUnknownField();
            }
            break;
          }
          case 1097936398: {
            if (input.isAtField(FieldNames.retcode)) {
              if (!input.trySkipNullValue()) {
                retcode = input.readUInt32();
                bitField0_ |= 0x00000002;
              }
            } else {
              input.skipUnknownField();
            }
            break;
          }
          default: {
            input.skipUnknownField();
            break;
          }
        }
      }
      input.endObject();
      return this;
    }

    @Override
    public SelectPhoneThemeScRsp clone() {
      return new SelectPhoneThemeScRsp().copyFrom(this);
    }

    @Override
    public boolean isEmpty() {
      return ((bitField0_) == 0);
    }

    public static SelectPhoneThemeScRsp parseFrom(final byte[] data) throws
        InvalidProtocolBufferException {
      return ProtoMessage.mergeFrom(new SelectPhoneThemeScRsp(), data).checkInitialized();
    }

    public static SelectPhoneThemeScRsp parseFrom(final ProtoSource input) throws IOException {
      return ProtoMessage.mergeFrom(new SelectPhoneThemeScRsp(), input).checkInitialized();
    }

    public static SelectPhoneThemeScRsp parseFrom(final JsonSource input) throws IOException {
      return ProtoMessage.mergeFrom(new SelectPhoneThemeScRsp(), input).checkInitialized();
    }

    /**
     * @return factory for creating SelectPhoneThemeScRsp messages
     */
    public static MessageFactory<SelectPhoneThemeScRsp> getFactory() {
      return SelectPhoneThemeScRspFactory.INSTANCE;
    }

    private enum SelectPhoneThemeScRspFactory implements MessageFactory<SelectPhoneThemeScRsp> {
      INSTANCE;

      @Override
      public SelectPhoneThemeScRsp create() {
        return SelectPhoneThemeScRsp.newInstance();
      }
    }

    /**
     * Contains name constants used for serializing JSON
     */
    static class FieldNames {
      static final FieldName curPhoneTheme = FieldName.forField("curPhoneTheme", "cur_phone_theme");

      static final FieldName retcode = FieldName.forField("retcode");
    }
  }
}
