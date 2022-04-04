package com.sharing.Utils;

import lombok.Data;

import java.io.*;
import java.util.*;

/**
 * 敏感词处理工具类
 *
 * @author 李福生
 * @date 2022-3-27
 * @time 下午 02:41
 */

@Data
public class IllegalWordDisposeUtil {


    /**
     * 敏感词容器
     */
    private static HashMap illegalWordMap;

    /**
     * 敏感词词库文件的路径
     */
    private static final String wordsFilePath;

    /**
     * 初始化词库内容，将词组导入
     */
    static {
        importIllegalWordsByFile();
        wordsFilePath = "D:/毕业设计/敏感词库/敏感词.txt";
    }

    /**
     * 初始化敏感词库,构建DFA算法模型
     *
     * @param illegalWordSet 敏感词set容器
     */
    private static void initIllegalWordMap(Set<String> illegalWordSet) {
        //初始化敏感词容器,减少扩容操作
        illegalWordMap = new HashMap<String, String>(illegalWordSet.size());
        Map<Object, Object> temp;
        Map<Object, Object> newWordMap;
        //遍历sensitiveWordSet
        for (String key : illegalWordSet) {
            temp = illegalWordMap;
            for (int i = 0; i < key.length(); i++) {
                //转换成char型
                char keyChar = key.charAt(i);
                //库中获取关键字
                Object wordMap = temp.get(keyChar);
                //如果存在该key,直接赋值,用于下一个循环获取
                if (wordMap != null) {
                    temp = (Map) wordMap;
                } else {
                    //不存在则,则构建一个map,同时将isEnd设置为0,因为他不是最后一个
                    newWordMap = new HashMap<>();
                    //不是最后一个
                    newWordMap.put("isEnd", "0");
                    temp.put(keyChar, newWordMap);
                    temp = newWordMap;
                }
                //最后一个
                if (i == key.length() - 1) temp.put("isEnd", "1");
            }
        }
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文本
     * @return 若包含返回true, 否则返回false
     */
    public static boolean contains(String txt) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            //  从第 i 个字符开始判断是否包含敏感词组
            int len = checkIllegalWord(txt, i);
            if (len > 0) {//大于0存在,返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检查文字中是否包含敏感字符,检查规则如下:
     *
     * @param text  检测的文本
     * @param begin 开始的下标
     * @return 如果存在, 则返回敏感词字符的长度, 不存在返回0
     */
    private static int checkIllegalWord(String text, int begin) {
        //敏感词结束标识位:用于敏感词只有1位的情况
        boolean flag = false;
        //匹配标识数默认为0
        int length = 0;
        char word;
        Map nowMap = illegalWordMap;
        for (int i = begin; i < text.length(); i++) {
            word = text.charAt(i);
            // 获取对应的key
            nowMap = (Map) nowMap.get(word);
            // 有对应的DFA状态，则判断是否为最后一个
            if (nowMap != null) {
                //  找到相应key,匹配长度 +1
                length++;
                //  如果为最后一个匹配规则,说明匹配到了一个敏感词的状态
                if ("1".equals(nowMap.get("isEnd"))) {
                    //结束标志位为true
                    flag = true;
                }
            } else {
                //  当前词组的DFA已经到结束,整个词组状态匹配完成结束查找
                break;
            }
        }
        if (length < 1 || !flag) {//长度必须大于等于1,为词
            length = 0;
        }
        return length;
    }


    /**
     * 获取文字中的敏感词
     *
     * @param text 检测的字符串
     * @return 返回字符串中包含的所有敏感词list
     */
    public static List<String> getIllegalWord(String text) {
        List<String> illegalWordList = new ArrayList();

        for (int i = 0; i < text.length(); i++) {
            //  从第 i 个字符开始判断是否包含敏感词组
            int length = checkIllegalWord(text, i);

            if (length > 0) {
                //  存在,加入list中
                illegalWordList.add(text.substring(i, i + length));

                // 跳过当前词组，继续遍历因为下一次循环 i 要自增，所以 i 的值需要减 1
                i = i + length - 1;
            }
        }
        return illegalWordList;
    }

    /**
     * 通过敏感词库文件导入敏感词汇
     *
     * @throws IOException 对敏感词库文件读取时发生的异常
     */
    public static void importIllegalWordsByFile() {
        HashSet<String> set = new HashSet<>();
        File file = new File(wordsFilePath);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader buffer = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            buffer = new BufferedReader(isr);
            String line;
            while ((line = buffer.readLine()) != null) {
                // 将敏感词统一转换为小写
                set.add(line.toLowerCase());
            }
        } catch (IOException e) {
            System.out.println("敏感词库文件导入异常");
        } finally {
            if (buffer != null)
                try {
                    buffer.close();
                } catch (IOException e) {
                    System.out.println("文件缓冲区关闭异常");
                }

            if (isr != null)
                try {
                    isr.close();
                } catch (IOException e) {
                    System.out.println("InputStreamReader字节输入流读取器关闭异常");
                }
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    System.out.println("FileInputStream文件字节输入流关闭异常");
                }
        }

        // 根据词库构建DFA
        initIllegalWordMap(set);
    }


    /**
     * 使用c中的值来隐藏过滤字符串
     *
     * @param text 需要处理的字符串
     * @param c    使用c中的值来做替换敏感词汇
     * @return 返回处理完毕后的字符串
     */
    public static String hideIllegalWords(String text, char c) {
        // 将字符串对象转换为字符数组
        String lowerCase = text.toLowerCase();
        char[] chars = lowerCase.toCharArray();

        // 遍历字符数组，替换其中的敏感词字符
        for (int i = 0; i < chars.length; i++) {
            //判断是否包含敏感字符
            int length = checkIllegalWord(text, i);

            if (length > 0) {   //存在则将其用 c 中的值替换掉
                int k = length;
                // 从最后开替换当前敏感词组的每一个字符
                while (k > 0) {
                    // 将字符用 c 的值代替
                    chars[i + k - 1] = c;
                    --k;
                }

                // 跳过当前词组，继续遍历因为下一次循环 i 要自增，所以 i 的值需要减 1
                i = i + length - 1;
            }
        }

        // 替换完毕后返回将替换后的字符数组转换为字符串并返回
        return String.valueOf(chars);
    }


}