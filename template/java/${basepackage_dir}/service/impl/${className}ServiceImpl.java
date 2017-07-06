<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.impl;

import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ${basepackage}.dao.${className}Dao;
import ${basepackage}.po.${className}Po;
import ${basepackage}.service.${className}Service;
import com.huajin.baymax.service.AbstractBaseService;

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
@Service("${className}Service")
public class ${className}ServiceImpl implements ${className}Service {
	@Autowired
	private ${className}Dao ${className?uncap_first}Dao;
	
	@Transactional(rollbackFor = Exception.class)
	public int insert(${className}Po o) {
		${className?uncap_first}Dao.insert(o);
		return o.get${table.pkColumn.columnName}();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int update(${className}Po o) {
		return ${className?uncap_first}Dao.update(o);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void delete(<@generateCompositeIdColumns/>) {
		<#if table.pkCount == 1>
		${className?uncap_first}Dao.getById(${table.pkColumn.columnNameLower});
		<#else>
		<@generateCompositeIdColumnsMap/>
		${className?uncap_first}Dao.delete(map);
		</#if>
	}
	
	public ${className}Po getById(<@generateCompositeIdColumns/>) {
		<#if table.pkCount == 1>
		return ${className?uncap_first}Dao.getById(${table.pkColumn.columnNameLower});
		<#else>
		<@generateCompositeIdColumnsMap/>
		return ${className?uncap_first}Dao.getById(map);
		</#if>
	}
	
	public List<${className}Po> select(Map<String, Object> map) {
		return ${className?uncap_first}Dao.select(map);
	}
}
<#macro generateCompositeIdColumns>
<#list table.compositeIdColumns as column>
<#rt>
<#compress>
${column.simpleJavaType} ${column.columnNameLower}<#if column_has_next>,</#if>
</#compress>
<#lt>
</#list>
</#macro>
<#macro generateCompositeIdColumnsMap>
		Map<String, Object> map = new java.util.HashMap<String, Object>();
<#list table.compositeIdColumns as column>
		map.put("${column.columnNameLower}", ${column.columnNameLower});
</#list>
</#macro>