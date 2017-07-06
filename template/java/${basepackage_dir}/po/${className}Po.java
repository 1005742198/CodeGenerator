<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.po;

<#list table.columns as column>
<#if column.isDateTimeColumn>
import org.springframework.format.annotation.DateTimeFormat;
<#break>
</#if>
</#list>

/**
 * 
 * ${table.tableAlias}
 * @author ${author}
 * ${now?string('yyyy-MM-dd mm:HH:ss')}
 */
public class ${className}Po {
	<#list table.columns as column>
	/**
	 * ${column.columnAlias}
	 */
	<#if column.isDateTimeColumn>
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	</#if>
	private ${column.simpleJavaType} ${column.columnNameLower};
	</#list>

<@generateJavaColumns/>

}

<#macro generateJavaColumns>
	<#list table.columns as column>
	public void set${column.columnName}(${column.simpleJavaType} value) {
		this.${column.columnNameLower} = value;
	}
	public ${column.simpleJavaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#list>
</#macro>
