package de.github.GSGJ.API.netty.nettycom;

public enum DecodingState {
    VERSION,
    TYPE,
    PAYLOAD_LENGTH,
    PAYLOAD,
}