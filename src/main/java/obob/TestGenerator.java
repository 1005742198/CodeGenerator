package obob;
import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

public class TestGenerator {

	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			System.err.println("the author must be gived.");
			System.exit(0);
		}
		GeneratorProperties.setProperty("author", args[0]);
		
		GeneratorFacade g = new GeneratorFacade();
		//删除生成器的输出目录//
        g.deleteOutRootDir();
		//通过数据库表生成文件,template为模板的根目录
		g.generateByTable("project_bizresults","template");
        //自动搜索数据库中的所有表并生成文件,template为模板的根目录
        //g.generateByAllTable("template");
        //g.generateByClass(Blog.class,"template_clazz");
	}
}
