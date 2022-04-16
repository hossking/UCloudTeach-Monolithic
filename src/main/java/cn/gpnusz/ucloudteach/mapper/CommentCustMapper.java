package cn.gpnusz.ucloudteach.mapper;

import cn.gpnusz.ucloudteach.resp.CommonCommentResp;

import java.util.List;

/**
 * @author h0ss
 * @description 用户获取课程评论的mapper
 * @date 2021/11/30 - 2:17
 */
public interface CommentCustMapper {

    /**
     * 根据课程id查询评论信息的接口
     * @param courseId : 课程id
     * @return : java.util.List<cn.gpnusz.ucloudteach.resp.CommonCommentResp>
     * @author h0ss
     */
    List<CommonCommentResp> getCommentByCourse(Long courseId);
}
