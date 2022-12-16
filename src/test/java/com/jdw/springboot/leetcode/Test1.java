package com.jdw.springboot.leetcode;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Test1 {
    static class OrderedStream {
        String[] strings;

        public OrderedStream(int n) {
            strings = new String[n];
        }

        public List<String> insert(int idKey, String value) {
            strings[idKey - 1] = value;
            List<String> result = new ArrayList<>(strings.length - idKey + 1);
            int i = idKey;
            boolean start = true;
            while (i-- > 0) {
                if (strings[i] == null) {
                    start = false;
                }
            }
            if (start) {
                int index = idKey - 1;
                while (index < strings.length && strings[index] != null) {
                    result.add(strings[index++]);
                }
            }
            return result;
        }
    }

    @Test
    void orderedStreamTest() {
        OrderedStream orderedStream = new OrderedStream(5);
        orderedStream.insert(3, "ccccc");
        orderedStream.insert(1, "aaaaa");
        orderedStream.insert(2, "bbbbb");
        orderedStream.insert(5, "eeeee");
        orderedStream.insert(4, "ddddd");
    }

    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int mi = i, mj = j, maxArea = Math.min(height[mi], height[mj]) * (mj - mi);
        while (i < j) {
            if (height[i] < height[j]) {
                while (height[i] > height[i + 1] && i < height.length - 2) {
                    i++;
                }
                i++;
                mi = i;
            } else {
                while (height[j - 1] < height[j] && 0 < j) {
                    j--;
                }
                j--;
                mj = j;
            }
            maxArea = Math.max(Math.min(height[mi], height[mj]) * (mj - mi), maxArea);
        }
        return maxArea;
    }

    @Test
    public void maxArea() {
        Assertions.assertEquals(24, maxArea(new int[]{1, 3, 2, 5, 25, 24, 5}));
        Assertions.assertEquals(49, maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    public String intToRoman(int num) {
        List<String> list = Arrays.asList("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "III", "II", "I");
        Map<String, Integer> map = new HashMap<>();
        map.put("M", 1000);
        map.put("CM", 900);
        map.put("D", 500);
        map.put("CD", 400);
        map.put("C", 100);
        map.put("XC", 90);
        map.put("L", 50);
        map.put("XL", 40);
        map.put("X", 10);
        map.put("IX", 9);
        map.put("V", 5);
        map.put("IV", 4);
        map.put("III", 3);
        map.put("II", 2);
        map.put("I", 1);
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < num / map.get(list.get(i)); j++) {
                result += list.get(i);
            }
            num = num % map.get(list.get(i));
        }
        return result;
    }

    @Test
    public void intToRoman() {
        Assertions.assertEquals("III", intToRoman(3));
        Assertions.assertEquals("IV", intToRoman(4));
        Assertions.assertEquals("IX", intToRoman(9));
        Assertions.assertEquals("LVIII", intToRoman(58));
        Assertions.assertEquals("MCMXCIV", intToRoman(1994));
    }

    public boolean isMatch(String s, String p) {

        return true;
    }

    @Test
    public void isMatch() {

    }


    @Test
    public void isMatch1() {
    }

    private void sss(String name, String str, JSONObject ss) {
        Set<String> set = Set.of("_custall", "_custall_1", "adivce_type_name", "adivceall", "advice_datetime", "advice_dept_name", "advice_item_content", "advice_item_name", "advice_stop_datetime", "afteroperation_diag_icd_name", "age", "anaesthesia_doctor_name", "anaesthesia_method_name", "anaesthesia_result", "birthday", "chief_complaint", "currentaddress", "dept_name", "deptname", "diag_code", "diag_code_symbol", "diag_course_desc", "diag_datatime", "diag_name", "diagall", "director_sign", "dishospital_date", "dishospital_way_name", "dishospitaldeptname", "doctor_sign", "dosage_unit", "drug_dosage", "drug_spec", "drug_usage_frequency", "drug_usage_way", "emrdocinfoA04", "emrdocinfoA06", "emrdocinfoA08", "emrdocinfoA09", "emrdocinfo_new", "exam_method_name", "exam_type", "examinfo", "fee_cure", "fee_drug", "fee_zcy", "generaloperation", "healthcardno", "inp_date", "inp_doctor_sign", "inpdeptname", "inpmrcoststat", "inpno", "item_no", "job_type_name", "lab_date", "lab_diag_fee", "lab_item_code", "lab_item_name", "lab_item_result_name", "labinfoitemresult", "nurse_fee", "operatiion_end_datetime", "operation_begin_datetime", "operation_cutheal_codename", "operation_doctor_name", "operation_grade_name", "operation_name", "operation_part_name", "operationname", "operattion_cutheal_typename", "outpno", "page", "pat_adm_condition", "pat_id", "pathology_type", "patient_name", "patientname", "physiciandoctorsign", "preoperation_diag_icd_name", "report_date", "report_doctor_sign", "sexname", "symptom_desc", "total_fee", "treat_situation", "vide_diag_fee", "visit_datetime", "visit_no");
        JSONObject jsonObject = JSON.parseObject(str);
        JSONObject json = jsonObject.getJSONObject("properties");
        for (String s1 : json.keySet()) {
            if (!set.contains(s1)) {
                JSONObject jsonObject1 = json.getJSONObject(s1);
                jsonObject1.put("index", false);
            }

        }
        jsonObject.put("properties", json);
        System.out.println(name + "\n" + json.toJSONString());
        ss.put(name, jsonObject);
    }


    @Test
    public void test() {}
}

