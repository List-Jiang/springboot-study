package com.jdw.springboot;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author ListJiang
 * @class 算法测试
 * @since 2021/1/1 22:29
 */
public class DigestUtilsTest {
    @Test
    public void md5Hex() {
        // 202cb962ac59075b964b07152d234b70
        System.out.println(DigestUtils.md5Hex("123"));
        // 202cb962ac59075b964b07152d234b70
        System.out.println(DigestUtils.md5Hex("123".getBytes(StandardCharsets.UTF_8)));
        // 32
        System.out.println(DigestUtils.md5Hex("123".getBytes(StandardCharsets.UTF_8)).length());
    }

    @Test
    public void sha1Hex() {
        // 40bd001563085fc35165329ea1ff5c5ecbdbbeef
        System.out.println(DigestUtils.sha1Hex("123"));
        // 40bd001563085fc35165329ea1ff5c5ecbdbbeef
        System.out.println(DigestUtils.sha1Hex("123".getBytes(StandardCharsets.UTF_8)));
        // 40
        System.out.println(DigestUtils.sha1Hex("123".getBytes(StandardCharsets.UTF_8)).length());
    }

    @Test
    public void sha256Hex() {
        // a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3
        System.out.println(DigestUtils.sha256Hex("123"));
        // a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3
        System.out.println(DigestUtils.sha256Hex("123".getBytes(StandardCharsets.UTF_8)));
        // 64
        System.out.println(DigestUtils.sha256Hex("123".getBytes(StandardCharsets.UTF_8)).length());
    }

    @Test
    public void sha384Hex() {
        // 9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f
        System.out.println(DigestUtils.sha384Hex("123"));
        // 9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f
        System.out.println(DigestUtils.sha384Hex("123".getBytes(StandardCharsets.UTF_8)));
        // 96
        System.out.println(DigestUtils.sha384Hex("123".getBytes(StandardCharsets.UTF_8)).length());
    }

    @Test
    public void sha512Hex() {
        // 3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2
        System.out.println(DigestUtils.sha512Hex("123"));
        // 3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2
        System.out.println(DigestUtils.sha512Hex("123".getBytes(StandardCharsets.UTF_8)));
        // 128
        System.out.println(DigestUtils.sha512Hex("123".getBytes(StandardCharsets.UTF_8)).length());
    }

}