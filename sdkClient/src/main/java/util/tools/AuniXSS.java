package util.tools;
/**
 * <p>防止XSS攻击</p>
 * @author zhangdongliang
 * @version $Id: AuniXSS.java, v 0.1 2014年9月23日 下午1:55:43 zhangdongliang Exp $
 */
public class AuniXSS {
   
    public static String encode(String value){
        if(value!=null){
            return value.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&acute;");
        }else{
            return null;
        }
    }
    public static String decode(String value){
        if(value!=null){
            return value.replaceAll( "&lt;","<").replaceAll("&gt;", ">").replaceAll("&quot;","\"").replaceAll("&acute;","'").replaceAll("&amp;","&");
        }else{
            return null;
        }
    }
}
