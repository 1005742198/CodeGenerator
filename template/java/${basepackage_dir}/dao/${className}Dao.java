<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import ${basepackage}.po.${className}Po;
import com.huajin.baymax.db.annotation.MyBatisDao;
import java.util.Map;
import java.util.List;

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
@MyBatisDao
public interface ${className}Dao {
	public int insert(${className}Po o);
	
	public int update(${className}Po o);
	
	public void delete(<@generatePkParam/>);
	
	public ${className}Po getById(<@generatePkParam/>);
	
	public List<${className}Po> select(Map<String, Object> map);
}
<#macro generatePkParam>
<#compress>
<#if table.pkCount == 1>
${table.pkColumn.simpleJavaType} ${table.pkColumn.columnNameLower}
<#else>
Map<String, Object> map
</#if>
</#compress>
</#macro>