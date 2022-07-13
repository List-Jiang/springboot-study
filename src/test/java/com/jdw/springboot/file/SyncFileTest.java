package com.jdw.springboot.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

/**
 * 同步文件测试类
 * @author ListJiang
 * @since 2021/2/18 15:25
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SyncFileTest {
    private final String bigFileUrl = "http://gcjs.sczwfw.gov.cn//mongo/download/602e3109bb570000d10019a2";
    private final String smallFileUrl = "http://gcjs.sczwfw.gov.cn/sys/common/download?fileId=5dc12315-cb6c-42e6-82b3" +
            "-3dbbfbebd5b3";
    private final String bigFilePath = "C:\\Users\\jdw\\Downloads\\test.zip";
    private final String smallFilePath = "C:\\Users\\jdw\\Downloads\\test.png";
    private static final int BIG_FILE_SIZE = 20 * 1024 * 1024;
    private static final int ONE_KB_SIZE = 1024;
    @Resource
    private RestTemplate restTemplate;


    @Test
    public void simpleTest() throws IOException {
        byte[] body = restTemplate.getForEntity(bigFileUrl, byte[].class).getBody();
        FileOutputStream outputStream = new FileOutputStream(bigFilePath);
        outputStream.write(body);
        outputStream.close();
    }

    @Test
    public void bigFileTest1() {
        long contentLength = restTemplate.headForHeaders(bigFileUrl).getContentLength();
        RequestCallback requestCallback = request -> {
            LocalDateTime start = LocalDateTime.now();
            System.out.println("开始请求数据时间" + start.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            request.getHeaders()
                    .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
        };
        // getForObject会将所有返回直接放到内存中,使用流来替代这个操作
        ResponseExtractor<Boolean> responseExtractor = response -> {
            LocalDateTime end = LocalDateTime.now();
            System.out.println("获得数据" + end.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            try {
                Files.copy(response.getBody(), Paths.get(bigFilePath));
                System.out.println("文件流大小为：" + response.getBody().available());
            } catch (IOException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        };
        Boolean execute = restTemplate.execute(bigFileUrl, HttpMethod.GET, requestCallback, responseExtractor);
        if (execute) {
            System.out.println("文件下载成功！");
        } else {
            System.out.println("文件下载失败！");
        }
    }

    @Test
    public void bigFileTest2() {
        long contentLength = restTemplate.headForHeaders(bigFileUrl).getContentLength();
        Assert.isTrue(contentLength > 0, "获取文件大小异常");
        boolean isBigFile = contentLength >= BIG_FILE_SIZE;
        if (contentLength > 1024 * ONE_KB_SIZE) {
            log.info("[多线程下载] Content-Length\t{} ({})", contentLength, (contentLength / 1024 / 1024) + "MB");
        } else if (contentLength > ONE_KB_SIZE) {
            log.info("[多线程下载] Content-Length\t{} ({})", contentLength, (contentLength / 1024) + "KB");
        } else {
            log.info("[多线程下载] Content-Length\t" + (contentLength) + "B");
        }
    }

}