package com.hyomin.demo.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidUtils {

    // UUID v4를 자동으로 생성하여 byte[]로 변환하는 메서드
    public static byte[] generateUuid() {
        return uuidToBytes(UUID.randomUUID()); // UUID v4 생성 및 변환
    }

    // UUID를 byte[]로 변환
    public static byte[] uuidToBytes(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    // byte[]를 UUID로 변환
    public static UUID bytesToUuid(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            throw new IllegalArgumentException("Invalid byte array length for UUID conversion");
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        return new UUID(mostSigBits, leastSigBits);
    }
}
