<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   
package ${basepackage}.rest;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huajin.baymax.db.Page;
import com.huajin.baymax.rest.RestBase;
import com.huajin.baymax.exception.BayMaxBaseException;
import com.huajin.baymax.exception.ExceptionThrowUtil;
import com.huajin.baymax.rest.response.BaseResponse;
import com.huajin.baymax.rest.response.DataResponse;
import com.huajin.baymax.rest.response.PageResponse;
import com.huajin.baymax.support.ResponseCodeBase;
import com.huajin.baymax.support.ResponseCodeProperties;
import ${basepackage}.po.${className}Po;
import ${basepackage}.service.${className}Service;

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
@Controller
@Path("/${classNameLowerCase}")
public class ${className}Rest extends RestBase {
	@Autowired
	private ${className}Service ${classNameFirstLower}Service;
	
	@POST
	@Produces("application/json;charset=utf8")
	@Path("/list")
	public Response list(String content) {
		JSONObject map = JSONObject.parseObject(content);
		Page page = null;
		if(map != null) {
			Integer pageIndex = map.getInteger(_PAGEINDEX);
			Integer pageSize = map.getInteger(_PAGESIZE);
			if(pageIndex != null || pageSize != null) {
				page = new Page();
				page.setPageNo(pageIndex);
				page.setPageSize(pageSize);
				map.put("page", page);
			}
		}
		List<${className}Po> list = ${classNameFirstLower}Service.select(map);
        PageResponse response = BaseResponse.build(PageResponse.class);
        response.setRetcode(ResponseCodeBase.OK)
			.setMsg(ResponseCodeProperties.getProperty(ResponseCodeBase.OK))
        	.setData(list).setTotalpage(page == null ? 1 : page.getLast())
        	.setTotalsize(page == null ? list.size() : (int)page.getCount());
        return response.toResponse();
	}
	
	@GET
	@Produces("application/json;charset=utf8")
	@Path("/view")
	public Object view(<@generateCompositeIdColumns/>) {
		<@checkCompositeIdColumns/>
		${className}Po ${classNameFirstLower} = ${classNameFirstLower}Service.getById(<@generateCompositeIdColumnsParam/>);
		DataResponse response = BaseResponse.build(DataResponse.class);
		response.setRetcode(ResponseCodeBase.OK)
			.setMsg(ResponseCodeProperties.getProperty(ResponseCodeBase.OK))
			.setData(${classNameFirstLower});
		return response.toResponse();
	}
	
	@POST
	@Produces("application/json;charset=utf8")
	@Path("/add")
	public Object add(String content) {
		if(StringUtils.isBlank(content)){
			return ExceptionThrowUtil.emptyParameterException(null).toResponse(); 
		}
		${className}Po ${classNameFirstLower} = JSON.parseObject(content, ${className}Po.class);
		int id = ${classNameFirstLower}Service.insert(${classNameFirstLower});
		BaseResponse response = BaseResponse.build(BaseResponse.class);
		if(id <= 0) {
			response.setRetcode(ResponseCodeBase.SYSTEM_ERROR);
		}else {
			response.setRetcode(ResponseCodeBase.OK);
		}
		response.setMsg(ResponseCodeProperties.getProperty(response.getRetcode()));
		return response.toResponse();
	}
	
	@POST
	@Produces("application/json;charset=utf8")
	@Path("/update")
	public Object update(String content) {
		if(StringUtils.isBlank(content)){
			return ExceptionThrowUtil.emptyParameterException(null).toResponse(); 
		}
		${className}Po ${classNameFirstLower} = JSON.parseObject(content, ${className}Po.class);
		int id = ${classNameFirstLower}Service.update(${classNameFirstLower});
		BaseResponse response = BaseResponse.build(BaseResponse.class);
		if(id <= 0) {
			response.setRetcode(ResponseCodeBase.SYSTEM_ERROR);
		}else {
			response.setRetcode(ResponseCodeBase.OK);
		}
		response.setMsg(ResponseCodeProperties.getProperty(response.getRetcode()));
		return response.toResponse();
	}
	
	@DELETE
	@Produces("application/json;charset=utf8")
	@Path("/delete")
	public Object delete(<@generateCompositeIdColumns/>) {
		<@checkCompositeIdColumns/>
		${classNameFirstLower}Service.delete(<@generateCompositeIdColumnsParam/>);
		BaseResponse response = BaseResponse.build(BaseResponse.class);
		response.setRetcode(ResponseCodeBase.OK)
			.setMsg(ResponseCodeProperties.getProperty(ResponseCodeBase.OK));
		return response.toResponse();
	}

}
<#macro generateCompositeIdColumns>
<#rt>
<#compress>
<#list table.compositeIdColumns as column>
		@QueryParam("${column.columnNameLower}") ${column.javaType} ${column.columnNameLower}<#if column_has_next>,</#if>
</#list>
</#compress>
<#lt>
</#macro>
<#macro generateCompositeIdColumnsParam>
<#list table.compositeIdColumns as column>
<#rt>
<#compress>
${column.columnNameLower}<#if column_has_next>,</#if>
</#compress>
<#lt>
</#list>
</#macro>
<#macro checkCompositeIdColumns>
		if(<#rt><#compress>
<#list table.compositeIdColumns as column>
<#if column.isStringColumn>
StringUtils.isEmpty(${column.columnNameLower}) 
<#else>
${column.columnNameLower} == null
</#if>
<#if column_has_next>||</#if>
</#list></#compress><#lt>) {
			return ExceptionThrowUtil.emptyParameterException(null).toResponse();
		}
</#macro>