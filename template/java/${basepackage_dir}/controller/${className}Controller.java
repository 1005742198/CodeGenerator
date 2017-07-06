<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   
package ${basepackage}.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.huajin.baymax.db.Page;
import com.huajin.baymax.logger.XMsgError;
import com.huajin.baymax.logger.Xlogger;
import com.huajin.baymax.controller.BaseController;
import ${basepackage}.po.${className}Po;
import ${basepackage}.service.${className}Service;

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
@Controller
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller extends BaseController {
	@Autowired
	private ${className}Service ${classNameFirstLower}Service;
	private String viewPath = "${classNameLowerCase}/";
	
	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		Map<String, Object> map = paramToMap(request);
		List<${className}Po> list = ${classNameFirstLower}Service.select(map);
		request.setAttribute("list", list);
		Page page = (Page)map.get("page");
		request.setAttribute("total", page == null ? list.size() : page.getCount());
		request.setAttribute("pageCurrent", page == null ? 1 : page.getPageNo());
		request.setAttribute("pageSize", page == null ? list.size() : page.getPageSize());
		return viewPath + "list";
	}
	
	@RequestMapping("new")
	public String toAddPage(HttpServletRequest request) {
		return viewPath + "add";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public Object add(@ModelAttribute ${className}Po ${classNameFirstLower}) {
		try {
			int id = ${classNameFirstLower}Service.insert(${classNameFirstLower});
			if(id <= 0) {
				return resultError();
			}
			return resultSuccess();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(getClass().getName(), "add", e));
			return resultError();
		}
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request) {
		<@generateCompositeIdColumns/>
		${className}Po obj = ${classNameFirstLower}Service.getById(<@generateCompositeIdColumnsParam/>);
		request.setAttribute("obj", obj);
		return viewPath + "edit";
	}
	
	@RequestMapping("update")
	@ResponseBody
	public Object update(@ModelAttribute ${className}Po ${classNameFirstLower}) {
		try {
			int id = ${classNameFirstLower}Service.update(${classNameFirstLower});
			if(id <= 0) {
				return resultError();
			}
			return resultSuccess();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(getClass().getName(), "update", e));
			return resultError();
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) {
		try {
			<@generateCompositeIdColumns/>
			${classNameFirstLower}Service.delete(<@generateCompositeIdColumnsParam/>);
			return resultSuccess();
		} catch (Exception e) {
			Xlogger.error(XMsgError.buildSimple(getClass().getName(), "delete", e));
			return resultError();
		}
	}

}
<#macro generateCompositeIdColumns>
<#list table.compositeIdColumns as column>
		${column.simpleJavaType} ${column.columnNameLower} = ${column.simpleJavaType}.valueOf(request.getParameter("${column.columnNameLower}"));
</#list>
</#macro>
<#macro generateCompositeIdColumnsParam>
<#list table.compositeIdColumns as column>
<#rt>
<#compress>
${column.columnNameLower}<#if column_has_next>,</#if>
</#compress>
<#lt>
</#list></#macro>