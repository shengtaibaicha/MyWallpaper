package com.baicha.mywallpaper.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName files
 */
@TableName(value ="files")
@Data
public class Files {
    /**
     * 
     */
    @TableId()
    private String FileId;

    /**
     * 
     */
    private String FileUrl;

    /**
     * 
     */
    private Date UploadTime;

    /**
     * 
     */
    private String Status;

    /**
     * 
     */
    private String UserId;

    /**
     *
     */
    private String FileName;

    /**
     *
     */
    private Integer Number;

    /**
     *
     */
    private String FileTitle;

    /**
     *
     */
    private String FileUrlse;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Integer Deleted;
}