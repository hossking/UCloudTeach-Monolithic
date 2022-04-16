package cn.gpnusz.ucloudteach.service.course;


import cn.gpnusz.ucloudteach.mapper.CommentCustMapper;
import cn.gpnusz.ucloudteach.req.CourseCommentQueryReq;
import cn.gpnusz.ucloudteach.resp.CommonCommentResp;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author h0ss
 * @description 公共的获取评论信息的业务层
 * @date 2021/11/30 - 2:01
 */
@Service
public class CommonCommentService {

    @Resource
    private CommentCustMapper commentCustMapper;

    public List<CommonCommentResp> getComments(CourseCommentQueryReq courseCommentQueryReq) {
        // 查询条件判断
        if (ObjectUtils.isEmpty(courseCommentQueryReq.getCourseId())) {
            return null;
        }
        // 获取全部课程评论信息
        return commentCustMapper.getCommentByCourse(courseCommentQueryReq.getCourseId());
    }
}
