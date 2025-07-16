package com.baicha.mywallpaper.service.impl;

import com.baicha.mywallpaper.model.Respons;
import com.baicha.mywallpaper.status.FileStatus;
import com.baicha.mywallpaper.tool.MinioTool;
import com.baicha.mywallpaper.tool.RequestContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baicha.mywallpaper.entity.Files;
import com.baicha.mywallpaper.service.FilesService;
import com.baicha.mywallpaper.mapper.FilesMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;

/**
* @author 12793
* @description 针对表【files】的数据库操作Service实现
* @createDate 2025-07-15 14:59:27
*/
@Service
@Slf4j
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files>
    implements FilesService{

    @Autowired
    private FilesMapper filesMapper;

    @Autowired
    private MinioTool minioTool;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Respons upload(MultipartFile file) {
        String s = minioTool.uploadFile(file);
        String[] s1 = s.split("/");
        // 上传完成之后把数据存入mysql
        try {
            Files f = new Files();
            f.setFileUrl(s);
            f.setUploadTime(new Date());
            f.setStatus(FileStatus.NOT_REVIEWED);
            f.setUserId(String.valueOf(RequestContextHolder.get("userId")));
            f.setFileName(s1[4]);
            f.setNumber(0);
            filesMapper.insert(f);
        } catch (Exception e) {
            log.error("文件上传失败" + e.getMessage());
            delete(s1[4]);
            return Respons.error("上传失败！");
        }
        // 记录上传文件日志
        log.info("用户id:" + RequestContextHolder.get("userId") + " 上传文件:" + s1[4]);
        HashMap<String, String> map = new HashMap<>();
        map.put("url", s);
        return Respons.ok(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void download(String fileName, HttpServletResponse response) {
        minioTool.downloadFile(fileName, response);
        // 有人下载后记录下载次数
        LambdaQueryWrapper<Files> qw = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Files> eq = qw.eq(Files::getFileName, fileName);
        Files files = filesMapper.selectOne(eq);
        files.setNumber(files.getNumber() + 1);
        filesMapper.updateById(files);
        log.info("用户id:" + RequestContextHolder.get("userId") + " 下载文件:" + fileName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Respons delete(String fileName) {
        try {
            minioTool.deleteFile(fileName);
            // 先获取当前用户id和数据库的当前图片的用户id是否相同
            LambdaQueryWrapper<Files> qw = new LambdaQueryWrapper<>();
            LambdaQueryWrapper<Files> eq = qw.eq(Files::getFileName, fileName)
                    .eq(Files::getUserId, RequestContextHolder.get("userId"));
            Files files = filesMapper.selectOne(eq);
            if (files == null) {
                return Respons.error("只能删除自己上传的图片！");
            }
            filesMapper.deleteById(files);
            log.info("用户id:" + RequestContextHolder.get("userId") + " 删除文件:" + fileName);
            return Respons.ok();
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Respons.error("删除失败！");
        }
    }

    @Override
    public Respons findPage(Integer page, Integer size) {
        // 创建分页对象
        Page<Files> files = new Page<>(page, size);

        Page<Files> filesPage = filesMapper.selectPage(files, null);
        long total = filesPage.getTotal();
        long pages = files.getPages();

        HashMap<String, Object> map = new HashMap<>();
        return Respons.ok(filesPage);
    }
}




