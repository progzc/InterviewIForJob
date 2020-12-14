package com.progzc.Iterator;

/**
 * @Description 定义通用的社交网络接口
 * @Author zhaochao
 * @Date 2020/12/14 18:10
 * @Email zcprog@foxmail.com
 * @Version V1.0
 */
public interface SocialNetwork {
    ProfileIterator createFriendsIterator(String profileEmail);

    ProfileIterator createCoworkersIterator(String profileEmail);
}
