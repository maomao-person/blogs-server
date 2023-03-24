package com.tcj.blogs.utils;



import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class CommonUtil {

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        if(ip.split(",").length > 1){
            ip = ip.split(",")[0];
        }

        return ip;
    }

    /**
     * 获取剩余时间
     * @param startTime 起始时间
     * @param period    有效期
     * @return  余下时间的时间戳
     */
    public Long getResidualTimestamp(String startTime, int period){
        Date createDate = null;
        Date expireDate = null;
        Date today = new Date();
        Long residualTimestamp = null;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if(period > 0 && StringUtils.isNotBlank(startTime)){
                createDate = sdf.parse(startTime);
                c.setTime(createDate); //设置时间
                c.add(Calendar.DATE, period); //日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
                expireDate = c.getTime(); //结果
//                System.out.println("[INFO] expireDate="+ expireDate);
//                expireNum = (expireDate.getTime() - today.getTime())/24/60/60/1000;//时间戳相差的毫秒数
//                System.out.println("Remaining Time is: "+expireNum);//除以一天的毫秒数
                residualTimestamp = expireDate.getTime() - today.getTime();
                System.out.println("Residual Timestamp is: "+ residualTimestamp);
            }

        } catch (ParseException e) {
            log.error("Time conversion format error!");
            e.printStackTrace();
        }

        return residualTimestamp;
    }

    /**
     * 读取文件内容
     * @param in
     * @return String
     */
    public String readStream(InputStream in) {
        try {
            // 1.创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 2.创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            // 3.开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            // 4.把字节数组转换为字符串
            String content = baos.toString();
            // 5.关闭输入流和输出流
            in.close();
            baos.close();
            // 6.返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }

    /**
     * 读取文件内容
     * @param path
     * @return (String)
     */
    public String readFileStr(String path, String file_encode){
        InputStream fis = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        StringBuffer s = null;
        try{
            String line;
            s = new StringBuffer();
            File file = new File(path);
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis, file_encode); //最后的"GBK"根据文件属性而定，如果不行，改成"UTF-8"试试
            br = new BufferedReader(reader);
            while ((line = br.readLine()) != null) {
                s.append(line);
                s.append(System.lineSeparator());
            }
            br.close();
            reader.close();

        } catch (Exception e) {
            log.error("readFileStr Exception is: " + e.getMessage());
        } finally{
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("fis.close IOException is: " + e.getMessage());
                }
            }
        }

        return s.toString();
    }


    /**
     * 对比两个时间
     * @param startTime
     * @param endTime
     * @return
     */
    public long validateLifetime(String startTime, String endTime){
        Date startDate = null;
        Date endDate = null;
        int expireNum = -1;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            startDate = sdf.parse(startTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate.getTime() - startDate.getTime();
    }
}
