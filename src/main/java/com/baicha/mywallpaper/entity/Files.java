package com.baicha.mywallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private String fileId;

    /**
     * 
     */
    private String fileUrl;

    /**
     * 
     */
    private Date uploadTime;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private String userId;

    /**
     *
     */
    private String fileName;

    /**
     *
     */
    private Integer number;

    /**
     *
     */
    private String fileTitle;

    /**
     *
     */
    private String fileUrlse;
}