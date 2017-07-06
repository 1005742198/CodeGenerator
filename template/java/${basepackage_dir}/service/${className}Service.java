<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service;

import java.util.Map;
import java.util.List;
import ${basepackage}.po.${className}Po;

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
public interface ${className}Service {
	
	public int insert(${className}Po o);
	
	public int update(${className}Po o);
	
	public void delete(<@generateCompositeIdColumns/>);
	
	public ${className}Po getById(<@generateCompositeIdColumns/>);
	
	public List<${className}Po> select(Map<String, Object> map);
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