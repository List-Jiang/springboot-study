package com.jdw.springboot;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ListJiang
 * @class 其他与项目无关测试类
 * @remark
 * @date 2020/8/188:16
 */
public class OtherTest {
    @Test
    public void replaceAllTest(){
        String path = "/gcjs_lhch_wt/99d7e2c787c2abc3b164e6738d9e4c41";
        String regex = "/read/gcjs_lhch_wt/99d7e2c787c2abc3b164e6738d9e4c41";
        String replacement = "/jeecg-boot/api/gcjs/lhch/report/view/{mCbsnum}/{reportId}";
        String newPath = path.replaceAll(regex,replacement);
        System.out.println(newPath);
    }
    @Test
    public void Base64() throws IOException, ClassNotFoundException {
        String msg = "士大夫撒qwertyio!@#$%^&*()_+1234567890-=/*-+|，。/、！@#￥%……&（（（）——+";
        byte[] encode = Base64.getEncoder().encode(msg.getBytes());
//        byte[] decode = Base64.getDecoder().decode(msg);
        System.out.println(Base64.getEncoder().encodeToString(msg.getBytes()));
        System.out.println(new String(encode,"UTF-8"));
        System.out.println(new String(Base64.getDecoder().decode(new String(encode,"UTF-8"))));
        //获取数据流Base64编码
        FileInputStream in = new FileInputStream("C:\\Users\\jdw\\Desktop\\新建文件夹\\timg (1).png");
        byte[] t = new byte[in.available()];
        in.read(t);
        String string = Base64.getEncoder().encodeToString(t);
        byte[] encode1 = Base64.getEncoder().encode(t);
        //Base64解码数据流
        byte[] decode = Base64.getDecoder().decode(string);
        FileOutputStream out = new FileOutputStream("C:\\Users\\jdw\\Desktop\\新建文件夹\\timg (s3).png");
        out.write(decode);
        in.close();
        out.flush();
        out.close();
        //获取String类型Base64编码
        String msg1 = "ds的手法对付《》？#￥%……&*（）";
        byte[] encode2 = Base64.getEncoder().encode(msg1.getBytes());
        //Base64解码为String类型
        String str1 = new String(Base64.getDecoder().decode(encode2));
        System.out.println("原文原文文"+msg1);
        System.out.println("编码解码后"+str1);
        //获取 Long 类型Base64编码
        Object oldObject = new Long("12354687");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(oldObject);
        //原对象字节数组
        byte[] bytes = outputStream.toByteArray();
        //编码后字节数组
        byte[] encode3 = Base64.getEncoder().encode(bytes);
        //解码后字节数组
        byte[] decode1 = Base64.getDecoder().decode(encode3);
        //此处可以通过循环判定内容是否变更，由于字节数组内容完全相等，所以”不想等“字符串永不打印
        if(bytes.length==decode1.length){
            for (int i = 0; i < decode1.length; i++) {
                if(bytes[i]!=decode1[i]){
                    System.out.println("不相等");
                }
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode1);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object newObject = objectInputStream.readObject();
        System.out.println("新旧对象是否相等："+newObject.equals(oldObject));
    }

    /**
     * 驼峰工具类测试
     */
    @Test
    public void CaseFormat1() {
        System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "test-data"));//testData
        System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "test_data"));//testData
        System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "test_data"));//TestData
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "testdata"));//testdata
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "TestData"));//test_data
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, "testData"));//test-data
    }
    @Test
    public void CaseFormat2() {
        List<String> list = new ArrayList<>();
        list.add("ID");
        list.add("PROJECTCODE");
        list.add("PROJECTNAME");
        list.add("INC_JGMC");
        list.add("INC_JYCS");
        list.add("INC_JGLXDH");
        list.add("JBR_MC");
        list.add("JBR_YDDH");
        list.add("ORDERNUM");
        list.add("AREAID");
        list.add("TYPECODE");
        list.add("PROJECTYEAR");
        list.add("CHANGETYPE");
        list.add("EXCHANGE");
        list.add("FILES");
        list.add("STATUS");
        list.add("PROJECTID");
        list.add("PROJECTVER");
        list.add("AREANAME");
        list.add("TYPENAME");
        list.add("APPLYTIME");
        list.add("SFYJXM");
        list.add("SFZDXM");
        list.add("XMSS");
        list.add("ZJLY");
        list.add("INC_JGDM");
        list.add("INC_ULN");
        list.add("INC_TOCL");
        list.add("INC_TUT");
        list.add("USERID");
        list.add("PROJECT_TYPE");
        list.add("PROJECT_NATURE");
        list.add("START_YEAR_MONTH");
        list.add("END_YEAR_MONTH");
        list.add("TOTAL_MONEY");
        list.add("PLACE_CODE");
        list.add("PLACE_CODE_DETAIL");
        list.add("INDUSTRY");
        list.add("SCALE_CONTENT");
        list.add("PROJECT_VALIFLAG");
        list.add("CREATED_TIME");
        list.add("UPDATED_TIME");
        list.add("LAND_AREA");
        list.add("BUILT_AREA");
        list.add("INDUSTRY_NAME");
        list.add("PLACE_CODE_NAME");
        list.add("ENGINERRING_CLASS");
        list.add("PRO_TYPE");
        list.add("PROJECT_FUND_ATTRIBUTE");
        list.add("ENGINERRING_CLASS_NAME");
        list.add("SBSJ");
        list.add("XMJSDDX");
        list.add("XMJSDDY");
        list.add("XMTZLY");
        list.add("TDHQFS");
        list.add("TDSFDSJFA");
        list.add("SFWCQYPG");
        list.add("XMSFWQBJ");
        list.add("DWTYSHXYDM");
        list.add("XMWQBJSJ");
        list.add("XMSJSJ");
        list.add("MAXSTAGE");
        list.add("BSSTATUS");
        list.add("XMSJAREAID");
        list.add("TYPEID");
        list.add("PARENTID");
        list.add("PROJECTNAMESON");
        list.add("PROJECTCODESON");
        list.add("APPLYZJLX");
        list.add("PROJECT_ATTRIBUTES");
        list.add("TARGETSTAGECODE");
        list.add("HISTORYSTAGECODES");
        list.add("PREPROJECTCODES");
        list.add("PROJECTSCOPE");
        list.add("inc_deputy");
        list.add("inc_deputy_mobile");
        list.add("PROJECT_SOURCE");
        list.add("BUILT_LENGTH");
        for (String str:list){
            System.out.println(",p."+str+" as "+ CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,str.toLowerCase()));
        }
    }

    @Test
    public void IntegerTest(){
        Integer t = new Integer(0);
        System.out.println(t);
        t =-2147483648;
        System.out.println(t);
        t--;
        System.out.println(t);

    }

    @Test
    public void test(){
        final String proAreaids = "510703000000,510704000000,510705000000,510711000000,510713000000,510714000000,510715000000,510717000000,510722000000,510723000000,510725000000,510726000000,510727000000,510781000000";
        System.out.println(proAreaids.contains("510704000000"));

        for (int errNum=0;errNum<100;errNum++){
            while (errNum<20){
                try {
                    System.out.println(1/(errNum%2));
                    return;
                } catch (Exception exception) {
                    errNum++;
                    System.out.println("出错次数"+errNum);
                }

            }

        }
    }

    @Test
    public void test1(){
        System.out.println("http://59.225.201.162:8087/group2/M00/73/13/rBKMWV_suVmATkUhAABVUhfkpuM416.pdf".replace("59.225.201.162","172.18.140.105"));
    }

    @Test
    public void test2(){
        int errNum = 0;
        while (errNum < 3) {
            try {
                System.out.println("调用一次");
                break;
            } catch (Exception exception) {
                errNum++;
            }
        }
    }
}
