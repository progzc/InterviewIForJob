package com.progzc.facade;

import java.io.File;

/**
 * @Description
 * @Author zhaochao
 * @Date 2020/12/12 17:34
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public class VedioConversion4MpegOgg implements VideoConversionFacade{
    @Override
    public File convertVideo(String fileName, String format)  {
        System.out.println("VedioConversion4MpegOgg: conversion started.");
        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec;
        if (format.equals("mp4")) {
            destinationCodec = new OggCompressionCodec();
        } else {
            destinationCodec = new MPEG4CompressionCodec();
        }
        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        File result = (new AudioMixer()).fix(intermediateResult);
        System.out.println("VedioConversion4MpegOgg: conversion completed.");
        return result;
    }
}
