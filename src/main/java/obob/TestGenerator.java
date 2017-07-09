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
		//鍒犻櫎鐢熸垚鍣ㄧ殑杈撳嚭鐩綍//
        g.deleteOutRootDir();
		//閫氳繃鏁版嵁搴撹〃鐢熸垚鏂囦欢,template涓烘ā鏉跨殑鏍圭洰褰�
		g.generateByTable("person_role_relation","template");
        //鑷姩鎼滅储鏁版嵁搴撲腑鐨勬墍鏈夎〃骞剁敓鎴愭枃浠�,template涓烘ā鏉跨殑鏍圭洰褰�
        //g.generateByAllTable("template");
        //g.generateByClass(Blog.class,"template_clazz");
	}
}
