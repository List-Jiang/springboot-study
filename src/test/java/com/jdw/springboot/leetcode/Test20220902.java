package com.jdw.springboot.leetcode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class Test20220902 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    int res = 0;

    public int longestUnivaluePath(TreeNode root) {
        dfs(root);
        return res;
    }

    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = 0, right = 0;
        int left1 = dfs(root.left), right1 = dfs(root.right);
        if (root.left != null && root.left.val == root.val) {
            left = left1 + 1;
        }
        if (root.right != null && root.right.val == root.val) {
            right = right1 + 1;
        }
        res = Math.max(res, left + right);
        return Math.max(left, right);
    }


    Map<String, Integer> map = new HashMap<>();
    Map<String, Integer> length = new HashMap<>();

    private int countUniqueChars(String str) {
        if (str.length() == 1) {
            return 1;
        }
        Set<Character> set = new HashSet<>();
        Set<Character> contains = new HashSet<>();
        for (int i = 0; i < str.length(); i++) {
            if (contains.contains(str.charAt(i))) {
                set.remove(str.charAt(i));
            } else {
                contains.add(str.charAt(i));
                set.add(str.charAt(i));
            }
        }
        return set.size();
    }

    public int uniqueLetterString(String s) {
        int l = 0;
        if (s.length() == 1) {
            l = 1;
        } else {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                for (int j = i + 1; j <= s.length(); j++) {
                    list.add(s.substring(i, j));
                }
            }
            for (String s1 : list) {
                if (!map.containsKey(s1)) {
                    int ss = length.containsKey(s1) ? length.get(s1) : countUniqueChars(s1);
                    length.put(s1, ss);
                    l += ss;
                } else {
                    l += map.get(s1);
                }
            }
        }
        map.put(s, l);
        return l;
    }

    @Test
    void uniqueLetterStringTest() {
        uniqueLetterString("DELQGVWNZKIJJPSXOVWWIZUXCEGWSQLESNSRBMKZARFPAXSVWQEZDENDAHNNIBHGHTFDLPGDLFXMIYRFNLMXHNPIFUAXINXPXLCTTJNLGGMKJIOEWBECNOFQPVCIKIAZMNGHEHFMCPWSMJTMGVSXTOGCGUYKFMNCGLCBRAFJLJVPIVDOLJBURULPGXBVDCEWXXXLTRMSHPKSPFDGNVOCZWDXJUWVNAREDOKTZMIUDKDQWWWSAEUUDBHMWZELOSBIHMAYJEMGZPMDOOGSCKLVHTGMETHUISCLJKDOQEWGVBULEMUXGTRKGXYFDIZTZWMLOFTCANBGUARNWQEQWGMIKMORVQUZANJNRNPMJWYLVHWKDFLDDBBMILAKGFROEQAMEVONUVHOHGPKLBPNYZFPLXNBCIFENCGIMIDCXIIQJWPVVCOCJTSKSHVMQJNLHSQTEZQTTMOXUSKBMUJEJDBJQNXECJGSZUDENJCPTTSREKHPRIISXMWBUGMTOVOTRKQCFSDOTEFPSVQINYLHXYVZTVAMWGPNKIDLOPGAMWSKDXEPLPPTKUHEKBQAWEBMORRZHBLOGIYLTPMUVBPGOOOIEBJEGTKQKOUURHSEJCMWMGHXYIAOGKJXFAMRLGTPNSLERNOHSDFSSFASUJTFHBDMGBQOKZRBRAZEQQVWFRNUNHBGKRFNBETEDJIWCTUBJDPFRRVNZENGRANELPHSDJLKVHWXAXUTMPWHUQPLTLYQAATEFXHZARFAUDLIUDEHEGGNIYICVARQNRJJKQSLXKZZTFPVJMOXADCIGKUXCVMLPFJGVXMMBEKQXFNXNUWOHCSZSEZWZHDCXPGLROYPMUOBDFLQMTTERGSSGVGOURDWDSEXONCKWHDUOVDHDESNINELLCTURJHGCJWVIPNSISHRWTFSFNRAHJAJNNXKKEMESDWGIYIQQRLUUADAXOUEYURQRVZBCSHXXFLYWFHDZKPHAGYOCTYGZNPALAUZSTOU");
        Assertions.assertEquals(uniqueLetterString("ABC"), 10);
        Assertions.assertEquals(uniqueLetterString("ABA"), 8);
    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = Arrays.stream(nums).sum();
        int[] array = Arrays.stream(nums).sorted().toArray();
        if (sum % k != 0) return false;
        int chunkSize = sum / k;
        int t = 0, left = 0, right = array.length - 1;
        while (t < k) {
            int chunk = array[right--];
            if (chunk > chunkSize) return false;
            if (chunk == chunkSize) t++;
            if (chunk < chunkSize) {
                while (chunk < chunkSize) {
                    chunk += array[left++];
                }
                if (chunk == chunkSize) {
                    t++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void canPartitionKSubsetsTest() {
        Assertions.assertTrue(canPartitionKSubsets(new int[]{2, 2, 3, 4, 4, 6, 7, 8, 10, 10}, 4));
        Assertions.assertTrue(canPartitionKSubsets(new int[]{4, 3, 2, 3, 5, 2, 1}, 4));
        Assertions.assertFalse(canPartitionKSubsets(new int[]{1, 2, 3, 4}, 3));
    }

    public boolean canFormArray1(int[] arr, int[][] pieces) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        for (int i = 0; i < pieces.length; i++) {
            int j = 0;
            for (int i1 = 0; i1 < pieces[i].length; i1++) {
                if (!map.containsKey(pieces[i][i1])) return false;
                if (map.get(pieces[i][i1]).intValue() < j) return false;
                j = map.get(pieces[i][i1]).intValue();
                map.remove(pieces[i][i1]);
            }
        }
        return map.isEmpty();
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        return false;
    }

    @Test
    public void canFormArray() {
        Assertions.assertTrue(canFormArray(new int[]{91, 4, 64, 78}, new int[][]{new int[]{78}, new int[]{4, 64}, new int[]{91}}));
        Assertions.assertTrue(canFormArray(new int[]{15, 88}, new int[][]{new int[]{88}, new int[]{15}}));
        Assertions.assertFalse(canFormArray(new int[]{1, 2, 3}, new int[][]{new int[]{2}, new int[]{1, 3}}));
        Assertions.assertFalse(canFormArray(new int[]{49, 18, 16}, new int[][]{new int[]{16, 18, 49}}));
    }
}
