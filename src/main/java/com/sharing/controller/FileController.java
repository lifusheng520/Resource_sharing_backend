package com.sharing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 与上传下载文件相关的controller接口
 *
 * @author 李福生
 * @date 2022-3-12
 * @time 上午 09:04
 */

@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传时的磁盘路径
     */
    @Value("${files.icon.upload.path}")
    private String uploadPath;

    @Value("${files.icon.host.url}")
    private String hostUrl;

    /**
     * 文件上传接口
     *
     * @param file 接收的文件
     * @return 处理的结果和状态
     * @throws IOException 中间对磁盘操作可能抛出io异常
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 先存放到磁盘
        File uploadFile = new File(this.uploadPath);
        if (!uploadFile.exists()) {   // 判断文件目录是否存在
            uploadFile.mkdirs();
        }
        // 文件的唯一标识id
        String fileId = IdUtil.fastSimpleUUID();
        File distFile = new File(this.uploadPath + fileId + StrUtil.DOT + type);

        String url = null;
        String md5;

        // 当文件存时获取文件md5，然后到数据库中查询是否重复
        if(distFile.exists()){
            // 获取文件md5
            md5 = SecureUtil.md5(distFile);
            System.out.println("md5==================================" + md5);

            // 查询文件是否存在(数据库中)

            // 如果查询到了文件信息（不为null），说明本次上传的是重复文件，则url = 数据库中的URL
            
            // 否则将本次上传的文件存储到磁盘目录

            // 把获取到的文件存储到磁盘
            file.transferTo(distFile);
            url = this.hostUrl + fileId;

        } else {
            // 把获取到的文件存储到磁盘
            file.transferTo(distFile);

            // 获取文件md5
            md5 = SecureUtil.md5(distFile);
        }




        // 查询文件是否存在(数据库中)

        // 查询数据库





        // 存储到数据库


        return url;
    }

    @GetMapping("/{fileId}")
    public void download(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        // 根据文件唯一标识id获取文件
        File file = new File(this.uploadPath + fileId);

        // 设置输出流格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileId, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        outputStream.write(FileUtil.readBytes(file));
        outputStream.flush();
        outputStream.close();
    }



}
