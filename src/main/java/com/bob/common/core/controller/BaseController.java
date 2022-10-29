package com.bob.common.core.controller;

import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.PageDomain;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.core.page.TableSupport;
import com.bob.common.utils.DateUtils;
import com.bob.common.utils.ServletUtils;
import com.bob.common.utils.StringUtils;
import com.bob.common.utils.sql.SqlUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import static com.bob.common.core.domain.AjaxResult.Type;

/**
 * web层基础数据处理
 */
public class BaseController {

    /**
     * 处理前台日期格式的String，自动转化为Date
     * @param webDataBinder
     */
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text){
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置数据分页
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.getPageDomain();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)){
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 获取request
     * @return
     */
    public HttpServletRequest getRequest(){
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     * @return
     */
    public HttpServletResponse getResponse(){
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     * @return
     */
    public HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     * @param list
     * @return
     */
    protected TableDataInfo getDataTable(List<?> list){
        TableDataInfo dataInfo = new TableDataInfo();
        dataInfo.setCode(0);
        dataInfo.setRows(list);
        dataInfo.setTotal(new PageInfo(list).getTotal());
        return dataInfo;
    }

    /**
     * 返回响应结果
     * @param rows
     * @return
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(Type type, String message) {
        return new AjaxResult(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

}

