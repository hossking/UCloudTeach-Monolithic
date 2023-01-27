package cn.gpnusz.ucloudteach.util.token.media;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
