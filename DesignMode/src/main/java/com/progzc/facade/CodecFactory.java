package com.progzc.facade;

/**
 * @Description 编解码工厂
 * @Author zhaochao
 * @Date 2020/12/12 17:08
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class CodecFactory {
    public static Codec extract(VideoFile file) {
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            System.out.println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        }
        else {
            System.out.println("CodecFactory: extracting ogg audio...");
            return new OggCompressionCodec();
        }
    }
}
