package com.baicha.mywallpaper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName tagAndFile
 */
@TableName(value ="tagAndFile")
@Data
public class Tagandfile {
    /**
     * 
     */
    @TableId
    private Integer id;

    /**
     * 
     */
    private Integer tagId;

    /**
     * 
     */
    private String fileId;
}