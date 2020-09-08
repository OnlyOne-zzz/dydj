package com.bestfeng.dydj.vo;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.mbg.model.Note;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author bsetfeng
 * @since 1.0
 **/
@Getter
@Setter
public class NoteVo  implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "公众号id")
    private Integer uniacid;

    private Integer uid;

    private String jobtitle;

    private String name;

    private Byte sex;

    private String tel;

    private String birthday;

    private String education;

    private String express;

    private String address;

    private String place;

    private String currentstatus;

    private String card;

    private String special;

    private Float money;

    private Integer areaid;

    @ApiModelProperty(value = "创建时间")
    private Integer createtime;

    private Byte status;

    private String avatarurl;

    private Integer refreshtime;

    private String thumbUrl;

    private Integer loginid;

    private Integer sort;

    private Byte isrecommand;

    private Byte isgood;

    private String username;

    private String shopname;

    private Integer cityid;

    /**
     * 服务次数
     */
    private Long serviceFrequency;

    /**
     * 评分
     */
    private Double score;


    public static NoteVo of(Note note, Long serviceFrequency, Double score){
        NoteVo vo = JSON.parseObject(JSON.toJSONString(note), NoteVo.class);
        vo.setServiceFrequency(serviceFrequency);
        vo.setScore(score);
        return vo;
    }
}
